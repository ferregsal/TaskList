package com.example.tasklist.data

import android.provider.BaseColumns

data class Category(var id: Int, var name: String, var priority: Int) {

    companion object {
        const val TABLE_NAME = "Categories"
        const val COLUMN_NAME_TITLE = "name"
        const val COLUMN_NAME_PRIORITY = "priority"


        const val SQL_CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_NAME_TITLE TEXT," +
                    "$COLUMN_NAME_PRIORITY INTEGER)"

        const val SQL_DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}