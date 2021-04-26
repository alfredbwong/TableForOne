package android.example.tableforone

import android.example.tableforone.databinding.FragmentMealCategorySelectBinding
import android.example.tableforone.mealCateorySelect.MealCategory
import android.example.tableforone.mealCateorySelect.MealCategoryAdapter
import android.example.tableforone.mealCateorySelect.MealCategorySelectViewModel
import android.example.tableforone.mealList.MealListViewModel
import android.os.Bundle
import android.util.Log
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

        val adapter = MealCategoryAdapter()
        binding.mealCategoryRecyclerView.adapter = adapter

        viewModel.mealCategories.observe(viewLifecycleOwner, Observer{
            it?.let {
                Log.i("MealCategorySelect", "list of meals :  ${it.size} ${it[0].strCategory}")
                adapter.submitList(it)
            }
        })

        return binding.root
    }

}