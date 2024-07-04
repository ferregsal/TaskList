package com.example.tasklist.activities

import CategoryDAO
import TaskDAO
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tasklist.data.Category
import com.example.tasklist.data.Task
import com.example.tasklist.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding

    private lateinit var categoryDAO: CategoryDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryDAO = CategoryDAO(this)

        binding.newCategoryButton.setOnClickListener {
            val categoryName = binding.newCategoryEditText.text.toString()
            val category = Category(-1, categoryName, priority=1)
            categoryDAO.insert(category)
            Toast.makeText(this, "Tarea guardada correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}