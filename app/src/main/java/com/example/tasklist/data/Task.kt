package com.example.tasklist.data

import android.provider.BaseColumns

data class Task(var id: Int, var name: String, var done: Boolean = false, var categoryId: Int?=null, var priority:Int) {

    companion object {
        const val TABLE_NAME = "Tasks"
        const val COLUMN_NAME_TITLE = "name"
        const val COLUMN_NAME_DONE = "done"
        const val COLUMN_NAME_CATEGORY_ID = "parentId"
        const val COLUMN_NAME_PRIORITY = "priority"


        const val SQL_CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_NAME_TITLE TEXT," +
                    "$COLUMN_NAME_DONE INTEGER,"+
                    "$COLUMN_NAME_CATEGORY_ID INTEGER FOREIGN KEY ,"+
                    "$COLUMN_NAME_PRIORITY INTEGER)"

        const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}