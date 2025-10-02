package com.example.motounplugged.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import android.app.TimePickerDialog
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import com.example.motounplugged.ui.components.SelectOptions


@Composable
fun ScheduleScreen() {
    val scrollState = rememberScrollState()
    val weekDays = listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab")
    val selectedDays = remember { mutableStateListOf<String>() }

    var time by remember { mutableStateOf(LocalTime.of(0, 0)) }
    var timeEnd by remember { mutableStateOf(LocalTime.of(0, 0)) }
    var selectedProfile by remember { mutableStateOf("") }



    var showTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState) // habilita o scroll
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Text("Agendamento", fontWeight=FontWeight.Bold,
//            fontSize = 20.sp,
//            fontFamily = FontFamily.SansSerif )

        Spacer(modifier = Modifier.height(8.dp))

        // Weekdays selection
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            weekDays.forEach { day ->
                val isSelected = selectedDays.contains(day)
                Button(
                    onClick = {
                        if (isSelected) selectedDays.remove(day) else selectedDays.add(day)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) Color.Gray else Color.LightGray
                    ),
                    modifier = Modifier.padding(horizontal = 4.dp)
                        .width(44.dp)
                        .height(38.dp),
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(day, fontSize = 16.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Time
        Text(
            "Hora Início",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif
        )

        Text(
            text = time.format(DateTimeFormatter.ofPattern("HH:mm")),
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            fontFamily = FontFamily.SansSerif
        )

        Button(
            onClick = { showTimePicker = true },
            colors = ButtonDefaults.buttonColors(Color.Gray)
        ) {
            Text(
                "Selecione a Hora de Início",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // TimeEnd
        Text(
            "Hora Fim",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            text = time.format(DateTimeFormatter.ofPattern("HH:mm")),
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            fontFamily = FontFamily.SansSerif
        )
        Button(
            onClick = { showEndTimePicker = true },
            colors = ButtonDefaults.buttonColors(Color.Gray)
        ) {
            Text(
                "Selecione a Hora de Fim",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Profile selection
        SelectOptions()

        Spacer(modifier = Modifier.height(16.dp))

        // Schedule button
        Button(
            onClick = { /* Logic to schedule session */ },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedProfile.isNotEmpty()
        ) {
            Text(
                "Agendar Sessão",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                fontFamily = FontFamily.SansSerif
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Scheduled sessions
        Text(
            "Sessões Agendadas",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif
        )

        Spacer(modifier = Modifier.height(8.dp))




    // TimePicker (using AndroidView)
    if (showTimePicker) {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    time = LocalTime.of(hour, minute)
                    showTimePicker = false
                },
                time.hour,
                time.minute,
                true
            ).show()
        }
    }

        // TimePicker (using AndroidView)
        if (showEndTimePicker) {
            val context = LocalContext.current
            LaunchedEffect(Unit) {
                TimePickerDialog(
                    context,
                    { _, hour, minute ->
                        timeEnd = LocalTime.of(hour, minute)
                        showEndTimePicker = false
                    },
                    timeEnd.hour,
                    timeEnd.minute,
                    true
                ).show()
            }
        }
  }
}
