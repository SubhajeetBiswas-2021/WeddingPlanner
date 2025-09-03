package com.subhajeet.weddingplanner.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Int =0,
    var title: String,
    var isCompleted: Boolean = false
)

