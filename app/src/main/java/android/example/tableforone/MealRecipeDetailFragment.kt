package android.example.tableforone

import android.example.tableforone.databinding.FragmentMealRecipeDetailBinding
import android.example.tableforone.mealCateorySelect.MealCategorySelectViewModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer


/**
 * A simple [Fragment] subclass.
 * Use the [MealRecipeDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealRecipeDetailFragment : Fragment() {

    private val viewModel : MealCategorySelectViewModel by activityViewModels()
    private lateinit var binding : FragmentMealRecipeDetailBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModel.getMealRecipeResponse()
        binding = FragmentMealRecipeDetailBinding.inflate(inflater)
        binding.mealRecipe = viewModel.mealRecipeItem.value

        viewModel.mealRecipeItem.observe(viewLifecycleOwner, Observer {
            it ->
            Log.i(TAG, "Got new meal recipe")
            binding.mealRecipe = viewModel.mealRecipeItem.value
            binding.executePendingBindings()
        })
        return binding.root
    }

    companion object {
        const val TAG="MealRecipeDetail"
    }
}