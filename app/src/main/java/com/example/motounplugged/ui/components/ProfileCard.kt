package com.example.motounplugged.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import com.example.motounplugged.ui.components.Icon_Edit_Profile

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add

import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.motounplugged.database.dao.ProfilesDao
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.motounplugged.utils.solicitarBiometria

@Composable
fun ProfileCard(
    title: String,
    count: Int,
    modifier: Modifier = Modifier,
    isActive: Boolean,
    isPasswordRequired: Boolean,
    onToggle: (Boolean) -> Unit,
    onClick: () -> Unit
) {
    var checked by remember { mutableStateOf(true) }
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFEFEF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.
                    fillMaxWidth().
                    clickable { onClick() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Icon_Edit_Profile()
            }

            Spacer(Modifier.height(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$count Aplicativos Bloqueados",
                    fontSize = 14.sp,
                    color = Color(0xFF7E7E7E)
                )


                Switch(
                    checked = isActive,
                    onCheckedChange = { novoEstado ->
                        // Lógica: Se estiver ATIVO e querendo DESATIVAR
                        if (isActive && !novoEstado) {

                            // 2. VERIFICAÇÃO AQUI: Só pede biometria se a flag for TRUE
                            if (isPasswordRequired) {
                                solicitarBiometria(context) {
                                    onToggle(false)
                                }
                            } else {
                                // Se não requer senha, desativa direto
                                onToggle(false)
                            }

                        } else {
                            // Está desativado e quer ativar (não pede senha)
                            onToggle(novoEstado)
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color.Black,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = Color.LightGray
                    )
                )
            }
        }
    }
}
