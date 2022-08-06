package studio.alphared.tableforone.views.mealcategoryselect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import studio.alphared.tableforone.databinding.FragmentMealCategorySelectBinding
import studio.alphared.tableforone.objects.category.MealCategoryAdapter
import studio.alphared.tableforone.network.Status


/**
 * A simple [Fragment] subclass. This fragment displays the list of categories.
 */
class MealCategorySelectFragment : Fragment() {

    //ViewModel
    private val viewModel: MealSelectCategoryViewModel by viewModels {
        MealSelectCategoryViewModelFactory(activity?.application)
    }
    private lateinit var binding : FragmentMealCategorySelectBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentMealCategorySelectBinding.inflate(inflater)
        binding.lifecycleOwner = this

        //Pass in a lambda function to control what happens after every click
        val adapter = MealCategoryAdapter{
            mealCategory->
            //Save the selection to the ViewModel
            viewModel.saveMealCategorySelected(mealCategory)
            findNavController().navigate(MealCategorySelectFragmentDirections.actionMealCategorySelectFragmentToMealSelectionFragment(mealCategory.strCategory))
        }
        binding.mealCategoryRecyclerView.adapter = adapter

        viewModel.mealCategories.observe(viewLifecycleOwner) { resource ->

            when (resource.status) {
                Status.SUCCESS -> {
                    binding.mealCategoryRecyclerView.adapter?.notifyDataSetChanged()
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