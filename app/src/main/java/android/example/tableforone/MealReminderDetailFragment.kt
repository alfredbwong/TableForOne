package android.example.tableforone

import android.example.tableforone.databinding.FragmentMealReminderDetailBinding
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
import androidx.navigation.fragment.findNavController

private const val MEAL_ID = "mealId"


/**
 * A simple [Fragment] subclass.
 * Use the [MealReminderDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealReminderDetailFragment : Fragment() {
    private var mealId: Long? = null
    private val viewModel: MealCategorySelectViewModel by activityViewModels() {
        MealCategorySelectViewModelFactory(activity?.applicationContext)
    }
    private lateinit var binding: FragmentMealReminderDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getLong(MEAL_ID)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getMealReminderById(mealId!!)
        binding = FragmentMealReminderDetailBinding.inflate(inflater)
        viewModel.mealReminder.observe(viewLifecycleOwner, Observer { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    if (resource.data == null) {

                    }
                    Log.i(TAG,"${resource.data}")

                    binding.mealReminderDetail = resource.data
                    binding.executePendingBindings()
                }
                Status.LOADING -> {


                }
                Status.ERROR -> {


                }
            }
        })

        binding.backReminderButton.setOnClickListener{
            findNavController().navigate(MealReminderDetailFragmentDirections.actionMealReminderDetailFragmentToMealListFragment())
        }
        return binding.root
    }

    companion object {
        const val TAG = "ReminderDetails"
    }
}