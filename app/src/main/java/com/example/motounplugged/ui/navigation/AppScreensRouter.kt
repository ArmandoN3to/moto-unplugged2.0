package com.example.motounplugged.ui.navigation

//rotas
sealed class AppScreensRouter(val route: String) {
    object Home : AppScreensRouter("home")
    object Schedule : AppScreensRouter("schedule")
    object Stats : AppScreensRouter("stats")
    object Profiles : AppScreensRouter("profiles")
    object User : AppScreensRouter("user")
    object Settings : AppScreensRouter("settings")
    object Streak : AppScreensRouter("streak")
    object CreateProfile : AppScreensRouter("create_profile")
    object SelectApps : AppScreensRouter("select_apps")
    object Register : AppScreensRouter("register")
    object TermsAndConditionsScreen : AppScreensRouter("terms_and_conditions")
}