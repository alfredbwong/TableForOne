package android.example.tableforone

import android.example.tableforone.databinding.FragmentMealCategorySelectBinding
import android.example.tableforone.meal.MealCategorySelectViewModelFactory
import android.example.tableforone.meal.MealReminderAddViewModel
import android.example.tableforone.meal.category.MealCategory
import android.example.tableforone.meal.category.MealCategoryAdapter
import android.example.tableforone.network.Status
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass. This fragment displays the list of categories.
 */
class MealCategorySelectFragment : Fragment() {

    // Shared ViewModel
    private val viewModel: MealReminderAddViewModel by activityViewModels {
        MealCategorySelectViewModelFactory(activity?.applicationContext)
    }
    private val mealCategories: MutableList<MealCategory> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        viewModel.getMealCategoriesData()
        val binding = FragmentMealCategorySelectBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModelCategories = viewModel

        //Pass in a lambda function to control what happens after every click
        val adapter = MealCategoryAdapter{
            mealCategory->
            //Save the selection to the ViewModel
            viewModel.mealCategorySelected.value = mealCategory.strCategory

            val navController = findNavController()
            navController.navigate(MealCategorySelectFragmentDirections.actionMealCategorySelectFragmentToMealSelectionFragment())
        }
        binding.mealCategoryRecyclerView.adapter = adapter

        viewModel.mealCategories.observe(viewLifecycleOwner, { resource ->

            when (resource.status) {
                Status.SUCCESS -> {
                    if (resource.data == null) {
                        Log.i(TAG, "Missing resource data")

                    }

                    mealCategories.clear()
                    mealCategories.addAll(resource.data as List<MealCategory>)
                    binding.mealCategoryRecyclerView.adapter?.notifyDataSetChanged()
                    adapter.submitList(mealCategories)
                }
                Status.LOADING -> {


                }
                Status.ERROR -> {


                }
            }
        })

        return binding.root
    }


    companion object{
    const val TAG = "MealCategory"
}
}