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
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return list.joinToString(",") // junta em uma string separada por vírgula
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        return if (data.isEmpty()) emptyList() else data.split(",")
    }
}