package com.example.motounplugged.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.motounplugged.ui.features.login.LoginScreen
import com.example.motounplugged.ui.features.register.RegisterScreen
import com.example.motounplugged.ui.features.terms_and_conditions.TermsAndConditionsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LRNavHost(
    onLoginSuccess: () -> Unit,
    onRegisterComplete: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.Login.route
    ) {
        composable(AppScreens.Login.route) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(AppScreens.Register.route)
                },
                onLoginSuccess = {
                    onLoginSuccess()
                }
            )
        }

        composable(AppScreens.Register.route) {
            RegisterScreen(
                onRegisterComplete = {
                    navController.popBackStack() // volta pra login
                },
                onBackToLogin = {
                    navController.popBackStack()
                },
                onNavigateToTermsAndConditions = {
                    navController.navigate(AppScreens.TermsAndConditionsScreen.route)
                }
            )
        }

        composable(AppScreens.TermsAndConditionsScreen.route){
            TermsAndConditionsScreen(
                onBack = {navController.popBackStack()},
                onNavigateToTermsAndConditions = {navController.navigate(AppScreens.TermsAndConditionsScreen.route)}
            )
        }
    }
}
