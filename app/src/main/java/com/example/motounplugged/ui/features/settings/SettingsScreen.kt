package com.example.motounplugged.ui.features.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.motounplugged.ui.components.NavigationSettingsCard

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navController: NavController
){
    Column (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Configurações",
                fontSize = 25.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Gerencie suas preferências e ajustes",
                fontSize = 20.sp,
                fontStyle = FontStyle.Normal,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            NavigationSettingsCard (
                title = "Geral",
                onClick = {
                    navController.navigate("general_settings")
//                    println("Navegando para Geral")
                }
            )

            NavigationSettingsCard (
                title = "Sobre",
                onClick = {
                    navController.navigate("about_settings")
//                    println("Navegando para Geral")
                }
            )
        }

    }
}
