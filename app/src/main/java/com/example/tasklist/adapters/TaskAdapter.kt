package com.example.tasklist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.data.Task
import com.example.tasklist.databinding.ItemTaskBinding

class TaskAdapter(
    private var dataSet: List<Task>,
    val onItemClickListener: (Int)->Unit):
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.render(dataSet[position])
        holder.itemView.setOnClickListener{onItemClickListener(position)}

    }

    override fun getItemCount(): Int = dataSet.size
    fun updateData(dataSet: List<Task>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }


    class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {

        fun render(superhero: Task) {
            binding.taskTextView.text = superhero.name
            // binding.biographyTextView.text = superhero.biography.realName

        }
    }
}