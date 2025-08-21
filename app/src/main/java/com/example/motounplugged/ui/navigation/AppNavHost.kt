package com.example.motounplugged.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Home.route,
        modifier = modifier
    ) {
        composable(AppScreens.Home.route) {
            GenericScreen("Tela Início")
        }
        composable(AppScreens.Schedule.route) {
            GenericScreen("Tela Agendar")
        }
        composable(AppScreens.Stats.route) {
            GenericScreen("Tela Estatísticas")
        }
        composable(AppScreens.Profiles.route) {
            GenericScreen("Tela Perfis")
        }
        composable(AppScreens.User.route) {
            GenericScreen("Tela Usuário")
        }
        composable(AppScreens.Settings.route) {
            GenericScreen("Tela Configurações")
        }
    }
}

@Composable
fun GenericScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = title)
    }
}