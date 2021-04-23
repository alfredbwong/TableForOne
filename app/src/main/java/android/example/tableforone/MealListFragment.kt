package android.example.tableforone

import android.example.tableforone.databinding.FragmentMealListBinding
import android.example.tableforone.mealList.MealListViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider


/**
 * A simple [Fragment] subclass.
 * Use the [MealListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealListFragment : Fragment() {
    private val viewModel: MealListViewModel by lazy {
        ViewModelProvider(this).get(MealListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentMealListBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        return binding.root
    }

}