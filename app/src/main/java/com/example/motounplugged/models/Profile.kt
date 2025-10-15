package com.example.motounplugged.models

data class Profile(val title: String, val appCount: Int)

val sampleProfiles = listOf(
    Profile("Trabalho", 4),
    Profile("Estudo", 6),
    Profile("Academia", 5),
    Profile("Foco total", 5),
    Profile("Foco padrao", 5)
)