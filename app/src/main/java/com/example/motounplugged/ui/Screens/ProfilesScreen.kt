package com.example.motounplugged.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.motounplugged.ui.components.ProfileCard

import com.example.motounplugged.ui.components.CustomToast
import com.example.motounplugged.ui.components.Icon_Edit_Profile

@Composable
fun ProfilesScreen(modifier: Modifier = Modifier) {
    var showToast by remember { mutableStateOf(false) }

    val cards = listOf(
        Pair("Trabalho", 4),
        Pair("Estudo", 6),
        Pair("Conforto", 6)
    )

    Scaffold(
        floatingActionButton = {
            FAB_new_profile {
                // Ação ao clicar no FAB
                showToast = true
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                Text(
                    text = "Seus Perfis",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color.Black
                )
            }

            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                cards.forEach { (title, count) ->
                    ProfileCard(title = title, count = count)
                }
            }
        }
    }
}

@Composable
fun FAB_new_profile(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = Color.LightGray,
        contentColor = Color.Black
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Adicionar perfil")
    }
}
