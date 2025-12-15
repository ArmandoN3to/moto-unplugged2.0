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
            parentColumns = ["idProfile"],
            childColumns = ["idProfile"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SessionsEntity(
    @PrimaryKey(autoGenerate = true)
    val idSession: Int = 0,
    val idProfile: Int,
    val daysOfWeek: List<WeekDaysAtribute>,
    val startHour: Int,
    val startMinute: Int
)

