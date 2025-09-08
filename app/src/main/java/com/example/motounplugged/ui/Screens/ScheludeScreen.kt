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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import com.example.motounplugged.ui.components.SelectOptions
import com.example.motounplugged.models.Profile
import com.example.motounplugged.models.sampleProfiles
import com.example.motounplugged.ui.components.ProfileCard


@Composable
fun ScheduleScreen() {
    val scrollState = rememberScrollState()
    val weekDays = listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab")
    val selectedDays = remember { mutableStateListOf<String>() }

    var time by remember { mutableStateOf(LocalTime.of(0, 0)) }
    var durationHours by remember { mutableStateOf(0) }
    var durationMinutes by remember { mutableStateOf(0) }
    var selectedProfile by remember { mutableStateOf("") }

    val scheduledSessions = listOf(
        "Segunda a Sexta \n08:00 - 30 min",
        "Sábado \n19:00 - 1 hour"
    )

    var showTimePicker by remember { mutableStateOf(false) }
    var showDurationPicker by remember { mutableStateOf(false) }

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
            "Hora",
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
                "Selecione a Hora",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Duration
        Text(
            "Duração",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif
        )
        Text(
            String.format("%02d:%02d", durationHours, durationMinutes),
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            fontFamily = FontFamily.SansSerif
        )
        Button(
            onClick = { showDurationPicker = true },
            colors = ButtonDefaults.buttonColors(Color.Gray)
        ) {
            Text(
                "Selecione a duração",
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


        scheduledSessions.forEach { session ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(session)
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(Color.Green, shape = CircleShape)
                    )
                }
            }
        }


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

    // DurationPicker (simulated with sliders)
    if (showDurationPicker) {
        AlertDialog(
            onDismissRequest = { showDurationPicker = false },
            confirmButton = {
                Button(onClick = { showDurationPicker = false }) {
                    Text("OK")
                }
            },
            title = { Text("Selecione a Duração") },
            text = {
                Column {
                    Text("Horas: $durationHours")
                    Slider(
                        value = durationHours.toFloat(),
                        onValueChange = { durationHours = it.toInt() },
                        valueRange = 0f..12f,
                        steps = 11
                    )
                    Text("Minutos: $durationMinutes")
                    Slider(
                        value = durationMinutes.toFloat(),
                        onValueChange = { durationMinutes = it.toInt() },
                        valueRange = 0f..59f,
                        steps = 58
                    )
                }
            }
        )
    }
  }
}
