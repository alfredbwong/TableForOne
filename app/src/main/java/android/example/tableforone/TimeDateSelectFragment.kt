package android.example.tableforone

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.example.tableforone.databinding.FragmentTimeDateSelectBinding
import android.example.tableforone.mealCateorySelect.MealCategorySelectViewModel
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.activityViewModels
import java.util.*
import androidx.lifecycle.Observer



/**
 * A simple [Fragment] subclass.
 * Use the [TimeDateSelectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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
}