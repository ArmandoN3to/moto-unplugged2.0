// MainActivity.kt
package com.example.motounplugged

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme
//import androidx.core.splashscreen.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            MotoUnpluggedTheme {
                MotoUnpluggedApp()
            }
        }
    }
}