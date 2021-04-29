package android.example.tableforone

import android.example.tableforone.databinding.FragmentMealCategorySelectBinding
import android.example.tableforone.mealCateorySelect.MealCategory
import android.example.tableforone.mealCateorySelect.MealCategoryAdapter
import android.example.tableforone.mealCateorySelect.MealCategorySelectViewModel
import android.example.tableforone.mealCateorySelect.MealCategorySelectViewModelFactory
import android.example.tableforone.network.Status
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass.
 * Use the [MealCategorySelectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealCategorySelectFragment : Fragment() {

    // Shared ViewModel
    private val viewModel: MealCategorySelectViewModel by activityViewModels {
        MealCategorySelectViewModelFactory(activity?.applicationContext)
    }
    private val mealCategories: MutableList<MealCategory> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel.getMealCategoriesData()
        val binding = FragmentMealCategorySelectBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModelCategories = viewModel

        //Pass in a lambda function to control what happens after every click
        val adapter = MealCategoryAdapter{
            mealCategory->
            //Save the selection to the viewmodel
            viewModel.mealCategorySelected.value = mealCategory.strCategory

            val navController = findNavController()
            navController.navigate(MealCategorySelectFragmentDirections.actionMealCategorySelectFragmentToMealSelectionFragment())
        }
        binding.mealCategoryRecyclerView.adapter = adapter

        viewModel.mealCategories.observe(viewLifecycleOwner, Observer { resource ->
//            Log.i(TAG, "Observe categories ${resource.data} ${resource.status}")
            when (resource.status) {
                Status.SUCCESS -> {
                    if (resource.data == null) {

                    }
                    //idle()

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