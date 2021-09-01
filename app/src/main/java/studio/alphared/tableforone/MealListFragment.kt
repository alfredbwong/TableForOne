package studio.alphared.tableforone

import android.content.Intent
import studio.alphared.tableforone.databinding.FragmentMealListBinding
import studio.alphared.tableforone.meal.MealCategorySelectViewModelFactory
import studio.alphared.tableforone.meal.MealReminderAddViewModel
import studio.alphared.tableforone.meal.reminder.MealReminder
import studio.alphared.tableforone.meal.reminder.MealReminderAdapter
import studio.alphared.tableforone.network.Status
import studio.alphared.tableforone.utils.MEAL_REMINDER_KEY_ID
import android.os.Bundle
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

        val adapter = MealReminderAdapter({
            mealReminder ->

            //Save the selection to the ViewModel
            viewModel.mealReminderItemSelected.value = mealReminder.id

            val intent = Intent(requireActivity(), MealReminderDetailActivity::class.java )
            intent.putExtra(MEAL_REMINDER_KEY_ID, mealReminder.id)
            startActivity(intent)
        },{
            mealReminder ->
            viewModel.deleteMealReminder(mealReminder.id)
        })
        binding.mealReminderRecyclerView.adapter = adapter
        viewModel.mealReminders.observe(viewLifecycleOwner, {
            resource->

            when (resource.status) {
                Status.SUCCESS -> {
                    if (resource.data.isNullOrEmpty()) {
                        binding.emptyListText.visibility = View.VISIBLE
                        binding.scrollView4.visibility = View.GONE

                    } else {
                        binding.scrollView4.visibility = View.VISIBLE
                        binding.emptyListText.visibility = View.GONE
                        mealReminders.clear()
                        mealReminders.addAll(resource.data)
                        binding.mealReminderRecyclerView.adapter?.notifyDataSetChanged()
                        adapter.submitList(mealReminders)
                    }
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