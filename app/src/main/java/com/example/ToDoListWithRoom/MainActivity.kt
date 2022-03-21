package com.example.ToDoListWithRoom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), TaskDialog.AddNewTaskCallback,
    TaskAdapter.TaskItemEventListener {
    private lateinit var databaseManager: DatabaseManager
    lateinit var taskDao: TaskDao
    private lateinit var taskList: ArrayList<Task>
    private lateinit var taskAdapter: TaskAdapter

    companion object {
        const val edited: String = "edited"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        taskDao = AppDatabase.getAppDatabase(this).getTaskDao()
        databaseManager = DatabaseManager(this)
        setContentView(R.layout.activity_main)
        taskList = taskDao.getAll() as ArrayList<Task>
        taskAdapter = TaskAdapter(taskList as java.util.ArrayList<Task>, this)
        rv_tasks.adapter = taskAdapter
        rv_tasks.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        clear_item_task.setOnClickListener {
            taskDao.clearALl()
            taskAdapter.clearItems()
        }
        fab_main_addTask.setOnClickListener {
            val taskDialog = TaskDialog()
            val bundle = Bundle()
            bundle.putBoolean(edited, false)
            taskDialog.arguments = bundle
            taskDialog.show(supportFragmentManager, null)

        }
        cancel_search_task.setOnClickListener {
            et_main_search.setText("")
        }
        et_main_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val tasks: ArrayList<Task> = if (et_main_search.text.isNotEmpty()) {
                    cancel_search_task.visibility = View.VISIBLE
                    taskDao.search(et_main_search.text.toString()) as ArrayList<Task>
                } else {
                    cancel_search_task.visibility = View.INVISIBLE
                    taskDao.getAll() as ArrayList<Task>
                }
                taskAdapter.setItems(tasks)
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    override fun onNewTask(task: Task) {
        val id = taskDao.add(task)
        if (id > -1) {
            task.id = id
            taskAdapter.addItemTask(task)
            rv_tasks.smoothScrollToPosition(taskAdapter.itemCount)
        }
    }

    override fun onEditTask(task: Task) {
        val result = taskDao.update(task)
        if (result > -1) {
            taskAdapter.updateItemTask(task)
        }
    }

    override fun onDeleteButtonClick(task: Task) {
        val result = taskDao.delete(task)
        if (result > -1) {
            taskAdapter.deleteItemTask(task)
        }


    }

    override fun onUpdateButtonClick(task: Task) {
        val taskDialog = TaskDialog()
        val bundle = Bundle()
        bundle.putParcelable(DatabaseManager.TABLE_TASK, task)
        bundle.putBoolean(edited, true)
        taskDialog.arguments = bundle
        taskDialog.show(supportFragmentManager, null)
    }

    override fun onUpdateChecked(task: Task) {
        taskDao.update(task)
    }


}