package com.example.tasklist.activities


import CategoryDAO
import TaskDAO
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasklist.R
import com.example.tasklist.adapters.CategoryAdapter
import com.example.tasklist.data.Category
import com.example.tasklist.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var categoryRecyclerView: RecyclerView
    lateinit var addCategoryButton: FloatingActionButton
    private lateinit var categoryAdapter: CategoryAdapter
    lateinit var categoryList: List<Category>
    lateinit var categoryDAO: CategoryDAO
    lateinit var deleteLayout:LinearLayout
    var searchText: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryRecyclerView = binding.categoryRecyclerView

        addCategoryButton = binding.addCategoryButton


        categoryDAO = CategoryDAO(this)
        categoryList = categoryDAO.findAll()


        categoryAdapter = CategoryAdapter(emptyList(), {position ->
            navigateToDetail(categoryList[position])

           // showAlertDialog()
          Toast.makeText(this, "Click en tarea: ${categoryList[position].name}", Toast.LENGTH_SHORT).show()


        }, { position, newCategoryName ->
            val category = categoryList[position]
            category.name = newCategoryName
            categoryDAO.update(category)
            loadData()
        }, {
            categoryDAO.delete(categoryList[it])
            Toast.makeText(this, "Tarea borrada correctamente", Toast.LENGTH_SHORT).show()
            loadData()
        }, {
            val category = categoryList[it]
            category.priority = category.priority
            categoryDAO.update(category)
            loadData()
        })
        binding.categoryRecyclerView.adapter = categoryAdapter
        binding.categoryRecyclerView.layoutManager=GridLayoutManager(this,2)

        binding.addCategoryButton.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()

        categoryList = categoryDAO.findAll()

        categoryAdapter.updateData(categoryList)
    }
    private fun loadData() {
        categoryList = categoryDAO.findAll()

        categoryAdapter.updateData(categoryList)
    }
    private fun applyFilters() {

        if (searchText != null) {
            categoryList = categoryDAO.findAll().filter {
                it.name.contains(searchText!!, true)
            }
        }

        categoryAdapter.updateData(categoryList)

    }
        fun navigateToDetail(category: Category) {
            val intent = Intent(this, DetailActivity::class.java)
             intent.putExtra(BaseColumns._ID, category.id)
            startActivity(intent)

        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)

        val searchViewItem = menu.findItem(R.id.menu_search)
        val searchView = searchViewItem.actionView as SearchView


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {


            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchText = newText
                applyFilters()
                return true
            }

        })

        return true
    }


        /* private fun showAlertDialog() {
             val builder = AlertDialog.Builder(this)

             val customLayout: View = layoutInflater.inflate(R.layout.custom_view_layout, null)
             builder.setView(customLayout)

             builder.setPositiveButton("OK") { dialog: DialogInterface?, which: Int ->
                 // Opcional: Realizar acciones adicionales cuando se pulse "OK"
             }

             val dialog = builder.create()
             dialog.show()
         }*/

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