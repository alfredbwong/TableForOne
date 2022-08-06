package studio.alphared.tableforone.views.mealreminderlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import studio.alphared.tableforone.MealReminderDetailActivity
import studio.alphared.tableforone.databinding.FragmentMealListBinding
import studio.alphared.tableforone.objects.reminder.MealReminderAdapter
import studio.alphared.tableforone.network.Status
import studio.alphared.tableforone.utils.MEAL_REMINDER_KEY_ID


/**
 * A simple [Fragment] subclass. This fragment displays the meal reminder list.
 */
class MealListFragment : Fragment() {

    private val viewModel : MealListViewModel by viewModels{
        MealReminderListViewModelFactory(activity?.application)
    }
    private lateinit var binding : FragmentMealListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMealListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val adapter = MealReminderAdapter({
            mealReminder ->
            viewModel.moveToReminderItemDetails(mealReminder.id)
        },{
            mealReminder ->
            viewModel.deleteMealReminder(mealReminder.id)
        })

        viewModel.mealReminderDeleted.observe(viewLifecycleOwner, Observer{
            if (it){
                adapter.notifyDataSetChanged()
                viewModel.deletionAndUpdateComplete()
            }
        })
        viewModel.mealReminderItemSelected.observe(viewLifecycleOwner, Observer{
            idOfMealReminder->
            val intent = Intent(requireActivity(), MealReminderDetailActivity::class.java )
            intent.putExtra(MEAL_REMINDER_KEY_ID, idOfMealReminder)
            startActivity(intent)
        })
        viewModel.mealReminderListEmpty.observe(viewLifecycleOwner, Observer{
            isMealReminderListEmpty ->
            if (isMealReminderListEmpty){
                setViewToEmptyIfEmptyList()
            }
        })
        binding.mealReminderRecyclerView.adapter = adapter

        viewModel.mealReminders.observe(viewLifecycleOwner) { resource ->

            when (resource.status) {
                Status.SUCCESS -> {
                    if (resource.data.isNullOrEmpty()) {
                        setViewToEmptyIfEmptyList()
                    } else {
                        binding.scrollViewMealReminder.visibility = View.VISIBLE
                        binding.emptyListText.visibility = View.GONE
                        binding.mealReminderRecyclerView.adapter?.notifyDataSetChanged()
                        adapter.submitList(resource.data)
                    }
                }
                Status.LOADING -> {


                }
                Status.ERROR -> {


                }
            }
        }
        binding.addMealFloatingActionButton.setOnClickListener{
            findNavController().navigate(MealListFragmentDirections.actionMealListFragmentToMealCategorySelectFragment())
        }

        return binding.root
    }

    private fun setViewToEmptyIfEmptyList() {
        binding.emptyListText.visibility = View.VISIBLE
        binding.scrollViewMealReminder.visibility = View.GONE
    }

    companion object{
        const val TAG = "MealList"
    }
}