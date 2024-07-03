package com.example.tasklist.activities


import TaskDAO
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.R
import com.example.tasklist.adapters.TaskAdapter
import com.example.tasklist.data.Task
import com.example.tasklist.databinding.ActivityMainBinding
import com.example.tasklist.databinding.ItemTaskBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var taskRecyclerView: RecyclerView
    lateinit var addTaskButton: FloatingActionButton
    private lateinit var taskAdapter: TaskAdapter
    lateinit var taskList: List<Task>
    lateinit var taskDAO: TaskDAO
    lateinit var deleteLayout:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskRecyclerView = binding.taskRecyclerView

        addTaskButton = binding.addTaskButton


        taskDAO = TaskDAO(this)
        taskList = taskDAO.findAll()

        taskAdapter = TaskAdapter(emptyList(), {
            showAlertDialog()
            Toast.makeText(this, "Click en tarea: ${taskList[it].name}", Toast.LENGTH_SHORT).show()

        }, { position, newTaskName ->
            val task = taskList[position]
            task.name = newTaskName
            taskDAO.update(task)
            loadData()
        }, {
            taskDAO.delete(taskList[it])
            Toast.makeText(this, "Tarea borrada correctamente", Toast.LENGTH_SHORT).show()
            loadData()
        }, {
            val task = taskList[it]
            task.done = !task.done
            taskDAO.update(task)
            loadData()
        })
        binding.taskRecyclerView.adapter = taskAdapter
        binding.taskRecyclerView.layoutManager=LinearLayoutManager(this)

        binding.addTaskButton.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()

        taskList = taskDAO.findAll()

        taskAdapter.updateData(taskList)
    }
    private fun loadData() {
        taskList = taskDAO.findAll()

       taskAdapter.updateData(taskList)
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)

        val customLayout: View = layoutInflater.inflate(R.layout.custom_view_layout, null)
        builder.setView(customLayout)

        builder.setPositiveButton("OK") { dialog: DialogInterface?, which: Int ->
            // Opcional: Realizar acciones adicionales cuando se pulse "OK"
        }

        val dialog = builder.create()
        dialog.show()
    }

   /* private fun navigateToDetail(superhero: Task) {
        Log.d("MainActivity", "Navigating to detail for superhero: ${superhero.name}, ID: ${superhero.id}")

        Toast.makeText(this, superhero.name, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_SUPERHERO_ID, superhero.id)
        startActivity(intent)
    }*/


       // findViewById<Button>(R.id.addTaskButton).setOnClickListener {
       //     taskDAO.findAll()
        //}
    }

//}