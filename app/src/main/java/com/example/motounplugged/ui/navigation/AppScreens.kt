package com.example.motounplugged.ui.navigation

sealed class AppScreens(val route: String) {
    object Home : AppScreens("home")
    object Schedule : AppScreens("schedule")
    object Stats : AppScreens("stats")
    object Profiles : AppScreens("profiles")
    object User : AppScreens("user")
    object Settings : AppScreens("settings")
}