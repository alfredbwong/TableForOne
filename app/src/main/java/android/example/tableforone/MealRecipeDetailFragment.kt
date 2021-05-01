package android.example.tableforone

import android.example.tableforone.databinding.FragmentMealRecipeDetailBinding
import android.example.tableforone.mealCateorySelect.MealCategorySelectViewModel
import android.example.tableforone.network.Status
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController


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

        viewModel.getMealRecipeDetailsData()
        binding = FragmentMealRecipeDetailBinding.inflate(inflater)

        viewModel.mealRecipeItem.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    if (resource.data == null) {

                    }

                    binding.mealRecipe = resource.data
                    viewModel.mealRecipeDetails = resource.data


                    binding.executePendingBindings()
                }
                Status.LOADING -> {


                }
                Status.ERROR -> {


                }
            }
        })

        binding.saveRecipeButton.setOnClickListener{
            val action = MealRecipeDetailFragmentDirections.actionMealRecipeDetailFragmentToTimeDateSelectFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    companion object {
        const val TAG="MealRecipeDetail"
    }
}