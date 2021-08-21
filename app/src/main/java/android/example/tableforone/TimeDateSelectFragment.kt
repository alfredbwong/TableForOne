package android.example.tableforone

import android.app.*
import android.content.Context
import android.content.Intent
import android.example.tableforone.databinding.FragmentTimeDateSelectBinding
import android.example.tableforone.meal.MealReminderAddViewModel
import android.example.tableforone.meal.reminder.MealReminder
import android.example.tableforone.receiver.MealReminderReceiver
import android.example.tableforone.utils.MEAL_REMINDER_KEY_ID
import android.example.tableforone.utils.MEAL_REMINDER_NAME
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.app.AlarmManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import java.util.Calendar.*


/**
 * A simple [Fragment] subclass. This fragment displays option for user to select a time/date through dialog and save.
 */
class TimeDateSelectFragment : Fragment(), DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {
    private val viewModel: MealReminderAddViewModel by activityViewModels()

    private lateinit var binding: FragmentTimeDateSelectBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentTimeDateSelectBinding.inflate(inflater)
        binding.dateTimeSelectButton.setOnClickListener{
            viewModel.setupDatePicker()
        }

        viewModel.showDatePicker.observe(viewLifecycleOwner, {
            showDatePicker->
            if (showDatePicker){
                val datePickerDialog =
                        DatePickerDialog(requireContext(), this, viewModel.year, viewModel.month,viewModel.day)
                datePickerDialog.show()
            }

        })

        viewModel.showTimePicker.observe(viewLifecycleOwner, {
            showTimePicker->
            if (showTimePicker){
                val timePickerDialog = TimePickerDialog(requireContext(), this, viewModel.hour, viewModel.minute,
                        DateFormat.is24HourFormat(requireContext()))
                timePickerDialog.show()
            }

        })

        val motionLayout = binding.motionLayout
        binding.motionLayout.setTransitionListener(object : MotionLayout.TransitionListener{
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                runBlocking{
                    launch(Dispatchers.IO) {
                        val mealIdSaved = viewModel.saveMealReminder()
                        //Notification
                        createDataNotification(mealIdSaved, viewModel.getMealReminderToBeSaved())

                    }
                }
                binding.nextButton.text = getString(R.string.meal_reminder_saving)
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            }

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                binding.nextButton.text = getString(R.string.meal_reminder_saved)

                val action = TimeDateSelectFragmentDirections.actionTimeDateSelectFragmentToMealListFragment()
                findNavController().navigate(action)
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }

        })
        binding.nextButton.setOnClickListener{
            if (viewModel.myYear != 0|| viewModel.myHour !=0 ) {

                motionLayout.transitionToEnd()
            } else {
                Toast.makeText(requireContext(), getString(R.string.toast_missing_date_time), Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }




    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.onDateSetFun( year, month, dayOfMonth)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        viewModel.onTimeSetFun(hourOfDay, minute)
        //Set date/time
        val datetimeToAlarm = LocalDateTime.of(viewModel.myYear, viewModel.myMonth, viewModel.myDay, viewModel.myHour, viewModel.myMinute)
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        val formattedDateTime = formatter.format(datetimeToAlarm)
        binding.textView.text = String.format(getString(R.string.time_date_display),formattedDateTime)
    }


    companion object {
        const val TAG = "TimeDateFrag"
    }
    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_HIGH

            ).apply {
                setShowBadge(false)
            }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Meal Reminder"

            val notificationManager = requireActivity().getSystemService(
                    NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)

        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createDataNotification(mealIdSaved: Long, mealReminderToBeSaved: MealReminder) {
        createChannel(getString(R.string.meal_reminder_notification_channel_id),getString(R.string.meal_reminder_notification_channel_name))

        //Replace mealReminderId
        mealReminderToBeSaved.id = mealIdSaved

        //Set date/time
        val datetimeToAlarm = getInstance(Locale.getDefault())
        datetimeToAlarm.timeInMillis = System.currentTimeMillis()
        datetimeToAlarm.set(HOUR_OF_DAY, mealReminderToBeSaved.mealHour)
        datetimeToAlarm.set(MINUTE, mealReminderToBeSaved.mealMinute)
        datetimeToAlarm.set(YEAR, mealReminderToBeSaved.mealYear)
        datetimeToAlarm.set(MONTH, mealReminderToBeSaved.mealMonth)
        datetimeToAlarm.set(DAY_OF_MONTH, mealReminderToBeSaved.mealDay)
        val intent = Intent(requireActivity().applicationContext, MealReminderReceiver::class.java).apply {
            putExtra(MEAL_REMINDER_KEY_ID, mealIdSaved)

            putExtra(MEAL_REMINDER_NAME, mealReminderToBeSaved.mealName)

        }
        val pendingIntent = PendingIntent.getBroadcast(requireActivity().applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.RTC_WAKEUP,
                datetimeToAlarm.timeInMillis, pendingIntent
        )


    }

    override fun onResume() {
        super.onResume()
        viewModel.resetDateAndTime()

    }
}