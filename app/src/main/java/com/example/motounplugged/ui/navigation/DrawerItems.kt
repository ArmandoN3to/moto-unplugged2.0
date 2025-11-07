package com.example.motounplugged.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Immutable
// cria as listas de items
@Immutable
object DrawerItems {
    val Topitems = listOf(
        NavigationItem("Início", Icons.Default.Home, AppScreensRouter.Home.route),
        NavigationItem("Agendar", Icons.Default.DateRange, AppScreensRouter.Schedule.route),
        NavigationItem("Estatísticas", Icons.Default.Face, AppScreensRouter.Stats.route),
        NavigationItem("Perfis", Icons.Default.Person, AppScreensRouter.Profiles.route),
        NavigationItem("Ofensiva", Icons.Default.Star, AppScreensRouter.Streak.route),
    )
    val BottomItems = listOf(
        NavigationItem("Usuário", Icons.Default.AccountCircle, AppScreensRouter.User.route),
        NavigationItem("Configurações", Icons.Default.Settings, AppScreensRouter.Settings.route),
        NavigationItem("Sair", Icons.Default.ExitToApp, "logout_action")
    )
}
