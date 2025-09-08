package com.example.motounplugged.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults


import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.motounplugged.models.sampleProfiles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectOptions() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Selecione o Perfil") }

    val profiles = sampleProfiles.map { it.title }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        // Campo principal (mostra o valor selecionado)
        TextField(

            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(
                text = "Selecione o perfil",
                color = Color.Gray) },

            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)) // arredonda os cantos
                .border(1.dp, Color.Gray, RoundedCornerShape(12.dp)), // adiciona borda
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White

            )
        )

        // Lista de opções
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            profiles.forEach { profile ->
                DropdownMenuItem(
                    text = { Text(profile) },
                    onClick = {
                        selectedOption = profile
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SelectOptionsPreview() {
    MaterialTheme {
        SelectOptions()
    }
}
