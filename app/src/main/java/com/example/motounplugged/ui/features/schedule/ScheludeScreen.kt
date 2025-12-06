package com.example.motounplugged.ui.features.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.motounplugged.database.entities.SessionsEntity

@Composable
fun ScheduleScreen(
    viewModel: ScheduleScreenViewModel,
    profileId: Int
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(profileId) {
        viewModel.loadSessions(profileId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text("Agendar Sessões", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(20.dp))

        // ---------- Dias da Semana ----------
        Text("Dias da Semana", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(10.dp))

        val days = listOf("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb")
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            days.forEachIndexed { index, day ->
                val isSelected = index in uiState.selectedDays
                Surface(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clickable { viewModel.toggleDay(index) },
                    tonalElevation = if (isSelected) 6.dp else 0.dp,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = day,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        // ---------- Horário ----------
        Text("Hora de Início", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = uiState.startHour.toString(),
                label = { Text("Hora") },
                onValueChange = { viewModel.setHour(it.toIntOrNull() ?: 0) },
                modifier = Modifier.width(100.dp)
            )
            Spacer(Modifier.width(16.dp))

            OutlinedTextField(
                value = uiState.startMinute.toString(),
                label = { Text("Minuto") },
                onValueChange = { viewModel.setMinute(it.toIntOrNull() ?: 0) },
                modifier = Modifier.width(100.dp)
            )
        }

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = { viewModel.saveSession(profileId) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar Sessão")
        }

        Spacer(Modifier.height(30.dp))

        Divider()

        // ---------- Lista de Sessões ----------
        Text("Sessões Agendadas", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(10.dp))

        LazyColumn {
            items(uiState.sessions) { session ->
                SessionItem(
                    session = session,
                    onDelete = { viewModel.deleteSession(it, profileId) }
                )
            }
        }
    }
}

@Composable
fun SessionItem(
    session: SessionsEntity,
    onDelete: (SessionsEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("Dias: ${session.daysOfWeek.joinToString()}")
                Text("Início: ${session.startHour}:${session.startMinute.toString().padStart(2, '0')}")
            }
            TextButton(onClick = { onDelete(session) }) {
                Text("Excluir")
            }
        }
    }
}
