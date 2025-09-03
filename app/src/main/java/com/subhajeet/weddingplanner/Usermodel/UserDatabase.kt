package com.subhajeet.weddingplanner.Usermodel

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 2, exportSchema = true)
abstract class UserDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao
}