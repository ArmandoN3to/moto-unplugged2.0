package com.example.motounplugged.ui.features.home

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.motounplugged.R
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.models.Profile
import com.example.motounplugged.services.FocusActions
import com.example.motounplugged.services.FocusModeService
import com.example.motounplugged.ui.navigation.AppScreens

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeScreenViewModel
){

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val isFocusActive by viewModel.isFocusActive.collectAsState()

    val keyguardManager =
        context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager


    fun startFocusService(context: Context, uiState: HomeUiState) {
        val profile = uiState.activeProfile ?: return

        val intent = Intent(context, FocusModeService::class.java).apply {
            action = FocusActions.ACTION_START
            putExtra("profileId", profile.idProfile)
            putExtra("durationMinutes", profile.duration)
            putExtra("interruptions", profile.interruptions)
            putExtra("passwordRequired", profile.passwordRequired)
        }

        ContextCompat.startForegroundService(context, intent)
    }

    fun stopFocusService(context: Context) {
        val intent = Intent(context, FocusModeService::class.java).apply {
            action = FocusActions.ACTION_STOP
        }
        context.startService(intent)
    }

    val unlockLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            stopFocusService(context)
        }
    }

    fun requestDeviceAuthentication() {
        if (!keyguardManager.isDeviceSecure) {
            // Aqui você pode usar Snackbar / Toast
            Toast.makeText(
                context,
                "Defina uma senha no dispositivo para desativar o modo foco",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val intent = keyguardManager.createConfirmDeviceCredentialIntent(
            "Confirmar identidade",
            "Digite a senha do dispositivo para sair do modo foco"
        )

        unlockLauncher.launch(intent)
    }

    // Permissão para notficaições
    val notificationPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                startFocusService(context, uiState)
            } else {
                Toast.makeText(
                    context,
                    "Permissão de notificação é necessária para o modo foco",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

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
                        text = "Duração: ${profile.duration} min",
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
                        onClick = {
                            if (isFocusActive) {
                                if (uiState.activeProfile!!.passwordRequired) {
                                    requestDeviceAuthentication()
                                } else {
                                    stopFocusService(context)
                                }
                            } else {
                                if (ContextCompat.checkSelfPermission(
                                        context,
                                        android.Manifest.permission.POST_NOTIFICATIONS
                                    ) != PackageManager.PERMISSION_GRANTED
                                ) {
                                    notificationPermissionLauncher.launch(
                                        android.Manifest.permission.POST_NOTIFICATIONS
                                    )
                                }

                                startFocusService(context, uiState)
                            }
                        } ,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = if (!isFocusActive) Color.hsv(205f,1f, 0.95f) else Color.Gray,
                                contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = if (isFocusActive) "Desativar" else "Ativar",
                            color = Color.White
                        )
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
                    text = "3h 15min",
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
                    text = "12 dias ",
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
                    text = "Melhor sequência",
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .padding(end = 90.dp)
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
                    color = Color.Black,
                    modifier = Modifier
                        .padding(1.dp)
                )
            }
        }

    }

}



