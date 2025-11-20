package com.example.motounplugged.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NavigationSettingsCard(
    title: String,
    onClick: () -> Unit // Parâmetro para a ação de clique/navegação
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // Torna a área clicável
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Espaçamento interno igual ao da imagem
            horizontalArrangement = Arrangement.SpaceBetween, // Texto na esquerda, ícone na direita
            verticalAlignment = Alignment.CenterVertically
        ) {
            // O Texto "Geral", "Perfil", etc.
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )

            // O ícone da seta (>)
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Ir para $title",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }

        // A linha divisória na parte inferior (como na imagem)
        HorizontalDivider(
            thickness = 0.5.dp,
            color = Color.LightGray.copy(alpha = 0.5f)
        )
    }
}