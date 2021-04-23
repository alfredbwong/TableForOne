package android.example.tableforone

import android.example.tableforone.databinding.FragmentMealListBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass.
 * Use the [MealListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MealListFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentMealListBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.addMealFloatingActionButton.setOnClickListener{
            findNavController().navigate(MealListFragmentDirections.actionMealListFragmentToMealCategorySelectFragment())
        }

        return binding.root
    }

}