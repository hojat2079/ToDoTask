package com.example.ToDoListWithRoom

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.ToDoListWithRoom.AppDatabase.Companion.DATABASE_VERSION

@Database(version = DATABASE_VERSION, exportSchema = false, entities = [Task::class])
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private var appDatabase: AppDatabase? = null
        private const val DATABASE_NAME = "tasks.db"
        const val DATABASE_VERSION = 1
        fun getAppDatabase(context: Context): AppDatabase {
            if (appDatabase == null) {
                appDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries().build()
            }
            return appDatabase as AppDatabase
        }
    }

    abstract fun getTaskDao(): TaskDao

}