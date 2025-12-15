package com.example.motounplugged.database

import androidx.room.TypeConverter
import com.example.motounplugged.database.entities.atributeenums.WeekDaysAtribute

class Converters {

    // Converte lista de enums para string (ex: "DOMINGO,TERCA,SEXTA")
    @TypeConverter
    fun fromWeekDaysList(days: List<WeekDaysAtribute>): String {
        return days.joinToString(",") { it.name }
    }

    // Converte string de volta para lista de enums
    @TypeConverter
    fun toWeekDaysList(data: String): List<WeekDaysAtribute> {
        if (data.isEmpty()) return emptyList()
        return data.split(",").mapNotNull { WeekDaysAtribute.fromName(it) }
    }

    // Estes conversores não serão mais necessários se você parar de usar List<Int> para dias
    @TypeConverter
    fun fromListInt(list: List<Int>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toListInt(data: String): List<Int> {
        if (data.isBlank()) return emptyList()
        return data.split(",").map { it.toInt() }
    }
}