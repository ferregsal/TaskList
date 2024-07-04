
import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import com.example.tasklist.data.Category
import com.example.tasklist.utils.DatabaseManager


class CategoryDAO(context: Context) {

    private val DatabaseManager: DatabaseManager = DatabaseManager(context)

    fun insert(category: Category) {
        val db = DatabaseManager.writableDatabase

        val values = ContentValues()
        values.put(Category.COLUMN_NAME_TITLE, category.name)
        values.put(Category.COLUMN_NAME_PRIORITY, category.priority)

        val newRowId = db.insert(Category.TABLE_NAME, null, values)
        category.id = newRowId.toInt()
    }

    fun update(category: Category) {
        val db = DatabaseManager.writableDatabase

        val values = ContentValues()
        values.put(Category.COLUMN_NAME_TITLE, category.name)
        values.put(Category.COLUMN_NAME_PRIORITY, category.priority)

        val updatedRows = db.update(
            Category.TABLE_NAME,
            values,
            "${BaseColumns._ID} = ${category.id}",
            null
        )
    }

    fun delete(category: Category) {
        val db = DatabaseManager.writableDatabase

        val deletedRows = db.delete(Category.TABLE_NAME, "${BaseColumns._ID} = ${category.id}", null)
    }

    fun find(id: Int) : Category? {
        val db = DatabaseManager.readableDatabase

        val projection = arrayOf(BaseColumns._ID, Category.COLUMN_NAME_TITLE, Category.COLUMN_NAME_PRIORITY)

        val cursor = db.query(
            Category.TABLE_NAME,                        // The table to query
            projection,                             // The array of columns to return (pass null to get all)
            "${BaseColumns._ID} = $id",      // The columns for the WHERE clause
            null,                         // The values for the WHERE clause
            null,                            // don't group the rows
            null,                             // don't filter by row groups
            null                           // The sort order
        )

        var category:Category? = null
        if (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(Category.COLUMN_NAME_TITLE))
            val priority = cursor.getInt(cursor.getColumnIndexOrThrow(Category.COLUMN_NAME_PRIORITY))

            category = Category(id, name, priority)
        }
        cursor.close()
        db.close()
        return category
    }

    fun findAll() : List<Category> {
        val db = DatabaseManager.readableDatabase

        val projection = arrayOf(BaseColumns._ID, Category.COLUMN_NAME_TITLE, Category.COLUMN_NAME_PRIORITY)

        val cursor = db.query(
            Category.TABLE_NAME,                        // The table to query
            projection,                             // The array of columns to return (pass null to get all)
            null,                            // The columns for the WHERE clause
            null,                         // The values for the WHERE clause
            null,                            // don't group the rows
            null,                             // don't filter by row groups
            null                           // The sort order
        )

        var categories = mutableListOf<Category>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(Category.COLUMN_NAME_TITLE))
            val priority = cursor.getInt(cursor.getColumnIndexOrThrow(Category.COLUMN_NAME_PRIORITY))

            val category = Category(id, name, priority)
            categories.add(category)
        }
        cursor.close()
        db.close()
        return categories
    }
}