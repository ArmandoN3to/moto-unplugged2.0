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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.ui.components.EditProfileDialog
import com.example.motounplugged.ui.components.ProfileCard
import com.example.motounplugged.ui.features.profiles.ProfilesScreenViewModel


@Composable
fun ProfilesScreen(
    viewModel: ProfilesScreenViewModel, // chama a minha model que tem o flow
    navController: NavHostController,
    modifier: Modifier = Modifier) {

       // profiles vai receber o meu stateflow e coletar esse estado
       val profiles by viewModel.profiles.collectAsState()
       var editingProfile by remember { mutableStateOf<ProfilesEntity?>(null) }

        Box(
            modifier = modifier
                .padding()
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter

        ) {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 10.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    items = profiles,
                    key = { it.idProfile}
                ) { profile ->
                    ProfileCard(
                        title = profile.ProfileName,
                        count = profile.appCount,
                        isActive = profile.isImmediatelyActive,
                        onToggle = { newValue ->
                            if (newValue){
                                profiles.forEach { other ->
                                    if (other.idProfile != profile.idProfile && other.isImmediatelyActive){
                                        viewModel.update(other.copy(isImmediatelyActive = false))
                                    }
                                }
                            }
                            val updated = profile.copy(isImmediatelyActive = newValue)
                            viewModel.update(updated)
                        },
                        onClick = {
                            navController.navigate("create_profile_screen?profileId=${profile.idProfile}")
                        }
                    )
                }
            }
        }
        // se um perfil foi selecionado para edição
        editingProfile?.let { profile ->
            EditProfileDialog(
                profile = profile,
                onDismiss = { editingProfile = null },
                onSave = { updated ->
                    viewModel.update(updated)
                    editingProfile = null
                }
            )
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

