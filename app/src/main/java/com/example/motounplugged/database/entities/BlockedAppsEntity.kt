package com.example.motounplugged.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "BlockedApps")
data class BlockedAppsEntity(
    @PrimaryKey (autoGenerate = true) val idApp: Int = 0,
    val nameApp: String,
    val categoryApp: String,
    val uniqueIdApp: Int // Alterar para selecionar o package name correto
    ){
}