package com.example.tasklist.activities

import TaskDAO
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.R
import com.example.tasklist.adapters.TaskAdapter
import com.example.tasklist.data.Task
import com.example.tasklist.databinding.ActivityDetailBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailActivity : AppCompatActivity() {


    private lateinit var binding: ActivityDetailBinding
    lateinit var taskRecyclerView: RecyclerView
    lateinit var addTaskButton: FloatingActionButton
    private lateinit var taskAdapter: TaskAdapter
    lateinit var taskList: List<Task>
    lateinit var taskDAO: TaskDAO

    var categoryId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryId = intent.getIntExtra(BaseColumns._ID, -1)


        taskRecyclerView = binding.taskRecyclerView

        addTaskButton = binding.addTaskButton


        taskDAO = TaskDAO(this)
        taskList = taskDAO.findAll()

        taskAdapter = TaskAdapter(emptyList(), {
            // showAlertDialog()
            Toast.makeText(this, "Click en tarea: ${taskList[it].name}", Toast.LENGTH_SHORT).show()

        }, { position, newTaskName ->
            val task = taskList[position]
            task.name = newTaskName
            task.categoryId = categoryId

            taskDAO.update(task)
            loadData()
        }, {
            taskDAO.delete(taskList[it])
            Toast.makeText(this, "Tarea borrada correctamente", Toast.LENGTH_SHORT).show()
            loadData()
        }, {
            onItemClickCheckBoxListener(it)
            loadData()
        })


        binding.taskRecyclerView.adapter = taskAdapter
        binding.taskRecyclerView.layoutManager= GridLayoutManager(this,2)

        binding.addTaskButton.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            intent.putExtra(BaseColumns._ID, categoryId)
            startActivity(intent)
        }


    }

    private fun onItemClickCheckBoxListener(position:Int) {
        val task: Task = taskList[position]
        task.done = !task.done
        taskDAO.update(task)
        taskAdapter.notifyItemChanged(position)
        loadData()
        //adapter.notifyDataSetChanged()
    }
    override fun onResume() {
        super.onResume()
        loadData()
    }
    private fun loadData() {
        taskList = taskDAO.findAllByCategoryId(categoryId)

        taskAdapter.updateData(taskList)
    }
}