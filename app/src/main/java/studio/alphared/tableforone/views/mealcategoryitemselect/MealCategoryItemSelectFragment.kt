package studio.alphared.tableforone.views.mealcategoryitemselect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import studio.alphared.tableforone.databinding.FragmentMealSelectionBinding
import studio.alphared.tableforone.objects.select.MealCategoryItemAdapter
import studio.alphared.tableforone.network.Status

/**
 * A simple [Fragment] subclass. This fragment displays the list of category recipe items.
 */
class MealCategoryItemSelectFragment : Fragment() {
    private lateinit var binding : FragmentMealSelectionBinding
    private val args: MealCategoryItemSelectFragmentArgs by navArgs()

    private val viewModel : MealCategoryItemSelectViewModel by viewModels {
        MealSelectCategoryItemViewModelFactory(activity?.application, args.categoryId)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentMealSelectionBinding.inflate(inflater)

        binding.lifecycleOwner = this

        val adapter = MealCategoryItemAdapter{
            val action = MealCategoryItemSelectFragmentDirections.actionMealSelectionFragmentToMealRecipeDetailFragment(it.idMeal)
            findNavController().navigate(action)
        }
        binding.mealSelectRecyclerView.adapter = adapter

        viewModel.mealCategoryItems.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    binding.mealSelectRecyclerView.adapter?.notifyDataSetChanged()
                    adapter.submitList(resource.data)
                    showMealCategoryComponents()
                }
                Status.LOADING -> {
                    showLoadingComponents()
                }
                Status.ERROR -> {
                }
            }
        }
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