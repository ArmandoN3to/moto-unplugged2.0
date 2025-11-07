package com.example.motounplugged.ui.features.profiles

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.ui.components.EditProfileDialog
import com.example.motounplugged.ui.components.ProfileCard
import com.example.motounplugged.ui.features.profiles.ProfilesScreenViewModel

@Composable
fun ProfilesScreen(
    viewModel: ProfilesScreenViewModel,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // Coleta os perfis do Flow em tempo real
    val profiles by viewModel.profiles.collectAsState()

    var editingProfile by remember { mutableStateOf<ProfilesEntity?>(null) }

    Scaffold(
        floatingActionButton = {
            FABNewProfile {
                navController.navigate("create_profile_screen")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Perfis de Foco",
                fontSize = 25.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "Crie e personalize seus perfis",
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 5.dp, bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    items = profiles,
                    key = { it.idProfile }
                ) { profile ->
                    ProfileCard(
                        title = profile.ProfileName,
                        count = profile.appCount,
                        isActive = profile.isImmediatelyActive,
                        onToggle = { newValue ->
                            viewModel.toggleActiveProfile(profile, newValue)
                        },
                        onClick = {
                            // Navega para a tela de edição do perfil e seus apps bloqueados
                            navController.navigate("create_profile_screen?profileId=${profile.idProfile}")
                        }
                    )
                }
            }

            // Diálogo de edição de perfil
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
    }
}

/**
 * Botão flutuante para criar novo perfil.
 */
@Composable
fun FABNewProfile(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = Color.LightGray,
        contentColor = Color.Black
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Adicionar perfil")
    }
}
