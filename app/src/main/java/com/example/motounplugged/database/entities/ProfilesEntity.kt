package com.example.motounplugged.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Profiles")
data class ProfilesEntity (
    @PrimaryKey (autoGenerate = true) val idProfile: Int = 0,
    val profileName: String,
    val appCount: Int = 0,
    val isImmediatelyActive: Boolean = false,
    val wallpaperUri: String? = null,
    val duration: Int = 0,
    val passwordRequired: Boolean = false,
    val batterySave: Boolean = false
)

