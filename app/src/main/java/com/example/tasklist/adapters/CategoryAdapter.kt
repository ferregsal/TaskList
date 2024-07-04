package com.example.tasklist.adapters

import TaskDAO
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.data.Category
import com.example.tasklist.data.Task
import com.example.tasklist.databinding.ItemCategoryBinding
import com.example.tasklist.databinding.ItemTaskBinding


class CategoryAdapter(
    private var dataSet: List<Category> = emptyList(),
    private val onItemClickListener: (Int) -> Unit,
    private val onItemSaveClickListener: (Int, String) -> Unit,
    private val onItemDeleteClickListener: (Int) -> Unit,
    private val onItemCheckedClickListener: (Int) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.render(dataSet[position])
        holder.itemView.setOnClickListener{onItemClickListener(position)}
        holder.binding.deleteImageButton.setOnClickListener {
            onItemDeleteClickListener(position)
        }

        holder.itemView.setOnLongClickListener{
            if (!holder.isEditing) {
                holder.toggleEditMode()
            }
            return@setOnLongClickListener true
        }
        holder.binding.saveImageButton.setOnClickListener {
            holder.toggleEditMode()
            onItemSaveClickListener(position, holder.binding.categoryEditText.text.toString())
        }


    }

    override fun getItemCount(): Int = dataSet.size
    fun updateData(dataSet: List<Category>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }


    class CategoryViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        var isEditing = false

        fun render(category: Category) {
            binding.categoryTextView.text = category.name
            binding.categoryEditText.setText(category.name)

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