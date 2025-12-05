package com.example.motounplugged.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motounplugged.database.entities.ProfilesEntity


@Composable
fun EditProfileDialog(
    profile: ProfilesEntity,
    onDismiss: () -> Unit,
    onSave: (ProfilesEntity) -> Unit
) {
    var name by remember { mutableStateOf(profile.profileName) }
    var count by remember { mutableStateOf(profile.appCount.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar Perfil") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome do Perfil") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = count,
                    onValueChange = { count = it },
                    label = { Text("Número de apps") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val updatedProfile = profile.copy(
                    profileName = name,
                    appCount = count.toIntOrNull() ?: profile.appCount
                )
                onSave(updatedProfile)
            }) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
