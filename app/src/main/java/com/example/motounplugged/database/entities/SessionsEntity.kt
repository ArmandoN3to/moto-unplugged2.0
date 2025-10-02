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
          childColumns = ["id_user"]
        ),
        ForeignKey(
            entity = WeekDaysAtribute::class,
            parentColumns = ["id"],
            childColumns = ["id_day"]
        )
    ])
data class SessionsEntity (
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val namePerfil: String,
    val startHour: String,
    val endHour: String,
    val isActive: Boolean = false,
    val points: Int = 0
)