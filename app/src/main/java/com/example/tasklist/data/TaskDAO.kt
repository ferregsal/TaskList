
import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.example.tasklist.data.Task
import com.example.tasklist.utils.DatabaseManager


class TaskDAO(context: Context) {

    private val databaseManager: DatabaseManager = DatabaseManager(context)

    fun insert(task: Task) {
        val db = databaseManager.writableDatabase

        val values = ContentValues()
        values.put(Task.COLUMN_NAME_TITLE, task.name)
        values.put(Task.COLUMN_NAME_DONE, task.done)
        values.put(Task.COLUMN_NAME_CATEGORY_ID, task.categoryId)
        values.put(Task.COLUMN_NAME_PRIORITY, task.priority)
        val newRowId = db.insert(Task.TABLE_NAME, null, values)
        task.id = newRowId.toInt()
    }

    fun update(task: Task) {
        val db = databaseManager.writableDatabase

        val values = ContentValues()
        values.put(Task.COLUMN_NAME_TITLE, task.name)
        values.put(Task.COLUMN_NAME_DONE, task.done)
        values.put(Task.COLUMN_NAME_CATEGORY_ID, task.categoryId)
        values.put(Task.COLUMN_NAME_PRIORITY, task.priority)
        val updatedRows = db.update(
            Task.TABLE_NAME,
            values,
            "${BaseColumns._ID} = ${task.id}",
            null
        )
    }

    fun delete(task: Task) {
        val db = databaseManager.writableDatabase

        val deletedRows = db.delete(Task.TABLE_NAME, "${BaseColumns._ID} = ${task.id}", null)
    }

    fun find(id: Int) : Task? {
        val db = databaseManager.readableDatabase

        val projection = arrayOf(BaseColumns._ID, Task.COLUMN_NAME_TITLE, Task.COLUMN_NAME_DONE, Task.COLUMN_NAME_CATEGORY_ID )

        val cursor = db.query(
            Task.TABLE_NAME,                        // The table to query
            projection,                             // The array of columns to return (pass null to get all)
            "${BaseColumns._ID} = $id",      // The columns for the WHERE clause
            null,                         // The values for the WHERE clause
            null,                            // don't group the rows
            null,                             // don't filter by row groups
            null                             // The sort order
        )

        var task: Task? = null
        if (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_TITLE))
            val done = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_DONE)) == 1
            val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_CATEGORY_ID))
            val priority = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_PRIORITY))

            task = Task(id, name, done,categoryId, priority)
        }
        cursor.close()
        db.close()
        return task
    }

    fun findAll() : List<Task> {
        val db = databaseManager.readableDatabase

        val projection = arrayOf(BaseColumns._ID, Task.COLUMN_NAME_TITLE, Task.COLUMN_NAME_DONE, Task.COLUMN_NAME_CATEGORY_ID, Task.COLUMN_NAME_PRIORITY )

        val cursor = db.query(
            Task.TABLE_NAME,                        // The table to query
            projection,                             // The array of columns to return (pass null to get all)
            null,                            // The columns for the WHERE clause
            null,                         // The values for the WHERE clause
            null,                            // don't group the rows
            null,                             // don't filter by row groups
            "${Task.COLUMN_NAME_DONE} != 0",                           // The sort order
        )

        var tasks = mutableListOf<Task>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_TITLE))
            val done = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_DONE)) == 1
            val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_CATEGORY_ID))
            val priority = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_NAME_PRIORITY))
            val task = Task(id, name, done, categoryId, priority)
            tasks.add(task)
        }
        cursor.close()
        db.close()
        return tasks
    }
}