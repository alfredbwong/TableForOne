package studio.alphared.tableforone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import studio.alphared.tableforone.databinding.FragmentMealCategorySelectBinding
import studio.alphared.tableforone.meal.MealCategorySelectViewModelFactory
import studio.alphared.tableforone.meal.MealReminderAddViewModel
import studio.alphared.tableforone.meal.category.MealCategory
import studio.alphared.tableforone.meal.category.MealCategoryAdapter
import studio.alphared.tableforone.network.Status


/**
 * A simple [Fragment] subclass. This fragment displays the list of categories.
 */
class MealCategorySelectFragment : Fragment() {

    // Shared ViewModel
    private val viewModel: MealReminderAddViewModel by activityViewModels {
        MealCategorySelectViewModelFactory(activity?.applicationContext)
    }
    private val mealCategories: MutableList<MealCategory> = mutableListOf()
    private lateinit var binding : FragmentMealCategorySelectBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        viewModel.getMealCategoriesData()
        binding = FragmentMealCategorySelectBinding.inflate(inflater)
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
                    mealCategories.clear()
                    mealCategories.addAll(resource.data as List<MealCategory>)
                    binding.mealCategoryRecyclerView.adapter?.notifyDataSetChanged()
                    adapter.submitList(mealCategories)
                    showMealCategoryComponents()
                }
                Status.LOADING -> {
                    showLoadingComponents()

                }
                Status.ERROR -> {
                }
            }
        })

        return binding.root
    }

    private fun showLoadingComponents() {
        binding.loadingProgressBar.visibility = View.VISIBLE
        binding.mealCategoryRecyclerView.visibility = View.GONE
    }
    private fun showMealCategoryComponents() {
        binding.loadingProgressBar.visibility = View.GONE
        binding.mealCategoryRecyclerView.visibility = View.VISIBLE
    }


    companion object{
    const val TAG = "MealCategory"
}
}