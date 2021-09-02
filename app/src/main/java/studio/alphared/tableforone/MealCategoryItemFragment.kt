package studio.alphared.tableforone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import studio.alphared.tableforone.databinding.FragmentMealSelectionBinding
import studio.alphared.tableforone.meal.MealReminderAddViewModel
import studio.alphared.tableforone.meal.select.MealCategoryItem
import studio.alphared.tableforone.meal.select.MealCategoryItemAdapter
import studio.alphared.tableforone.network.Status

/**
 * A simple [Fragment] subclass. This fragment displays the list of category recipe items.
 */
class MealCategoryItemFragment : Fragment() {
    private lateinit var binding : FragmentMealSelectionBinding
    private val viewModel: MealReminderAddViewModel by activityViewModels()
    private val mealCategoryItems: MutableList<MealCategoryItem> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewModel.getMealCategoryItemsData()
        binding = FragmentMealSelectionBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModelCategories = viewModel

        val adapter = MealCategoryItemAdapter{
            viewModel.mealRecipeItemSelected.value = it.idMeal
            val action = MealCategoryItemFragmentDirections.actionMealSelectionFragmentToMealRecipeDetailFragment()
            findNavController().navigate(action)
        }
        binding.mealSelectRecyclerView.adapter = adapter

        viewModel.mealCategoryItems.observe(viewLifecycleOwner, {
            resource->
            when (resource.status) {
                Status.SUCCESS -> {
                    mealCategoryItems.clear()
                    mealCategoryItems.addAll(resource.data as List<MealCategoryItem>)
                    binding.mealSelectRecyclerView.adapter?.notifyDataSetChanged()
                    adapter.submitList(mealCategoryItems)
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
        binding.loadingRecipeSelectProgressBar.visibility = View.VISIBLE
        binding.mealSelectRecyclerView.visibility = View.GONE
    }
    private fun showMealCategoryComponents() {
        binding.loadingRecipeSelectProgressBar.visibility = View.GONE
        binding.mealSelectRecyclerView.visibility = View.VISIBLE
    }
    companion object {
        const val TAG = "MealSelect"
    }
}