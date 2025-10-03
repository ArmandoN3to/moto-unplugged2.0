package com.example.motounplugged.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import com.example.motounplugged.database.entities.atributeenums.WeekDaysAtribute

@Entity(
    tableName = "Sessions",
    foreignKeys = [
        ForeignKey(
            entity = ProfilesEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_user"],
            onDelete = ForeignKey.CASCADE // bom para deletar sessões junto com o perfil
        ),
        ForeignKey(
            entity = WeekDaysAtribute::class,
            parentColumns = ["id"],
            childColumns = ["id_day"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SessionsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val id_user: Int? = 0,   // FK para ProfilesEntity
    val id_day: Int? = 0,    // FK para WeekDaysAtribute
    val namePerfil: String,
    val startHour: String,
    val endHour: String,
    val isActive: Boolean = false,
    val points: Int = 0
)
