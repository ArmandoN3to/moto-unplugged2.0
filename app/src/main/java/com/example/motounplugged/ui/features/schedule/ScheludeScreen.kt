package com.example.motounplugged.ui.screens

import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
//import java.time.LocalTime
//import java.time.format.DateTimeFormatter
//import android.app.TimePickerDialog
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.database.entities.SessionsEntity
import com.example.motounplugged.database.entities.atributeenums.WeekDaysAtribute
import com.example.motounplugged.ui.features.schedule.ScheduleScreenViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun ScheduleScreen(
    viewModel: ScheduleScreenViewModel
) {
    val scrollState = rememberScrollState()
    val weekDays = listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sab")
    val selectedDays = remember { mutableStateListOf<String>() }

    var startTime by remember { mutableStateOf(LocalTime.of(0, 0)) }
    var endTime by remember { mutableStateOf(LocalTime.of(0, 0)) }
    var selectedProfile by remember { mutableStateOf<ProfilesEntity?>(null) }

    var showStartTimePicker by remember { mutableStateOf(false) }
    var showEndTimePicker by remember { mutableStateOf(false) }

    val sessions by viewModel.sessions.collectAsState()
    val profiles by viewModel.profiles.collectAsState()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
        ){
          Text(
              text = "Agendamento",
              color = Color.Black,
              fontWeight = FontWeight.Bold,
              fontSize = 25.sp,
              fontStyle = FontStyle.Normal,
              modifier = Modifier
                  .padding(2.dp)
                  .align(Alignment.CenterHorizontally)
          )
            Text(
                text = "Agende seu horário de foco",
                fontSize = 20.sp,
                color = Color.Gray,
                fontStyle = FontStyle.Normal,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        // Weekdays selection
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
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

        // --- Hora de início ---
        Text("Hora de Início", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(
            text = startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp
        )
        Button(onClick = { showStartTimePicker = true }, colors = ButtonDefaults.buttonColors(Color.Gray)) {
            Text("Selecionar Hora de Início", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Hora de fim ---
        Text("Hora de Fim", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Text(
            text = endTime.format(DateTimeFormatter.ofPattern("HH:mm")),
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp
        )
        Button(onClick = { showEndTimePicker = true }, colors = ButtonDefaults.buttonColors(Color.Gray)) {
            Text("Selecionar Hora de Fim", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- Seleção de perfil ---
        Text("Selecione o Perfil", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        profiles.forEach { profile ->
            val isSelected = selectedProfile?.id == profile.id
            Button(
                onClick = { selectedProfile = profile },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color.Gray else Color.LightGray
                )
            ) {
                Text(profile.ProfileName)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        // --- Botão para salvar sessão ---
        Button(
            onClick = {
                val selectedDaysEnums = selectedDays.mapNotNull { day ->
                    when (day) {
                        "Dom" -> WeekDaysAtribute.DOMINGO
                        "Seg" -> WeekDaysAtribute.SEGUNDA
                        "Ter" -> WeekDaysAtribute.TERCA
                        "Qua" -> WeekDaysAtribute.QUARTA
                        "Qui" -> WeekDaysAtribute.QUINTA
                        "Sex" -> WeekDaysAtribute.SEXTA
                        "Sab" -> WeekDaysAtribute.SABADO
                        else -> null
                    }
                }

                selectedProfile?.let { profile ->
                    val session = SessionsEntity(
                        id = 0, // autogerado pelo Room
                        namePerfil = profile.ProfileName,
                        startHour = startTime.toString(),
                        endHour = endTime.toString(),
                        dayOfWeek = selectedDaysEnums, // agora é uma lista
                        isActive = true,
                        id_profile = profile.id
                    )
                    viewModel.save(session)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedProfile != null && selectedDays.isNotEmpty()
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
                        Text("Dias: ${session.dayOfWeek.joinToString(", ")}")
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
    if (showStartTimePicker) {
        TimePickerDialog(
            context,
            { _, hour, minute ->
                startTime = LocalTime.of(hour, minute)
                showStartTimePicker = false
            },
            startTime.hour, startTime.minute, true
        ).show()
    }

    // --- TimePicker fim ---
    if (showEndTimePicker) {
        TimePickerDialog(
            context,
            { _, hour, minute ->
                endTime = LocalTime.of(hour, minute)
                showEndTimePicker = false
            },
            endTime.hour, endTime.minute, true
        ).show()
    }
}
