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
            onDelete = ForeignKey.CASCADE // bom para deletar sessões junto com o perfil
        )
    ]
)
data class SessionsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val idProfile: Int,
    val dayOfWeek: List<WeekDaysAtribute>,
    val namePerfil: String,
    val startHour: String,
    val endHour: String,
    val isActive: Boolean = false,
    val points: Int = 0
)

