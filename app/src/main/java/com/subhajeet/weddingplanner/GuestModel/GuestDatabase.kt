package com.subhajeet.weddingplanner.GuestModel

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Guest::class], version = 1, exportSchema = false)
abstract class GuestDatabase : RoomDatabase() {
    abstract fun guestDao(): GuestDao
}