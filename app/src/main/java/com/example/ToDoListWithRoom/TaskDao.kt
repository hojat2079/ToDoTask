package com.example.ToDoListWithRoom

import androidx.room.*

@Dao
interface TaskDao {
    @Insert
    fun add(entity: Task): Long

    @Delete
    fun delete(entity: Task): Int

    @Update
    fun update(entity: Task): Int

    @Query("SELECT * FROM tbl_task ")
    fun getAll(): List<Task>

    @Query("SELECT * FROM tbl_task WHERE title LIKE '%' || :query || '%' ")
    fun search(query: String): List<Task>

    @Query("DELETE FROM tbl_task")
    fun clearALl()
}