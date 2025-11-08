// MainActivity.kt
package com.example.motounplugged

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.motounplugged.ui.features.register.RegisterScreen
import com.example.motounplugged.ui.navigation.AppNavHost
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme
//import androidx.core.splashscreen.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MotoUnpluggedTheme {
                val navController = rememberNavController()
                var isLoggedIn by remember { mutableStateOf(false) }

                if (!isLoggedIn) {
                    RegisterScreen(
                        onRegisterComplete = {
                            isLoggedIn = true // sai da tela de cadastro
                        }
                    )
                } else {
                    MotoUnpluggedApp(
                        onLogout = { isLoggedIn = false}) // retorna para register
                }

            }
        }
    }
}