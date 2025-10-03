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
import com.example.motounplugged.database.entities.SessionsEntity

import com.example.motounplugged.ui.components.SelectOptions
import com.example.motounplugged.ui.features.schedule.ScheduleScreenViewModel


@Composable
fun ScheduleScreen(
    viewModel: ScheduleScreenViewModel // agora a tela recebe o ViewModel
) {
    val scrollState = rememberScrollState()
    val weekDays = listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab")
    val selectedDays = remember { mutableStateListOf<String>() }

    var time by remember { mutableStateOf(LocalTime.of(0, 0)) }
    var timeEnd by remember { mutableStateOf(LocalTime.of(0, 0)) }
    var selectedProfile by remember { mutableStateOf("") }

    var showTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    // Coletar sessões salvas no banco
    val sessions by viewModel.sessions.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Seleção dos dias da semana ---
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
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
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

        // --- Hora de Início ---
        Text("Hora Início", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(
            text = time.format(DateTimeFormatter.ofPattern("HH:mm")),
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp
        )
        Button(onClick = { showTimePicker = true }, colors = ButtonDefaults.buttonColors(Color.Gray)) {
            Text("Selecione a Hora de Início", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Hora de Fim ---
        Text("Hora Fim", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(
            text = timeEnd.format(DateTimeFormatter.ofPattern("HH:mm")),
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp
        )
        Button(onClick = { showEndTimePicker = true }, colors = ButtonDefaults.buttonColors(Color.Gray)) {
            Text("Selecione a Hora de Fim", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Seleção do Perfil ---
        SelectOptions(onProfileSelected = { profile ->
            selectedProfile = profile
        })


        Spacer(modifier = Modifier.height(16.dp))

        // --- Botão para salvar agendamento ---
        Button(
            onClick = {
                val session = SessionsEntity(
                    namePerfil = selectedProfile,
                    startHour = time.format(DateTimeFormatter.ofPattern("HH:mm")),
                    endHour = timeEnd.format(DateTimeFormatter.ofPattern("HH:mm")),
                    isActive = true
                )
                viewModel.save(session)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedProfile.isNotEmpty()
        ) {
            Text("Agendar Sessão", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Sessões agendadas ---
        Text("Sessões Agendadas", fontWeight = FontWeight.Bold, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(8.dp))

        sessions.forEach { session ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.LightGray)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(session.namePerfil, fontWeight = FontWeight.Bold)
                        Text("${session.startHour} - ${session.endHour}")
                    }
                    Switch(
                        checked = session.isActive,
                        onCheckedChange = { viewModel.update(session.copy(isActive = it)) }
                    )
                }
            }
        }
    }

    // --- TimePicker início ---
    if (showTimePicker) {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    time = LocalTime.of(hour, minute)
                    showTimePicker = false
                },
                time.hour, time.minute, true
            ).show()
        }
    }

    // --- TimePicker fim ---
    if (showEndTimePicker) {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    timeEnd = LocalTime.of(hour, minute)
                    showEndTimePicker = false
                },
                timeEnd.hour, timeEnd.minute, true
            ).show()
        }
    }
}

@Composable
fun SelectOptions(
    onProfileSelected: (String) -> Unit
) {
    val profiles = listOf("Trabalho", "Faculdade", "Cinema") // exemplo
    var selectedProfile by remember { mutableStateOf("") }

    Column {
        profiles.forEach { profile ->
            Button(
                onClick = {
                    selectedProfile = profile
                    onProfileSelected(profile) // <-- chama o callback
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedProfile == profile) Color.Gray else Color.LightGray
                )
            ) {
                Text(profile)
            }
        }
    }
}

