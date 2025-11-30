package com.example.motounplugged.ui.navigation

//rotas
sealed class AppScreens(val route: String) {
    object Home : AppScreens("home")
    object Schedule : AppScreens("schedule")
    object Stats : AppScreens("stats")
    object Profiles : AppScreens("profiles")
    object Settings : AppScreens("settings")
    object Streak : AppScreens("streak")
    object CreateProfile : AppScreens("create_profile")
    object SelectApps : AppScreens("select_apps")
    object Register : AppScreens("register")
    object Login : AppScreens("login")
    object TermsAndConditionsScreen : AppScreens("terms_and_conditions")
    object SplashScreen: AppScreens("splash")
}