package com.example.ToDoListWithRoom

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseManager(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db!!.execSQL(
                "CREATE TABLE $TABLE_TASK " +
                        "($TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " $TASK_TITLE TEXT ,$TASK_COMPLETED BOOLEAN);"
            )

        } catch (ex: SQLiteException) {
            Log.e(TAG, "onCreate: $ex")
        }

    }

    companion object {
        private const val TAG = "DatabaseManager"
        const val DATABASE_NAME = "tasks.db"
        const val DATABASE_VERSION = 1
        const val TABLE_TASK = "tasks"
        const val TASK_ID = "id"
        const val TASK_TITLE = "title"
        const val TASK_COMPLETED = "completed"
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_TASK")
        onCreate(db)
    }
}