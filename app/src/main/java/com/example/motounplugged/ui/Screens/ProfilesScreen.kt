package com.example.motounplugged.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // Importação importante!
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
import com.example.motounplugged.ui.components.ProfileCard
import com.example.motounplugged.models.Profile


@Composable
fun ProfilesScreen(modifier: Modifier = Modifier) {
    var showToast by remember { mutableStateOf(false) }



    val profiles = listOf(
        Profile("Trabalho", 4),
        Profile("Estudo", 6),
        Profile("Academia", 5),
        Profile("armando", 5),
        Profile("claudio", 5),
        Profile("adasd", 5),
        Profile("adasdfdf", 5)
    )


        Box(
            modifier = modifier
                .padding()
                .fillMaxSize(),
            contentAlignment = Alignment.CenterStart

        ) {


            LazyColumn(
                contentPadding = PaddingValues(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp),
                modifier = Modifier.fillMaxWidth().padding(),
                horizontalAlignment = Alignment.CenterHorizontally


            ) {
                items(
                    items = profiles,
                    key = { profile -> profile.title }
                ) { profile ->
                    ProfileCard(
                        title = profile.title,
                        count = profile.appCount
                    )
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
@Preview
@Composable
fun ProfilesScreenPreview() {
    ProfilesScreen()
}