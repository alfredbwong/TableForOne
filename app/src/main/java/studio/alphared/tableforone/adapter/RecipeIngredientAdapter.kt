package studio.alphared.tableforone.adapter

import studio.alphared.tableforone.meal.Ingredient
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import studio.alphared.tableforone.databinding.RecipeIngredientBinding

class RecipeIngredientAdapter : ListAdapter<Ingredient, RecipeIngredientAdapter.ViewHolder>(
    RecipeIngredientDiffCallback()
) {
    class ViewHolder private constructor(val binding: RecipeIngredientBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: Ingredient) {
            binding.ingredientMeasure.text = ingredient.measure
            binding.ingredientName.text = ingredient.ingredient
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipeIngredientBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object {
        const val TAG = "RecipeIngredientAdapter"
    }
}

class RecipeIngredientDiffCallback : DiffUtil.ItemCallback<Ingredient>() {

    override fun areItemsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Ingredient, newItem: Ingredient): Boolean {
        return oldItem == newItem
    }

}
