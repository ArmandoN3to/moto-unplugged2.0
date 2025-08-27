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
import androidx.compose.ui.window.Dialog

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import android.app.TimePickerDialog
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun ScheduleScreen() {
    val weekDays = listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab")
    val selectedDays = remember { mutableStateListOf<String>() }

    var time by remember { mutableStateOf(LocalTime.of(0, 0)) }
    var durationHours by remember { mutableStateOf(0) }
    var durationMinutes by remember { mutableStateOf(0) }
    var selectedProfile by remember { mutableStateOf("") }

    val profiles = listOf("Profile 1", "Profile 2", "Profile 3")
    val scheduledSessions = listOf(
        "Monday to Friday\n08:00 - 30 min",
        "Saturday\n19:00 - 1 hour"
    )

    var showTimePicker by remember { mutableStateOf(false) }
    var showDurationPicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Agendamento", fontWeight=FontWeight.Bold, fontSize = 18.sp, fontFamily = FontFamily.SansSerif )

        Spacer(modifier = Modifier.height(16.dp))

        // Weekdays selection
        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            weekDays.forEach { day ->
                val isSelected = selectedDays.contains(day)
                Button(
                    onClick = {
                        if (isSelected) selectedDays.remove(day) else selectedDays.add(day)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) Color.Gray else Color.LightGray
                    ),
                    shape = CircleShape
                ) {
                    Text(day)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Time
        Text("Hora", fontWeight=FontWeight.Bold, fontSize = 18.sp, fontFamily = FontFamily.SansSerif)
        Text(time.format(DateTimeFormatter.ofPattern("HH:mm")))
        Button(onClick = { showTimePicker = true }, colors = ButtonDefaults.buttonColors(Color.Gray)) {
            Text("Select time", fontWeight=FontWeight.Bold, fontFamily = FontFamily.SansSerif)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Duration
        Text("Duração", fontWeight=FontWeight.Bold, fontSize = 18.sp, fontFamily = FontFamily.SansSerif)
        Text(
            String.format("%02d:%02d", durationHours, durationMinutes),
            style = MaterialTheme.typography.displayMedium
        )
        Button(onClick = { showDurationPicker = true }, colors = ButtonDefaults.buttonColors(Color.Gray)) {
            Text("Selecione a duração.", fontWeight=FontWeight.Bold, fontFamily = FontFamily.SansSerif)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Profile selection
        var dropdownExpanded by remember { mutableStateOf(false) }
        Box {
            OutlinedButton(onClick = { dropdownExpanded = true }) {
                Text(selectedProfile.ifEmpty { "Escolha um perfil" })
            }
            DropdownMenu(expanded = dropdownExpanded, onDismissRequest = { dropdownExpanded = false }) {
                profiles.forEach { profile ->
                    DropdownMenuItem(
                        text = { Text(profile) },
                        onClick = {
                            selectedProfile = profile
                            dropdownExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Schedule button
        Button(
            onClick = { /* Logic to schedule session */ },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedProfile.isNotEmpty()
        ) {
            Text("Agendar Sessão", fontWeight=FontWeight.Bold, fontSize = 18.sp, fontFamily = FontFamily.SansSerif)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Scheduled sessions
        Text("Sessões Agendadads", fontWeight=FontWeight.Bold, fontSize = 18.sp, fontFamily = FontFamily.SansSerif)

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
