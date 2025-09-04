package com.subhajeet.weddingplanner.GuestModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guests")
data class Guest(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val rsvpStatus: String // Yes, No, Maybe
)