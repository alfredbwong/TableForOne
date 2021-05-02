package android.example.tableforone

import android.example.tableforone.databinding.FragmentMealSelectionBinding

import android.example.tableforone.meal.select.MealCategoryItem
import android.example.tableforone.meal.select.MealCategoryItemAdapter
import android.example.tableforone.mealCateorySelect.MealCategorySelectViewModel
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
 * Use the [MealCategoryItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealCategoryItemFragment : Fragment() {
    private lateinit var binding : FragmentMealSelectionBinding
    private val viewModel: MealCategorySelectViewModel by activityViewModels()
    private val mealCategoryItems: MutableList<MealCategoryItem> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.i(TAG, "onCreateView")
        viewModel.getMealCategoryItemsData()
        binding = FragmentMealSelectionBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModelCategories = viewModel

        val adapter = MealCategoryItemAdapter{
            it->
            viewModel.mealRecipeItemSelected.value = it.idMeal
            val action = MealCategoryItemFragmentDirections.actionMealSelectionFragmentToMealRecipeDetailFragment()
            findNavController().navigate(action)
        }
        binding.mealSelectRecyclerView.adapter = adapter

        viewModel.mealCategoryItems.observe(viewLifecycleOwner, Observer{
            resource->
            Log.i(TAG, "$resource")
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