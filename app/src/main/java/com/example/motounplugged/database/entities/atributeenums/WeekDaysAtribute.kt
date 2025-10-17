package com.example.motounplugged.database.entities.atributeenums

enum class WeekDaysAtribute (val id_day: Int){
    DOMINGO (1),
    SEGUNDA (2),
    TERCA (3),
    QUARTA (4),
    QUINTA (5),
    SEXTA(6),
    SABADO(7);

    companion object {
        fun fromName(name: String): WeekDaysAtribute? =
            entries.firstOrNull { it.name == name }
    }
}