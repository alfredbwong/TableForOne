package android.example.tableforone.meal.select

import android.example.tableforone.databinding.MealCategoryListItemBinding
import android.example.tableforone.databinding.MealSelectListItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class MealSelectAdapter (private val listener: (MealSelectItem) -> Unit): ListAdapter<MealSelectItem,
        MealSelectAdapter.ViewHolder>(MealSelectDiffCallback()) {

    class ViewHolder private constructor(val binding: MealSelectListItemBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item: MealSelectItem?) {
            binding.mealSelectItem = item
            binding.executePendingBindings()
            
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MealSelectListItemBinding.inflate(layoutInflater, parent, false)

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
 * Used by ListAdapter to calculate the minumum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class MealSelectDiffCallback : DiffUtil.ItemCallback<MealSelectItem>() {
    override fun areItemsTheSame(oldItem: MealSelectItem, newItem: MealSelectItem): Boolean {
        return oldItem.idMeal == newItem.idMeal
    }

    override fun areContentsTheSame(oldItem: MealSelectItem, newItem: MealSelectItem): Boolean {
        return oldItem == newItem
    }
}
