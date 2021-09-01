package studio.alphared.tableforone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import studio.alphared.tableforone.databinding.RecipeInstructionBinding

class RecipeInstructionAdapter : ListAdapter<String, RecipeInstructionAdapter.ViewHolder> (
    RecipeInstructionDiffCallback()
){
    class ViewHolder private constructor(val binding: RecipeInstructionBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(instructionStep: String, position:Int) {
            binding.instructionText.text = instructionStep
            binding.instructionNumber.text = (position+1).toString()
            binding.executePendingBindings()

        }
        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipeInstructionBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }
    companion object{
        const val TAG = "RecipeInstructionAdapter"
    }
}

class RecipeInstructionDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}
