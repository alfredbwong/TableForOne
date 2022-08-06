package studio.alphared.tableforone.objects.reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import studio.alphared.tableforone.databinding.MealReminderListItemBinding


class MealReminderAdapter (private val listener: (MealReminder) -> Unit, private val deleteListener: (MealReminder) -> Unit): ListAdapter<MealReminder,
        MealReminderAdapter.ViewHolder>(MealReminderDiffCallback()) {

    class ViewHolder private constructor(val binding: MealReminderListItemBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item: MealReminder?, deleteListener: (MealReminder) -> Unit) {
            binding.mealReminder = item
            binding.deleteReminderButton.setOnClickListener{
                if (item != null) {
                    deleteListener(item)
                }
            }
            binding.executePendingBindings()
            
        }
        companion object {
            const val TAG = "ViewHolder"
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MealReminderListItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, deleteListener)
        holder.itemView.setOnClickListener{
            listener(item)
        }
    }

}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class MealReminderDiffCallback : DiffUtil.ItemCallback<MealReminder>() {
    override fun areItemsTheSame(oldItem: MealReminder, newItem: MealReminder): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MealReminder, newItem: MealReminder): Boolean {
        return oldItem == newItem
    }
}


