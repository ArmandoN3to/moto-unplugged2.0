// DrawerItems.kt
package com.example.motounplugged.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Immutable

@Immutable
object DrawerItems {
    val Topitems = listOf(
        NavigationItem("Início", Icons.Default.Home, "home"),
        NavigationItem("Agendar", Icons.Default.DateRange, "schedule"),
        NavigationItem("Estatísticas", Icons.Default.Face, "stats"),
        NavigationItem("Perfis", Icons.Default.Person, "profiles"),

    )
    val BottomItems = listOf(
        NavigationItem("Usuário", Icons.Default.AccountCircle, "user"),
        NavigationItem("Configurações", Icons.Default.Settings, "settings"),
        NavigationItem("Logout", Icons.Default.ExitToApp, "logout")
    )
}
