package android.alphared.tableforone

import android.alphared.tableforone.databinding.FragmentMealSelectionBinding
import android.alphared.tableforone.meal.MealReminderAddViewModel
import android.alphared.tableforone.meal.select.MealCategoryItem
import android.alphared.tableforone.meal.select.MealCategoryItemAdapter
import android.alphared.tableforone.network.Status
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

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
                    if (resource.data == null) {
                    }
                    //idle()

                    mealCategoryItems.clear()
                    mealCategoryItems.addAll(resource.data as List<MealCategoryItem>)
                    binding.mealSelectRecyclerView.adapter?.notifyDataSetChanged()
                    adapter.submitList(mealCategoryItems)
                }
                Status.LOADING -> {


                }
                Status.ERROR -> {


                }
            }
        })



        return binding.root
    }

    companion object {
        const val TAG = "MealSelect"
    }
}