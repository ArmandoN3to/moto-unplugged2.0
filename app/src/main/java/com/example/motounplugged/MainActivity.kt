// MainActivity.kt
package com.example.motounplugged

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.motounplugged.ui.screens.ProfilesScreen
import com.example.motounplugged.ui.screens.ScheduleScreen
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MotoUnpluggedTheme {
                MotoUnpluggedApp()
            }
        }
    }
}