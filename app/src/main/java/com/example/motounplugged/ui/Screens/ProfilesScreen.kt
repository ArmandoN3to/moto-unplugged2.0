package com.example.motounplugged.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

import com.example.motounplugged.ui.components.CustomToast
import com.example.motounplugged.ui.components.Icon_Edit_Profile

@Composable
fun ProfilesScreen(modifier: Modifier = Modifier) {
    var showToast by remember { mutableStateOf(false) }

    val cards = listOf(
        Pair("Trabalho", 4),
        Pair("Estudo", 6),
        Pair("Conforto", 6)
    )

    Column(
        modifier = modifier
            .fillMaxWidth()

    ) {
        Row (modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = "Escolha seu foco e organize sua experiência",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif,
            )

        }

        Spacer( modifier = Modifier
            .height(50.dp))

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        ){
            Text(text = "Seus Perfis",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 13.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black
            )
        }


        cards.forEach { (title, count) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEFEFEF)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(modifier = modifier.padding(16.dp)) {
                    Row (
                        modifier = modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF000000))
                        Icon_Edit_Profile()

                    }

                    Spacer(Modifier.height(16.dp))
                    Text("$count Aplicativos Bloqueados", fontSize = 14.sp, color = Color(0xFF7E7E7E))
                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = { showToast = true },
                        modifier = modifier
                            .fillMaxWidth(0.9f)
                            .height(40.dp)
                            .align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )

                    ) {

                        Text("Ativar Perfil")
                    }
                }
            }
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ){

            Button(
                onClick = {
                    // tela criar perfil
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(40.dp)
                    .align(Alignment.CenterVertically),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Adicionar"
                )
                Spacer(modifier = Modifier
                    .width(8.dp)
                )
                Text(text = "Criar Novo Perfil")
            }

        }
        if (showToast) {
            Spacer(modifier = Modifier.height(8.dp))
            CustomToast("Perfil Ativado")
            LaunchedEffect(Unit) {
                delay(2000)
                showToast = false
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilesScreenPreview() {
    ProfilesScreen()
}