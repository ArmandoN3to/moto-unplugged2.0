package com.example.motounplugged.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.motounplugged.ui.components.ProfileCard
import com.example.motounplugged.ui.features.createprofile.CreateProfileUiState
//import com.example.motounplugged.models.sampleProfiles
import com.example.motounplugged.ui.features.createprofile.sampleProfiles
import com.example.motounplugged.ui.features.profiles.ProfilesScreenViewModel


@Composable
fun ProfilesScreen(
    viewModel: ProfilesScreenViewModel, // chama a minha model que tem o flow
    modifier: Modifier = Modifier) {

       // profiles vai receber o meu stateflow e coletar esse estado
       val profiles by viewModel.profiles.collectAsState()

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
                    key = { it.id}
                ) { profile ->
                    ProfileCard(
                        title = profile.ProfileName,
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
/*@Preview
@Composable
fun ProfilesScreenPreview() {
    ProfilesScreen()
}*/