package android.example.tableforone

import android.app.*
import android.content.Context
import android.content.Intent
import android.example.tableforone.databinding.FragmentTimeDateSelectBinding
import android.example.tableforone.meal.reminder.MealReminder
import android.example.tableforone.mealCateorySelect.MealCategorySelectViewModel
import android.example.tableforone.receiver.MealReminderReceiver
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
import androidx.core.app.AlarmManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import java.util.*
import java.util.Calendar.*


/**
 * A simple [Fragment] subclass.
 * Use the [TimeDateSelectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val REMINDER_KEY_ID = "Reminder-Key"
class TimeDateSelectFragment : Fragment(), DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {
    private val viewModel: MealCategorySelectViewModel by activityViewModels()

    private lateinit var binding: FragmentTimeDateSelectBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentTimeDateSelectBinding.inflate(inflater)
        binding.dateTimeSelectButton.setOnClickListener{
            viewModel.setupDatePicker()
        }

        viewModel.showDatePicker.observe(viewLifecycleOwner, Observer {
            showDatePicker->
            if (showDatePicker){
                val datePickerDialog =
                        DatePickerDialog(requireContext(), this, viewModel.year, viewModel.month,viewModel.day)
                datePickerDialog.show()
            }

        })

        viewModel.showTimePicker.observe(viewLifecycleOwner, Observer {
            showTimePicker->
            if (showTimePicker){
                val timePickerDialog = TimePickerDialog(requireContext(), this, viewModel.hour, viewModel.minute,
                        DateFormat.is24HourFormat(requireContext()))
                timePickerDialog.show()
            }

        })
        binding.nextButton.setOnClickListener{
            if (viewModel.myYear != 0|| viewModel.myHour !=0 ) {
                val mealIdSaved = viewModel.saveMealReminder()

                //Notification
                createChannel(getString(R.string.meal_reminder_notification_channel_id),getString(R.string.meal_reminder_notification_channel_name))

                createDataNotification(mealIdSaved, viewModel.getMealReminderToBeSaved())
                val action = TimeDateSelectFragmentDirections.actionTimeDateSelectFragmentToMealListFragment()
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), "You must enter a date/time!", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }




    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.onDateSetFun( year, month, dayOfMonth)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        viewModel.onTimeSetFun(hourOfDay, minute)

        binding.textView.text = "Year: " + viewModel.myYear +
                "\n" + "Month: " + viewModel.myMonth +
                "\n" + "Day: " + viewModel.myDay +
                "\n" + "Hour: " + viewModel.myHour +
                "\n" + "Minute: " + viewModel.myMinute
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

    fun createDataNotification(mealIdSaved: Long, mealReminderToBeSaved: MealReminder) {
        //Replace mealReminderId
        mealReminderToBeSaved.id = mealIdSaved

        //Set date/time
        val datetimeToAlarm = Calendar.getInstance(Locale.getDefault())
        datetimeToAlarm.timeInMillis = System.currentTimeMillis()
        datetimeToAlarm.set(Calendar.HOUR_OF_DAY, mealReminderToBeSaved.mealHour)
        datetimeToAlarm.set(Calendar.MINUTE, mealReminderToBeSaved.mealMinute)
        datetimeToAlarm.set(Calendar.YEAR, mealReminderToBeSaved.mealYear)
        datetimeToAlarm.set(Calendar.MONTH, mealReminderToBeSaved.mealMonth)
        datetimeToAlarm.set(Calendar.DAY_OF_MONTH, mealReminderToBeSaved.mealDay)

        val intent = Intent(requireActivity().applicationContext, MealReminderReceiver::class.java).apply {
            putExtra(REMINDER_KEY_ID, mealIdSaved)
        }
        val pendingIntent = PendingIntent.getBroadcast(requireActivity().applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        AlarmManagerCompat.setExactAndAllowWhileIdle(
                alarmManager,
                AlarmManager.RTC_WAKEUP,
                datetimeToAlarm.timeInMillis, pendingIntent
        )


    }
}