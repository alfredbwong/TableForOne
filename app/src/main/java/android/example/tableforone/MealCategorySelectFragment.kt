package android.example.tableforone

import android.example.tableforone.databinding.FragmentMealCategorySelectBinding
import android.example.tableforone.mealCateorySelect.MealCategory
import android.example.tableforone.mealCateorySelect.MealCategorySelectViewModel
import android.example.tableforone.mealList.MealListViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


/**
 * A simple [Fragment] subclass.
 * Use the [MealCategorySelectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealCategorySelectFragment : Fragment() {

    private val viewModel: MealCategorySelectViewModel by lazy {
        ViewModelProvider(this).get(MealCategorySelectViewModel::class.java)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding = FragmentMealCategorySelectBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModelCategories = viewModel

        viewModel.mealCategories.observe(viewLifecycleOwner, Observer { listCategories ->
            val listCategoriesStr = mutableListOf<String>()
            for (category in listCategories) {
                listCategoriesStr.add(category.strCategory)
            }
            val spinner = binding.selectMealCategorySpinner

            val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                listCategoriesStr
            )
            spinner.adapter = spinnerAdapter
        })

        return binding.root
    }

}