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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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



        OutlinedCard (
            border = BorderStroke(1.dp,Color.Black),
            modifier = Modifier
                .size(width = 350.dp, height = 150.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp)
        ){
            Row {
                Text(
                    text = "Trabalho",
                    fontStyle = FontStyle.Normal,
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
                        .padding(0.dp)) {
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

            Row {

                Text(
                    text = "Bloqueie redes sociais",
                    fontStyle = FontStyle.Normal,
                    fontSize = 20.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(top = 6.dp)

                )
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