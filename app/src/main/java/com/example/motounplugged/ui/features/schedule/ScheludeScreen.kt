package com.example.motounplugged.ui.features.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // Importar Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.database.entities.SessionsEntity
import com.example.motounplugged.database.entities.atributeenums.WeekDaysAtribute

// Constantes de estilo
private val HORIZONTAL_PADDING = 16.dp
private val VERTICAL_SPACER = 24.dp
private val ICON_SIZE = 20.dp
private val CARD_CORNER_RADIUS = 8.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    profileId: Int,
    viewModel: ScheduleScreenViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(
                        // Centraliza o conteúdo (os dois textos)
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Agendamento",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Agende suas Sessões de Foco",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            // Removido o padding top desnecessário
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(Modifier.height(8.dp))

            // Seletor de Perfil
            if (uiState.profiles.isNotEmpty()) {
                ProfileSelector(
                    profiles = uiState.profiles,
                    selectedProfileId = uiState.selectedProfileId,
                    onProfileSelected = { profileId ->
                        viewModel.onEvent(ScheduleEvent.OnProfileSelected(profileId))
                    }
                )
            } else {
                Text(
                    "Nenhum perfil encontrado.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = HORIZONTAL_PADDING, vertical = 8.dp)
                )
            }

            // Separador após a primeira seção
            Spacer(Modifier.height(VERTICAL_SPACER / 2))
            Divider(Modifier.padding(horizontal = HORIZONTAL_PADDING))
            Spacer(Modifier.height(VERTICAL_SPACER / 2))


            // Seletor de Dias da Semana
            DaysOfWeekSelector(
                selectedDays = uiState.selectedDays,
                onDayToggle = { day ->
                    viewModel.onEvent(ScheduleEvent.OnDayToggle(day))
                }
            )

            Divider(Modifier.padding(horizontal = HORIZONTAL_PADDING))

            Spacer(Modifier.height(VERTICAL_SPACER))

            // Seletor de Horário de Início
            StartTimeSelector(
                hour = uiState.startHour,
                minute = uiState.startMinute,
                onHourChange = { hour ->
                    viewModel.onEvent(ScheduleEvent.OnStartHourChange(hour))
                },
                onMinuteChange = { minute ->
                    viewModel.onEvent(ScheduleEvent.OnStartMinuteChange(minute))
                }
            )

            Divider(Modifier.padding(horizontal = HORIZONTAL_PADDING))
            Spacer(Modifier.height(VERTICAL_SPACER))

            // Lista de Sessões Agendadas
            SessionsListComponent(
                sessions = uiState.sessions,
                onDeleteSession = { session ->
                    viewModel.onEvent(ScheduleEvent.OnDeleteSession(session))
                }
            )

            Spacer(Modifier.height(VERTICAL_SPACER * 2))
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = HORIZONTAL_PADDING) // Mantém o padding lateral
                .padding(bottom = 16.dp), // Espaçamento da base da tela
            contentAlignment = Alignment.BottomCenter
        ) {
            Button(
                onClick = { viewModel.onEvent(ScheduleEvent.OnSaveSession) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray, contentColor = Color.DarkGray
            )
            ) {
                Text(
                    text = "Salvar Agendamento",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

// ------------------------------------ Componentes ------------------------------------

@Composable
private fun SectionTitleRow(
    icon: ImageVector,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(horizontal = HORIZONTAL_PADDING)) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier.size(ICON_SIZE),
            tint = Color.Gray // Ícone de seção agora é preto
        )
        Spacer(Modifier.width(8.dp))
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSelector(
    profiles: List<ProfilesEntity>,
    selectedProfileId: Int?,
    onProfileSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val selectedProfile = profiles.firstOrNull { it.idProfile == selectedProfileId }

    Column(Modifier.fillMaxWidth()) {
        SectionTitleRow(icon = Icons.Default.AccountCircle, title = "Perfil")

        Spacer(Modifier.height(8.dp))

        Box(modifier = Modifier.padding(horizontal = HORIZONTAL_PADDING)) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedProfile?.profileName ?: "Selecione um perfil",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Perfil") },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    trailingIcon = {
                        // Ícone de dropdown (seta)
                        Icon(
                            Icons.Default.ArrowDropDown, // Usando ArrowDropDown como trailingIcon
                            contentDescription = null,
                            tint = Color.Gray // Ícone em preto
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.DarkGray,
                    unfocusedBorderColor = Color.LightGray,
                    focusedLabelColor = Color.DarkGray,
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = Color.DarkGray
                )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    profiles.forEach { profile ->
                        DropdownMenuItem(
                            text = { Text(profile.profileName) },
                            onClick = {
                                expanded = false
                                onProfileSelected(profile.idProfile)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DurationComponent(
    duration: Int,
    onDurationChange: (Int) -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        SectionTitleRow(icon = Icons.Default.Timer, title = "Duração do Agendamento")

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = duration.toString(),
            onValueChange = {
                val value = it.toIntOrNull()
                if (value != null && value >= 0) {
                    onDurationChange(value)
                }
            },
            label = { Text("Duração (minutos)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HORIZONTAL_PADDING),
            singleLine = true
        )
    }
}

@Composable
fun DaysOfWeekSelector(
    selectedDays: Set<WeekDaysAtribute>,
    onDayToggle: (WeekDaysAtribute) -> Unit
) {
    val orderedDays = WeekDaysAtribute.entries.sortedBy {
        if (it == WeekDaysAtribute.DOMINGO) 7 else it.id_day - 1
    }

    val dayLabels = mapOf(
        WeekDaysAtribute.SEGUNDA to "S",
        WeekDaysAtribute.TERCA to "T",
        WeekDaysAtribute.QUARTA to "Q",
        WeekDaysAtribute.QUINTA to "Q",
        WeekDaysAtribute.SEXTA to "S",
        WeekDaysAtribute.SABADO to "S",
        WeekDaysAtribute.DOMINGO to "D",
    )

    Column(Modifier.fillMaxWidth()) {
        SectionTitleRow(icon = Icons.Default.CalendarToday, title = "Dias da Semana")

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HORIZONTAL_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            orderedDays.forEach { day ->
                FilterChip(
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color.Gray,
                        selectedLabelColor = Color.White,
                        containerColor = Color.LightGray,
                        labelColor = Color.Black
                    ),
                    selected = selectedDays.contains(day),
                    onClick = { onDayToggle(day) },
                    label = {
                        Text(
                            text = dayLabels[day] ?: day.name.take(1),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 2.dp)
                )
            }
        }
    }
}

@Composable
fun StartTimeSelector(
    hour: Int,
    minute: Int,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        SectionTitleRow(icon = Icons.Default.Schedule, title = "Horário de Início")

        Spacer(Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(HORIZONTAL_PADDING),
            modifier = Modifier.padding(horizontal = HORIZONTAL_PADDING)
        ) {
            OutlinedTextField(
                value = hour.toString(),
                onValueChange = {
                    val v = it.toIntOrNull()
                    if (v != null && v in 0..23) onHourChange(v)
                    else if (it.isEmpty() || it == "0") onHourChange(0)
                },
                label = { Text("Hora (0-23)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            OutlinedTextField(
                value = minute.toString(),
                onValueChange = {
                    val v = it.toIntOrNull()
                    if (v != null && v in 0..59) onMinuteChange(v)
                    else if (it.isEmpty() || it == "0") onMinuteChange(0)
                },
                label = { Text("Minuto (0-59)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
                singleLine = true
            )
        }
    }
}

@Composable
fun SessionsListComponent(
    sessions: List<SessionsEntity>,
    onDeleteSession: (SessionsEntity) -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        SectionTitleRow(icon = Icons.Default.ListAlt, title = "Sessões Agendadas")

        Spacer(Modifier.height(8.dp))

        if (sessions.isEmpty()) {
            Text(
                "Nenhuma sessão cadastrada",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = HORIZONTAL_PADDING)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = HORIZONTAL_PADDING),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                sessions.forEach { session ->

                    // --- CARTÃO INDIVIDUAL DA SESSÃO ---
                    Card(
                        shape = RoundedCornerShape(CARD_CORNER_RADIUS),
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        val sortedDays = session.daysOfWeek.sortedBy { it.id_day }
                        val daysString = sortedDays.joinToString(", ") {
                            it.name.take(3)
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${session.startHour.toString().padStart(2, '0')}:${session.startMinute.toString().padStart(2, '0')} - $daysString",
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            // Botão de apagar
                            IconButton(
                                onClick = { onDeleteSession(session) },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Apagar Sessão",
                                    tint = Color.DarkGray //
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}