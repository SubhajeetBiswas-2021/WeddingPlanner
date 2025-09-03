package com.subhajeet.weddingplanner.repo

import com.subhajeet.weddingplanner.model.Task
import com.subhajeet.weddingplanner.model.TaskDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TaskRepository @Inject constructor(private val dao :TaskDao) {


    fun getTasks(): Flow<List<Task>> = dao.getAllTasks()

    suspend fun addTask(task: Task) = dao.insertTask(task)

    suspend fun updateTask(task: Task) = dao.updateTask(task)

    suspend fun deleteTask(task: Task) = dao.deleteTask(task)

    suspend fun getTaskById(id:Int) = dao.getTaskById(id)
}