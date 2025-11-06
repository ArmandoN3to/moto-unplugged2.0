package com.example.motounplugged.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "BlockedApps",
        foreignKeys = [
            ForeignKey(
                entity = ProfilesEntity::class, // Entidade pai
                parentColumns = ["idProfile"], // Chave estrangeira
                childColumns = ["blockedProfileId"],  // Chave estrangeira na tabela filha
                onDelete = ForeignKey.CASCADE  // Deleta os apps quando o perfil é removido
            )
        ])
data class BlockedAppsEntity(
    @PrimaryKey (autoGenerate = true) val idBlockedApp: Int = 0,
    val nameApp: String,
    val categoryApp: String,
    val packageName: String, // Alterar para selecionar o package name correto
    val blockedProfileId: Int
    ){
}