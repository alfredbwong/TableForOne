package android.example.tableforone

import android.example.tableforone.databinding.FragmentMealCategorySelectBinding
import android.example.tableforone.mealCateorySelect.MealCategoryAdapter
import android.example.tableforone.mealCateorySelect.MealCategorySelectViewModel
import android.os.Bundle
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

    private val viewModel: MealCategorySelectViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentMealCategorySelectBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModelCategories = viewModel

        //Pass in a lambda function to control what happens after every click
        val adapter = MealCategoryAdapter{
            mealCategory->
            //Save the selection to the viewmodel
            viewModel._mealCategorySelected.value = mealCategory.strCategory

            val navController = findNavController()
            navController.navigate(MealCategorySelectFragmentDirections.actionMealCategorySelectFragmentToMealSelectionFragment())
        }
        binding.mealCategoryRecyclerView.adapter = adapter

        viewModel.mealCategories.observe(viewLifecycleOwner, Observer{
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }

}