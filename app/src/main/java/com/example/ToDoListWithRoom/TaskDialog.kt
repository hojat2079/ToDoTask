package com.example.ToDoListWithRoom

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_task.view.*

class TaskDialog : DialogFragment() {
    private lateinit var addNewTaskCallback: AddNewTaskCallback
    private lateinit var task: Task
    private var edit: Boolean = false
    override fun onAttach(context: Context) {
        super.onAttach(context)
        addNewTaskCallback = context as AddNewTaskCallback
        edit = arguments!!.getBoolean(MainActivity.edited, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialog = AlertDialog.Builder(context).setCancelable(false)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_task, null, false)
        view.et_dialog_save.isSingleLine = true
        if (edit) {
            task = arguments!!.getParcelable(DatabaseManager.TABLE_TASK)!!
            view.et_dialog_save.setText(task.title)
            view.title_task_dialog.text = "ویرایش کردن وظیفه"
        }
        view.btn_dialog_save.setOnClickListener {
            if (!edit) {
                val editTextAddNewTask = view.et_dialog_save.text.toString()
                if (editTextAddNewTask.isNotEmpty()) {
                    val newTask = Task(editTextAddNewTask, false)
                    addNewTaskCallback.onNewTask(newTask)
                    dismiss()
                } else view.etl_dialog_save.error = "عنوان نمی تواند خالی باشد!!!"
            } else {
                val editTextAddNewTask = view.et_dialog_save.text.toString()
                if (editTextAddNewTask.isNotEmpty()) {
                    task.title = editTextAddNewTask
                    addNewTaskCallback.onEditTask(task)
                    dismiss()
                } else view.etl_dialog_save.error = "عنوان نمی تواند خالی باشد!!!"
            }

        }
        alertDialog.setView(view)
        return alertDialog.create()

    }


    interface AddNewTaskCallback {
        fun onNewTask(task: Task)
        fun onEditTask(task: Task)
    }
}