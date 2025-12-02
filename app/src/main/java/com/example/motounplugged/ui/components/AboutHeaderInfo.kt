package com.example.motounplugged.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppHeaderInfo(
    icon: Painter,     // O ícone é recebido aqui (pode ser qualquer imagem)
    title: String,     // Ex: "Sobre o Adobe Express"
    version: String,   // Ex: "29.3.1"
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), // Margem padrão da tela
        horizontalAlignment = Alignment.Start // Alinhado à esquerda
    ) {
        // 1. O Ícone
        Image(
            painter = icon,
            contentDescription = null, // Decorativo neste contexto
            modifier = Modifier
                .size(72.dp) // Tamanho similar ao da imagem (relativamente grande)
                .clip(RoundedCornerShape(18.dp)) // Arredonda os cantos (Squircle)
        )

        Spacer(modifier = Modifier.height(24.dp)) // Espaço entre ícone e texto

        // 2. Título
        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(4.dp)) // Espaço pequeno entre título e versão

        // 3. Versão
        Text(
            text = "Versão $version",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }
}