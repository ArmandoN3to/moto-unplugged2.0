package com.example.motounplugged.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@Composable
fun PhotoPicker() {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    // mutable é um estado observavel que observa a uri se ela vai ser nula ou não
    // remember vai ser um estado de recomposição da uri
    // selectImageUri verifica esse estado de mudança

    // Launcher para abrir a galeria
    val launcher = rememberLauncherForActivityResult (
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }
    //rememberLauncher criar um laucher e inicia uma intent retornando o resultado
    // ActivityResult é um contrato com seletor, ele que abre a galeria e retorna a uri
    //uri pode ser nula ou não, disso a selectImageUri recebe a uri e troca de estado ou seja a imagem

    Box(
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
            .background(Color.Gray)
            .clickable {
                // Abre o seletor de imagem apenas
                launcher.launch("image/*")
            },
        contentAlignment = Alignment.Center
    ) {
        if (selectedImageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(selectedImageUri),
                contentDescription = "Foto do usuário",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Icon(
                imageVector = Icons.Default.AddAPhoto,
                contentDescription = "Adicionar foto",
                tint = Color.White,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}
