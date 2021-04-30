package android.example.tableforone

import android.example.tableforone.databinding.FragmentMealListBinding
import android.example.tableforone.databinding.FragmentMealSelectionBinding
import android.example.tableforone.meal.reminder.MealReminder
import android.example.tableforone.meal.reminder.MealReminderAdapter
import android.example.tableforone.mealCateorySelect.MealCategory
import android.example.tableforone.mealCateorySelect.MealCategorySelectViewModel
import android.example.tableforone.mealCateorySelect.MealCategorySelectViewModelFactory
import android.example.tableforone.mealList.MealListViewModel
import android.example.tableforone.network.Status
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass.
 * Use the [MealListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealListFragment : Fragment() {

    private val viewModel : MealCategorySelectViewModel by activityViewModels(){

            MealCategorySelectViewModelFactory(activity?.applicationContext)

    }
    private lateinit var binding : FragmentMealListBinding
    private var mealReminders: MutableList<MealReminder> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMealListBinding.inflate(inflater)

        binding.lifecycleOwner = this
        viewModel.getMealRemindersData()

        val adapter = MealReminderAdapter{
            mealReminder ->

            //Save the selection to the viewmodel
            viewModel.mealReminderItemSelected.value = mealReminder.id

            val navController = findNavController()
//            navController.navigate(MealListFragmentDirections.actionMealListFragmentToMealReminderDetailFragment(mealReminder.id))
        }
        binding.mealReminderRecyclerView.adapter = adapter
        viewModel.mealReminders.observe(viewLifecycleOwner, Observer {
            resource->
//            Log.i(TAG, "meal Reminders : $resource.data")

            when (resource.status) {
                Status.SUCCESS -> {
                    if (resource.data == null) {

                    }
                    //idle()

                    mealReminders.clear()
                    mealReminders.addAll(resource.data as List<MealReminder>)
//                    Log.i(TAG, "$mealReminders")
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