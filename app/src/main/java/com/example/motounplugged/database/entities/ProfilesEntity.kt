package com.example.motounplugged.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Profiles")
data class ProfilesEntity (
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val ProfileName: String,
    val appCount: Int = 0,
    val isImmediatelyActive: Boolean = true
)

