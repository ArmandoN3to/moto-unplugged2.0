// MainActivity.kt
package com.example.motounplugged

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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

@Preview
@Composable
fun defaltpreview(){
    MotoUnpluggedTheme {
        MotoUnpluggedApp()
    }
}