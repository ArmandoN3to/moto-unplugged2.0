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
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.motounplugged.R
import com.example.motounplugged.ui.navigation.AppScreens

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeScreenViewModel
){

    val uiState by viewModel.uiState.collectAsState()

    Column (
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 10.dp)
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
                    .padding(top = 10.dp)

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
        if (uiState.activeProfile == null) {
            Text(
                text = "Nenhum perfil ativo",
                fontStyle = FontStyle.Italic,
                fontSize = 20.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
                    .padding(start = 85.dp)

            )
        } else {
            val profile = uiState.activeProfile

            OutlinedCard(
                modifier = Modifier
                    .size(width = 350.dp, height = 200.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp)
                    .border(
                        width = 3.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(5.dp)
                    )
            ) {
                Column(Modifier.padding(16.dp)) {

                    Text(
                        text = profile!!.profileName,
                        fontStyle = FontStyle.Italic,
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "Duração: ${profile!!.duration} min",
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Normal,
                        color = Color.Black
                    )

                    Text(
                        text = "Apps bloqueados: ${uiState.blockedAppsCount}",
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Normal,
                        color = Color.Black
                    )

                    Spacer(Modifier.height(12.dp))

                    //Adicionar próxima sessão
                    uiState.nextSession?.let { session ->
                        Text(
                            text = "Próxima sessão: ${session.startHour
                                .toString()
                                .padStart(2, '0')}:${session.startMinute
                                .toString()
                                .padStart(2, '0')}",
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    ElevatedButton(
                        onClick = { viewModel.onActivateProfile() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Ativar",color = Color.DarkGray)
                    }

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
                        color = Color.DarkGray,
                        modifier = Modifier
                            .padding(1.dp)
                    )
                }
            }


            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
            ) {

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
                    color = Color.DarkGray,
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
                    color = Color.DarkGray,
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
                    color = Color.DarkGray,
                    modifier = Modifier
                        .padding(1.dp)
                )
            }
            ElevatedButton(
                border = BorderStroke(2.dp,Color.LightGray),
                onClick = {navController.navigate(AppScreens.Schedule.route)},
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
                    color = Color.DarkGray,
                    modifier = Modifier
                        .padding(1.dp)
                )
            }
        }





    }
}


