package com.example.tasklist.activities

import TaskDAO
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Toast
import com.example.tasklist.data.Task
import com.example.tasklist.databinding.ActivityTaskBinding

class TaskActivity : AppCompatActivity() {

    private var categoryId: Int = -1
    private lateinit var binding: ActivityTaskBinding

    private lateinit var taskDAO: TaskDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        categoryId = intent.getIntExtra(BaseColumns._ID, -1)

        taskDAO = TaskDAO(this)

        binding.newTaskButton.setOnClickListener {
            val taskName = binding.newTaskEditText.text.toString()
            val task = Task(-1, taskName, false, categoryId, 1)
            taskDAO.insert(task)
            Toast.makeText(this, "Tarea guardada correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}