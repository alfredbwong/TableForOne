package android.example.tableforone

import android.example.tableforone.databinding.FragmentMealSelectionBinding
import android.example.tableforone.meal.select.MealSelectAdapter
import android.example.tableforone.mealCateorySelect.MealCategorySelectViewModel
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
 * Use the [MealSelectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealSelectionFragment : Fragment() {
    private lateinit var binding : FragmentMealSelectionBinding
    private val viewModel: MealCategorySelectViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel.getMealCategoryItemsResponse()
        Log.i(TAG, "onCreateView")
        binding = FragmentMealSelectionBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModelCategories = viewModel

        val adapter = MealSelectAdapter{
            findNavController().popBackStack()
        }
        binding.mealSelectRecyclerView.adapter = adapter

        viewModel.mealItems.observe(viewLifecycleOwner, Observer{
            it->
            Log.i(TAG,"INFO: $it")
            adapter.submitList(it)
        })



        return binding.root
    }

    companion object {
        const val TAG = "MealSelect"
    }
}