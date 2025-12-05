package com.example.motounplugged.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Immutable
// cria as listas de items
@Immutable
object DrawerItems {
    val Topitems = listOf(
        NavigationItem("Início", Icons.Default.Home, AppScreens.Home.route),
        NavigationItem("Agendar", Icons.Default.DateRange, AppScreens.Schedule.route),
        NavigationItem("Estatísticas", Icons.Default.Face, AppScreens.Stats.route),
        NavigationItem("Perfis", Icons.Default.Person, AppScreens.Profiles.route),
        NavigationItem("Ofensiva", Icons.Default.Star, AppScreens.Streak.route),
    )
    val BottomItems = listOf(
        NavigationItem("Configurações", Icons.Default.Settings, AppScreens.Settings.route),
        NavigationItem("Sair", Icons.Default.ExitToApp, "logout_action")
    )
}
