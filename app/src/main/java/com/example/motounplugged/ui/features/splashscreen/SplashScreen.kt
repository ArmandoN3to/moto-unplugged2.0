package com.example.motounplugged.ui.features.splashscreen
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController


@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is SplashUiState.NavigateToHome -> {
                navController.navigate("home") {
                    // Remove a splash da pilha
                    popUpTo("splash") { inclusive = true }
                }
            }
            is SplashUiState.NavigateToLogin -> {
                navController.navigate("login") {
                    // Remove a splash da pilha
                    popUpTo("splash") { inclusive = true }
                }
            }
            else -> {} // Continua no estado Loading
        }
    }

    // A UI que o usuário vê enquanto o ViewModel decide
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}