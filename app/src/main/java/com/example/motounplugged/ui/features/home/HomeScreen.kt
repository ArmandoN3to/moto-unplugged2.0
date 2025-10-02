package com.example.motounplugged.ui.features.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AppShortcut
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    Column (
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .fillMaxSize()

    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Moto Unplugged",
                fontStyle = FontStyle.Normal,
                fontSize = 25.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Matenha o Foco e aumente suas metas",
                fontStyle = FontStyle.Normal,
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .align(Alignment.CenterHorizontally)

            )
        }


       //card de perfis
        OutlinedCard (
            border = BorderStroke(1.dp,Color.Black),
            modifier = Modifier
                .size(width = 350.dp, height = 180.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp)
        ){
            Row {
                Text(
                    text = "Trabalho",
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                )

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                ElevatedButton(onClick = { onClick() },
                    modifier = Modifier
                        .padding(horizontal = 10.dp)) {
                    Text(
                        text = "Ativo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Normal,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(1.dp)
                    )
                }
            }
            // linha de descrição de modo
            Row (
            ){
                Text(
                    text = "Bloqueie redes sociais",
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(top = 0.dp)
                        .padding(start = 16.dp)
                )
            }

            Row {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Hash symbol",
                    modifier = Modifier
                        .size(width = 30.dp, height = 18.dp)
                        .padding(start = 16.dp)
                        .padding(top = 7.dp)
                )
                Text(
                    text = "2h",
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black
                )


                Icon(
                    imageVector = Icons.Default.AppShortcut,
                    contentDescription = "Hash symbol",
                    modifier = Modifier
                        .size(width = 30.dp, height = 18.dp)
                        .padding(start = 16.dp)
                        .padding(top = 7.dp)
                )
                Text(
                    text = "12 apps ",
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black
                )
            }
                ElevatedCard(
                    shape = RoundedCornerShape(5.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    ),
                    modifier = Modifier
                        .size(width = 270.dp, height = 50.dp)
                        .padding(start = 15.dp, top = 5.dp)

                ) {
                    Row {
                        Text(
                            text = "Horário da sessão",
                            fontSize = 10.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color.Gray,
                            modifier = Modifier
                                .padding(start = 5.dp),
                            textAlign = TextAlign.Center,
                        )
                    }
                    Row {
                        Text(
                            text = "14:00 -16:00",
                            fontSize = 10.sp,
                            fontStyle = FontStyle.Italic,
                            color = Color.Gray,
                            modifier = Modifier
                                .padding(start = 6.dp, end = 10.dp),
                            textAlign = TextAlign.Center,
                        )

                    }



                }

            }


        }
    }

@Preview(showBackground = true
)
@Composable
private fun Homescreen(){
    MotoUnpluggedTheme {
        HomeScreen(onClick = {})
    }

}