package com.example.motounplugged.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.ui.features.profiles.ProfilesScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectOptions(
    viewModel: ProfilesScreenViewModel,
    onProfileSelected: (ProfilesEntity) -> Unit = {} // callback opcional
) {
    // Estado local do menu suspenso
    var expanded by remember { mutableStateOf(false) }

    // Texto exibido no campo
    var selectedOption by remember { mutableStateOf("Selecione o Perfil") }

    // Coleta a lista de perfis do ViewModel
    val profiles by viewModel.profiles.collectAsState()

    // Container principal com estilo de menu suspenso do Material 3
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        // Campo principal (área clicável que abre o menu)
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true, // impede digitação manual
            label = { Text(text = "Selecione o perfil", color = Color.Gray) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor() // necessário para posicionamento correto
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Color.Gray, RoundedCornerShape(12.dp)),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        // Lista de opções exibida ao expandir
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            profiles.forEach { profile ->
                DropdownMenuItem(
                    text = { Text(profile.profileName) }, // usa o nome real do perfil
                    onClick = {
                        selectedOption = profile.profileName
                        expanded = false
                        onProfileSelected(profile) // notifica a seleção para o pai
                    }
                )
            }
        }
    }
}
