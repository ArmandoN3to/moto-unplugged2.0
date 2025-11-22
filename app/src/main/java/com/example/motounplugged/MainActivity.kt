// MainActivity.kt
package com.example.motounplugged

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import com.example.motounplugged.ui.navigation.LRNavHost
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme
//import androidx.core.splashscreen.installSplashScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MotoUnpluggedTheme {
                var isLoggedIn by remember { mutableStateOf(false) }

                if (!isLoggedIn) {
                    LRNavHost(
                        onLoginSuccess = { isLoggedIn = true },
                        onRegisterComplete = { /* voltar pra login */ }
                    )
                } else {
                    MotoUnpluggedApp( // <-- CHAMA SUA APP COM SIDEBAR
                        onLogout = { isLoggedIn = false }
                    )
                }
            }
        }
    }
}