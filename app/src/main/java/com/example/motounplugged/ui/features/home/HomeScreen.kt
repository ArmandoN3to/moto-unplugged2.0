package com.example.motounplugged.ui.features.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AppShortcut
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.motounplugged.R
import com.example.motounplugged.ui.navigation.AppScreens
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    navController: NavHostController
){
    Column (
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .fillMaxSize()

    ){
        Row (
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ){
            Image(
                painter = painterResource(R.drawable.img),
                contentDescription = "Logo Motounplugged",
                modifier = Modifier
                    .size(width = 50.dp, height = 50.dp)
                    .padding(top = 15.dp)

            )

        }
        Spacer(modifier = Modifier .height(10.dp))


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

        Spacer(modifier = Modifier
            .height(30.dp))


        //card de perfis
        OutlinedCard (

            modifier = Modifier
                .size(width = 350.dp, height = 180.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp)
                .border(
                    width = 3.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(5.dp)
                )
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
                        .padding(horizontal = 10.dp)
                        .padding(top = 2.dp)) {
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
                        text = "14:00 - 16:00",
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
        Spacer( modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 30.dp))

        OutlinedCard (

            modifier = Modifier
                .size(width = 350.dp, height = 160.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
                .border(
                    width = 3.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(5.dp)
                )
        ){
            Row {
                Text(
                    text = "Estastísticas",
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

                ElevatedButton(onClick = {navController.navigate(AppScreens.Stats.route)},
                    modifier = Modifier
                        .size(width = 130.dp, height = 45.dp)
                        .padding(end = 15.dp, top = 10.dp)
                ) {
                    Text(
                        text = "Ver mais",
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        fontStyle = FontStyle.Normal,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(1.dp)
                    )
                }
            }


            Row {

                Text(
                    text = "3h 42min",
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 90.dp)
                )

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                Text(
                    text = "45 min ",
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(end = 100.dp)
                )
            }
            Row (
                modifier = Modifier
                    .padding(top = 5.dp)
            ){

                Text(
                    text = "Hoje",
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(start = 110.dp)
                )

                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                Text(
                    text = "Média",
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(end = 115.dp)
                )
            }
        }
        Spacer( modifier = Modifier.height(10.dp))
        Divider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 30.dp))

        OutlinedCard (
            modifier = Modifier
                .size(width = 350.dp, height = 190.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
                .border(
                    width = 3.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(5.dp)
                )
        ){
            Row {
                Text(
                    text = "Acesso rápido",
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }
            ElevatedButton(
                border = BorderStroke(2.dp,Color.LightGray),
                onClick = { navController.navigate(AppScreens.Profiles.route) },
                modifier = Modifier
                    .padding(top = 5.dp)
                    .padding(start = 15.dp)
                    .size(width = 320.dp, height = 40.dp)
            ) {
                Text(
                    text = "Gerenciar Perfis",
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Normal,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(1.dp)
                )
            }
            ElevatedButton(
                border = BorderStroke(2.dp,Color.LightGray),
                onClick = { navController.navigate(AppScreens.Schedule.route) },
                modifier = Modifier
                    .padding(top = 10.dp)
                    .padding(start = 15.dp)
                    .size(width = 320.dp, height = 40.dp)
            ) {
                Text(
                    text = "Agendar",
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Normal,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(1.dp)
                )
            }
        }





    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    MotoUnpluggedTheme {
        val navController = rememberNavController()
        HomeScreen(
            navController = navController,
            onClick = {}
        )
    }
}

