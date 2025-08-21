// DrawerItems.kt
package com.example.motounplugged.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Immutable

@Immutable
object DrawerItems {
    val Topitems = listOf(
        NavigationItem("Início", Icons.Default.Home, AppScreens.Home.route),
        NavigationItem("Agendar", Icons.Default.DateRange, AppScreens.Schedule.route),
        NavigationItem("Estatísticas", Icons.Default.Face, AppScreens.Stats.route),
        NavigationItem("Perfis", Icons.Default.Person, AppScreens.Profiles.route),
    )
    val BottomItems = listOf(
        NavigationItem("Usuário", Icons.Default.AccountCircle, AppScreens.User.route),
        NavigationItem("Configurações", Icons.Default.Settings, AppScreens.Settings.route),
        NavigationItem("Logout", Icons.Default.ExitToApp, "logout_action")
    )
}
