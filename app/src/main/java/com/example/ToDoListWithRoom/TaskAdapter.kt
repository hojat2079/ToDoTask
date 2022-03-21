package com.example.ToDoListWithRoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_task.view.*

class TaskAdapter(
     var tasksList: ArrayList<Task>,
    private val taskItemEventListener: TaskItemEventListener
) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val checkBox = view.checkBox_task as CheckBox
        private val delete = view.img_task_delete as ImageView
        fun onBind(task: Task) {
            checkBox.text = task.title
            checkBox.setOnCheckedChangeListener(null)
            checkBox.isChecked = task.isCompleted
            delete.setOnClickListener {
                taskItemEventListener.onDeleteButtonClick(task)
            }
            view.setOnLongClickListener {
                taskItemEventListener.onUpdateButtonClick(task)
                return@setOnLongClickListener false
            }
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                task.isCompleted = isChecked
                taskItemEventListener.onUpdateChecked(task)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.onBind(tasksList[position])
    }

    override fun getItemCount(): Int = tasksList.size

    fun addItemTask(task: Task) {
        tasksList.add(itemCount, task)
        notifyItemInserted(itemCount)
    }

    fun deleteItemTask(task: Task) {
        for (i in 0 until tasksList.size) {
            if (tasksList[i] == task) {
                tasksList.remove(task)
                notifyItemRemoved(i)
                break
            }
        }
    }

    fun updateItemTask(task: Task) {
        for (i in 0 until tasksList.size) {
            if (tasksList[i].id == task.id) {
                tasksList[i] = task
                notifyItemChanged(i)
                break
            }
        }
    }

    fun clearItems() {
        tasksList.clear()
        notifyDataSetChanged()
    }

    fun setItems(tasks: ArrayList<Task>) {
        this.tasksList = tasks
        notifyDataSetChanged()
    }

    interface TaskItemEventListener {
        fun onDeleteButtonClick(task: Task)
        fun onUpdateButtonClick(task: Task)
        fun onUpdateChecked(task: Task)
    }
}
