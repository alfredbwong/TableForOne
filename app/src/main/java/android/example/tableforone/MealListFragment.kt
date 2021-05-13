package android.example.tableforone

import android.content.Intent
import android.example.tableforone.databinding.FragmentMealListBinding
import android.example.tableforone.meal.MealCategorySelectViewModelFactory
import android.example.tableforone.meal.MealReminderAddViewModel
import android.example.tableforone.meal.reminder.MealReminder
import android.example.tableforone.meal.reminder.MealReminderAdapter
import android.example.tableforone.network.Status
import android.example.tableforone.utils.MEAL_REMINDER_KEY_ID
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass. This fragment displays the meal reminder list.
 */
class MealListFragment : Fragment() {

    private val viewModel : MealReminderAddViewModel by activityViewModels {

            MealCategorySelectViewModelFactory(activity?.applicationContext)

    }
    private lateinit var binding : FragmentMealListBinding
    private var mealReminders: MutableList<MealReminder> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMealListBinding.inflate(inflater)

        binding.lifecycleOwner = this
        viewModel.getMealRemindersData()

        val adapter = MealReminderAdapter{
            mealReminder ->

            //Save the selection to the ViewModel
            viewModel.mealReminderItemSelected.value = mealReminder.id

            val intent = Intent(requireActivity(), MealReminderDetailActivity::class.java )
            Log.i(TAG, "${mealReminder.id}")
            intent.putExtra(MEAL_REMINDER_KEY_ID, mealReminder.id)
            startActivity(intent)
        }
        binding.mealReminderRecyclerView.adapter = adapter
        viewModel.mealReminders.observe(viewLifecycleOwner, {
            resource->

            when (resource.status) {
                Status.SUCCESS -> {
                    if (resource.data == null) {
                        Log.i(TAG, "Missing resource data")

                    }
                    //idle()

                    mealReminders.clear()
                    val data = resource.data as List<MealReminder>

                    mealReminders.addAll(resource.data)
                    binding.mealReminderRecyclerView.adapter?.notifyDataSetChanged()
                    adapter.submitList(mealReminders)
                }
                Status.LOADING -> {


                }
                Status.ERROR -> {


                }
            }
        })
        binding.addMealFloatingActionButton.setOnClickListener{
            findNavController().navigate(MealListFragmentDirections.actionMealListFragmentToMealCategorySelectFragment())
        }

        return binding.root
    }
    companion object{
        const val TAG = "MealList"
    }
}