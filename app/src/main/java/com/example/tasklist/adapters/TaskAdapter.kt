package com.example.tasklist.adapters

import TaskDAO
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.data.Task

import com.example.tasklist.databinding.ItemTaskBinding


class TaskAdapter(
    private var dataSet: List<Task> = emptyList(),
    private val onItemClickListener: (Int) -> Unit,
    private val onItemSaveClickListener: (Int, String) -> Unit,
    private val onItemDeleteClickListener: (Int) -> Unit,
    private val onItemCheckedClickListener: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.render(dataSet[position])
        holder.itemView.setOnClickListener{onItemClickListener(position)}
        holder.binding.deleteImageButton.setOnClickListener {
            onItemDeleteClickListener(position)
        }
        holder.binding.taskCheckBox.setOnCheckedChangeListener { checkbox, _ ->
                if (checkbox.isPressed) {
                    onItemCheckedClickListener(position)
                }
        }
        holder.itemView.setOnLongClickListener{
            if (!holder.isEditing) {
                holder.toggleEditMode()
            }
            return@setOnLongClickListener true
        }
        holder.binding.saveImageButton.setOnClickListener {
            holder.toggleEditMode()
            onItemSaveClickListener(position, holder.binding.taskEditText.text.toString())
        }


    }

    override fun getItemCount(): Int = dataSet.size
    fun updateData(dataSet: List<Task>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }


    class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        var isEditing = false

        fun render(task: Task) {
            binding.taskTextView.text = task.name
            binding.taskEditText.setText(task.name)
            binding.taskCheckBox.isChecked = task.done

        }

        fun toggleEditMode() {
            if (isEditing) {
                binding.deleteLayout.visibility=View.VISIBLE
                binding.saveLayout.visibility=View.GONE
            } else {
                binding.deleteLayout.visibility=View.GONE
                binding.saveLayout.visibility=View.VISIBLE
            }
            isEditing = !isEditing
        }
    }
}