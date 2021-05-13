package android.example.tableforone.meal.select

import android.example.tableforone.databinding.MealCategorySelectListItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class MealCategoryItemAdapter (private val listener: (MealCategoryItem) -> Unit): ListAdapter<MealCategoryItem,
        MealCategoryItemAdapter.ViewHolder>(MealSelectDiffCallback()) {

    class ViewHolder private constructor(val binding: MealCategorySelectListItemBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item: MealCategoryItem?) {
            binding.mealSelectItem = item
            binding.executePendingBindings()
            
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MealCategorySelectListItemBinding.inflate(layoutInflater, parent, false)

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
        holder.itemView.setOnClickListener{listener(item)}
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class MealSelectDiffCallback : DiffUtil.ItemCallback<MealCategoryItem>() {
    override fun areItemsTheSame(oldItem: MealCategoryItem, newItem: MealCategoryItem): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: MealCategoryItem, newItem: MealCategoryItem): Boolean {
        return oldItem == newItem
    }
}
