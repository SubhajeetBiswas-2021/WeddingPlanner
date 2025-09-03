package com.subhajeet.weddingplanner.model

import androidx.room.Dao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 2, exportSchema = true)
abstract class TaskDatabase : RoomDatabase() {         //since abstract room will make the body of this we will not give and is extended with roomdatabase as the class is going to be functional for roomdatabase
    abstract fun getDao(): TaskDao
}