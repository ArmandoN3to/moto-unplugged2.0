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
import com.example.motounplugged.ui.components.ProfileCard

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


        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        ){
            Text(text = "Seus Perfis",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black
            )

        }
        Column (
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .align(Alignment.CenterHorizontally)

        ){  cards.forEach { (title, count) ->
            ProfileCard(title = title, count = count)
        }
        }


//        Row (
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 10.dp),
//            horizontalArrangement = Arrangement.Center
//        ){
//          implementar o floataction button
//            Button(
//                onClick = {
//                    // tela criar perfil
//                },
//                modifier = Modifier
//                    .fillMaxWidth(0.9f)
//                    .height(40.dp)
//                    .align(Alignment.CenterVertically),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Black,
//                    contentColor = Color.White)
//            ) {
//                Icon(
//                    imageVector = Icons.Filled.Add,
//                    contentDescription = "Adicionar"
//                )
//                Spacer(modifier = Modifier
//                    .width(8.dp)
//                )
//                Text(text = "Criar Novo Perfil")
//            }

        FloatingActionButton(
            onClick = { },
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }
        }
    }




@Preview(showBackground = true)
@Composable
fun ProfilesScreenPreview() {
    ProfilesScreen()
}