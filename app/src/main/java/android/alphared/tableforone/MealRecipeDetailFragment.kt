package android.alphared.tableforone

import android.alphared.tableforone.adapter.RecipeIngredientAdapter
import android.alphared.tableforone.adapter.RecipeInstructionAdapter
import android.alphared.tableforone.databinding.FragmentMealRecipeDetailBinding
import android.alphared.tableforone.meal.MealReminderAddViewModel
import android.alphared.tableforone.network.Status
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration


/**
 * A simple [Fragment] subclass. This fragment displays the recipe information.
 */
class MealRecipeDetailFragment : Fragment() {

    private val viewModel : MealReminderAddViewModel by activityViewModels()
    private lateinit var binding : FragmentMealRecipeDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        viewModel.getMealRecipeDetailsData()
        binding = FragmentMealRecipeDetailBinding.inflate(inflater)

        viewModel.mealRecipeItem.observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    if (resource.data == null) {
                        Log.i(TAG, "Missing resource data")

                    } else {

                        binding.mealRecipe = resource.data
                        viewModel.mealRecipeDetails = resource.data
                        viewModel.convertListOfInstructions(resource.data.strInstructions)
                        viewModel.createListOfIngredients(resource.data)
                        binding.executePendingBindings()
                    }
                }
                Status.LOADING -> {


                }
                Status.ERROR -> {


                }
            }
        })

        val instructionAdapter = RecipeInstructionAdapter()
        binding.instructionRecyclerView.adapter = instructionAdapter
        viewModel.mealRecipeItemInstructions.observe(viewLifecycleOwner, {
            listInstructions->
            instructionAdapter.submitList(listInstructions)
        })

        val ingredientAdapter = RecipeIngredientAdapter()
        binding.ingredientsRecyclerView.adapter = ingredientAdapter
        viewModel.mealRecipeItemIngredients.observe(viewLifecycleOwner, {
            listIngredients->
            ingredientAdapter.submitList(listIngredients)
        })
        binding.ingredientsRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

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