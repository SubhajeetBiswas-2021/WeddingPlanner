package com.subhajeet.weddingplanner.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.subhajeet.weddingplanner.model.Task
import com.subhajeet.weddingplanner.repo.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyViewModel @Inject constructor(private val repository: TaskRepository) : ViewModel() {

    // Backing property (private, can be modified inside ViewModel only)
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())

    // Exposed as read-only (UI can only collect, not modify directly)
    val tasks = _tasks.asStateFlow()

    /*init {
        insertDefaultTasksIfEmpty()
        getAllTasks()
    }*/
    init {
        getAllTasks()
        viewModelScope.launch {
            // Check if database is empty and insert defaults
            val currentTasks = repository.getTasks().first()
            if (currentTasks.isEmpty()) {
                insertDefaultTasksIfEmpty()
            }
        }
    }
    private fun insertDefaultTasksIfEmpty() {
        viewModelScope.launch {
            if (_tasks.value.isEmpty()) {
                val defaults = listOf(
                    Task(title = "Venue booking"),
                    Task(title = "Photography"),
                    Task(title = "Catering"),
                    Task(title = "Mehendi"),
                    Task(title = "Sangeet"),
                    Task(title = "Honeymoon booking")
                )
                defaults.forEach { repository.addTask(it) }
            }
        }
    }

    fun getAllTasks() {
        viewModelScope.launch {
            repository.getTasks().collect { list ->
                _tasks.value = list
            }
        }
    }


    fun addTask(title: String) {
        viewModelScope.launch {
            repository.addTask(Task(title = title))
        }
    }

    fun updateTask(task: Task, newTitle: String, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateTask(task.copy(title = newTitle, isCompleted = isCompleted))
        }
    }

    fun toggleComplete(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task.copy(isCompleted = !task.isCompleted))

        }
    }

    fun updateTaskById( newTitle: String, taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            val task = repository.getTaskById(taskId)   // ✅ fetch task from DB
            if (task != null) {
                repository.updateTask(task.copy(title = newTitle, isCompleted = isCompleted))
            }
        }
    }

    fun deleteTaskById(taskId:Int){
        viewModelScope.launch {
            val task = repository.getTaskById(taskId)   // ✅ fetch task from DB
            if (task != null) {
                repository.deleteTask(task)
            }
        }
    }










}