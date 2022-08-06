package studio.alphared.tableforone.objects.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import studio.alphared.tableforone.databinding.MealCategoryListItemBinding

/**
 * List Adapter class for binding the list of meal categories and placing them in a ViewHolder
 */
class MealCategoryAdapter (private val listener: (MealCategory) -> Unit): ListAdapter<MealCategory,
        MealCategoryAdapter.ViewHolder>(MealCategoryDiffCallback()) {

    class ViewHolder private constructor(val binding: MealCategoryListItemBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item: MealCategory?) {
            binding.mealCategory = item
            binding.executePendingBindings()
            
        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MealCategoryListItemBinding.inflate(layoutInflater, parent, false)

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
class MealCategoryDiffCallback : DiffUtil.ItemCallback<MealCategory>() {
    override fun areItemsTheSame(oldItem: MealCategory, newItem: MealCategory): Boolean {
        return oldItem.idCategory == newItem.idCategory
    }

    override fun areContentsTheSame(oldItem: MealCategory, newItem: MealCategory): Boolean {
        return oldItem == newItem
    }
}
