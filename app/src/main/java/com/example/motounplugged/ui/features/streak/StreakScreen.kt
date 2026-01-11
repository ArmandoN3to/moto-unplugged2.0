package com.example.motounplugged.ui.features.streak

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.motounplugged.R
import com.example.motounplugged.ui.components.ButtonComponent
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme

@Composable
fun StreakScreen(
    modifier: Modifier = Modifier,
    streakCount: Int,
    daysInMonth: Int = 30 // mock: 30 dias do "mês"

) {
    val context = LocalContext.current
    Column (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){

        Column (
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Ofensivas",
                fontSize = 25.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Monitore suas ofensivas e progressos",
                fontSize = 20.sp,
                fontStyle = FontStyle.Normal,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding( top = 30.dp)
        ){
            Row {

                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star icon",
                    tint = Color(0xFFBB86FC),
                    modifier = Modifier
                        .size(width = 150.dp, height = 130.dp)
                        .padding(start = 16.dp)
                        .padding(top = 7.dp)
                )

                Spacer(modifier = Modifier .weight(1f))

                Column (
                    modifier = Modifier
                    .padding(top = 45.dp)
                        .padding(10.dp)
                        .border(
                            width = 3.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(5.dp),
                        )
                ){
                    Text(
                        text = "7",
                        fontStyle = FontStyle.Italic,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier =  Modifier
                            .padding(start = 80.dp)
                            .padding(top = 5.dp)
                    )
                    Text(
                        text = "Dias de Ofensivas",
                        fontStyle = FontStyle.Italic,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp)
                    )
                }

            }
        }

        Column(
            modifier = Modifier.padding(16.dp, top = 30.dp)
        ) {
            Text(
                text = "Calendário de Foco",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 5.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(20.dp))

            // Grade de 7 colunas
            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                modifier = Modifier.height(300.dp)
            ) {
                // cria os itens com base no numero de dias do mes no caso 28 para o exemplo de vizualização
                items(daysInMonth) {dayIndex ->
                    val day = dayIndex + 1
                    val color = when {
                        day <= 7 && day <= streakCount -> Color(0xFFF7F0BE)
                        day in 8..14 && day <= streakCount -> Color(0xFFB20027)
                        day in 14..21 && day <= streakCount -> Color(0xFFC3D48B)
                        day >= 22 && day <= streakCount -> Color(0xFFBB86FC)
                        else -> Color.LightGray
                    }
                    //cria a box para fazer a sobreposição com o dia
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(color),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$day",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 1º ícone + texto
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color.LightGray,
                    modifier = Modifier.size(35.dp)
                )
                Text(
                    text = "3 dias",
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic
                )
            }

            // 2º ícone + texto
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFF7F0BE),
                    modifier = Modifier.size(35.dp)
                )
                Text(
                    text = "7 dias",
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic
                )
            }

            // 3º ícone + texto
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFB20027),
                    modifier = Modifier.size(35.dp)
                )
                Text(
                    text = "10 dias",
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic
                )
            }


            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFC3D48B),
                    modifier = Modifier.size(35.dp)
                )
                Text(
                    text = "21 dias",
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic
                )
            }


            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFBB86FC),
                    modifier = Modifier.size(35.dp)
                )
                Text(
                    text = "30 dias",
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic
                )
            }
        }
        var showShareModal by remember { mutableStateOf(false) }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(48.dp),
                onClick = { showShareModal = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray, // fundo cinza claro
                    contentColor = Color.Gray
                ),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Text("Compartilhar", color = Color.White)
            }
        }

        if (showShareModal) {
            Dialog(onDismissRequest = { showShareModal = false }) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.45f)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            "Compartilhar ofensiva",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(Modifier.height(80.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {

                            // WhatsApp
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    shareToWhatsApp(context, "Minha ofensiva no Moto Unplugged é de 12 dias!")
                                }
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_whatsapp),
                                    contentDescription = "WhatsApp",
                                    modifier = Modifier.size(50.dp)
                                )
                                Text("WhatsApp")
                            }

                            // Instagram
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    shareToInstagram(context, "Minha ofensiva no Moto Unplugged é de 12 dias!")
                                }
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_instagram),
                                    contentDescription = "Instagram",
                                    modifier = Modifier.size(50.dp)
                                )
                                Text("Instagram")
                            }

                            // Facebook
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.clickable {
                                    shareToFacebook(context, "Minha ofensiva no Moto Unplugged é de 12 dias!")
                                }
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_facebook),
                                    contentDescription = "Facebook",
                                    modifier = Modifier.size(50.dp)
                                )
                                Text("Facebook")
                            }
                        }

                        Spacer(Modifier.height(80.dp))

                        OutlinedButton(onClick = { showShareModal = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.LightGray, // fundo cinza claro
                                contentColor = Color.Gray
                            ),
                            border = BorderStroke(1.dp, Color.LightGray)) {
                            Text("Cancelar", color = Color.White)
                        }
                    }
                }
            }
        }

    }
}
fun shareToWhatsApp(context: Context, message: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        setPackage("com.whatsapp")
        putExtra(Intent.EXTRA_TEXT, message)
    }

    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "WhatsApp não instalado", Toast.LENGTH_SHORT).show()
    }
}

fun shareToInstagram(context: Context, message: String) {


    val intent = context.packageManager.getLaunchIntentForPackage("com.instagram.android")

    if (intent != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Instagram não instalado", Toast.LENGTH_SHORT).show()
    }
}

fun shareToFacebook(context: Context, message: String) {


    val intent = context.packageManager.getLaunchIntentForPackage("com.facebook.katana")

    if (intent != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "Facebook não instalado", Toast.LENGTH_SHORT).show()
    }
}




@Preview(showBackground = true)
@Composable
private fun streakscreenview(){
    MotoUnpluggedTheme {
        StreakScreen(
            streakCount = 28 // 28 dias para fins de vizualização de todos os modos de ofensiva
        )
    }
}