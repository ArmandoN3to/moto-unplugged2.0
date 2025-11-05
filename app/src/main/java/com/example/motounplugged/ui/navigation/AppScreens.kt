package com.example.motounplugged.ui.navigation

//rotas
sealed class AppScreens(val route: String) {
    object Home : AppScreens("home")
    object Schedule : AppScreens("schedule")
    object Stats : AppScreens("stats")
    object Profiles : AppScreens("profiles")
    object User : AppScreens("user")
    object Settings : AppScreens("settings")
    object Streak : AppScreens("streak")
    object CreateProfile : AppScreens("create_profile")
    object SelectApps : AppScreens("select_apps")
    object SplashScreen: AppScreens("splash")
}