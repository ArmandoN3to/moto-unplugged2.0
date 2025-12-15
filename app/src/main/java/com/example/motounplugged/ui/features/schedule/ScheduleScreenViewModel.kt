package com.example.motounplugged.ui.features.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.database.entities.SessionsEntity
import com.example.motounplugged.database.entities.atributeenums.WeekDaysAtribute // Importar o enum
import com.example.motounplugged.repositories.ProfileRepository
import com.example.motounplugged.repositories.SessionsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ScheduleUiState(
    val selectedProfileId: Int? = null,
    val selectedProfileName: String = "",

    // Alterado para Set<WeekDaysAtribute>
    val selectedDays: Set<WeekDaysAtribute> = emptySet(),
    val startHour: Int = 8,
    val startMinute: Int = 0,

    val profiles: List<ProfilesEntity> = emptyList(),
    val sessions: List<SessionsEntity> = emptyList(),

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed interface ScheduleEvent {
    data class OnProfileSelected(val profileId: Int) : ScheduleEvent
    // Alterado para WeekDaysAtribute
    data class OnDayToggle(val day: WeekDaysAtribute) : ScheduleEvent
    data class OnStartHourChange(val hour: Int) : ScheduleEvent
    data class OnStartMinuteChange(val minute: Int) : ScheduleEvent
    data object OnSaveSession : ScheduleEvent
    // Novo: Evento para apagar uma sessão
    data class OnDeleteSession(val session: SessionsEntity) : ScheduleEvent
}



class ScheduleScreenViewModel(
    private val profileRepository: ProfileRepository,
    private val sessionRepository: SessionsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState: StateFlow<ScheduleUiState> = _uiState.asStateFlow()

    init {
        loadProfiles()
    }

    /* ---------------- LOADERS ---------------- */

    private fun loadProfiles() {
        viewModelScope.launch {
            profileRepository.profiles.collect() { profiles ->

                // Lógica de seleção do perfil ativo:
                // 1. Tenta encontrar o perfil com 'isImmediatelyActive' true (assumindo o campo em ProfilesEntity)
                // 2. Se não encontrar, usa o primeiro perfil da lista.
                val activeProfile = profiles.firstOrNull {
                    // ATENÇÃO: Assumindo que ProfilesEntity possui o campo 'isImmediatelyActive: Boolean'
                    // it.isImmediatelyActive == true
                    true // Placeholder, ajuste esta linha na sua implementação real
                } ?: profiles.firstOrNull()


                _uiState.update {
                    it.copy(
                        profiles = profiles,
                        selectedProfileId = activeProfile?.idProfile,
                        selectedProfileName = activeProfile?.profileName ?: ""
                    )
                }

                activeProfile?.idProfile?.let {
                    loadSessions(it)
                }
            }
        }
    }

    private fun loadSessions(profileId: Int) {
        viewModelScope.launch {
            val sessions = sessionRepository.getSessionsForProfile(profileId)
            _uiState.update { it.copy(sessions = sessions) }
        }
    }

    /* ---------------- EVENTS ---------------- */

    fun onEvent(event: ScheduleEvent) {
        when (event) {

            is ScheduleEvent.OnProfileSelected -> {
                val profile = _uiState.value.profiles.firstOrNull {
                    it.idProfile == event.profileId
                }

                _uiState.update {
                    it.copy(
                        selectedProfileId = event.profileId,
                        selectedProfileName = profile?.profileName ?: ""
                    )
                }

                loadSessions(event.profileId)
            }

            // Lógica ajustada para WeekDaysAtribute
            is ScheduleEvent.OnDayToggle -> {
                _uiState.update {
                    val updatedDays = it.selectedDays.toMutableSet()
                    if (updatedDays.contains(event.day)) {
                        updatedDays.remove(event.day)
                    } else {
                        updatedDays.add(event.day)
                    }
                    it.copy(selectedDays = updatedDays)
                }
            }

            // Limite de horas e minutos já é aplicado aqui:
            is ScheduleEvent.OnStartHourChange ->
                _uiState.update {
                    it.copy(startHour = event.hour.coerceIn(0, 23)) // Limite: 0-23
                }

            is ScheduleEvent.OnStartMinuteChange ->
                _uiState.update {
                    it.copy(startMinute = event.minute.coerceIn(0, 59)) // Limite: 0-59
                }

            ScheduleEvent.OnSaveSession -> saveSession()

            // Lógica para apagar a sessão
            is ScheduleEvent.OnDeleteSession -> deleteSession(event.session)
        }
    }

    /* ---------------- SAVE ---------------- */

    private fun saveSession() {
        val state = _uiState.value
        val profileId = state.selectedProfileId ?: return

        if (state.selectedDays.isEmpty()) {
            _uiState.update { it.copy(errorMessage = "Selecione ao menos um dia") }
            return
        }

        viewModelScope.launch {
            // daysOfWeek agora é List<WeekDaysAtribute>
            val session = SessionsEntity(
                idProfile = profileId,
                daysOfWeek = state.selectedDays.toList(),
                startHour = state.startHour,
                startMinute = state.startMinute
            )

            sessionRepository.addSession(session)

            loadSessions(profileId)

            _uiState.update {
                it.copy(
                    selectedDays = emptySet(),
                    errorMessage = null
                )
            }
        }
    }

    /* ---------------- DELETE ---------------- */

    private fun deleteSession(session: SessionsEntity) {
        viewModelScope.launch {
            // Assumindo um mé
            // A sessão deve ter um ID para ser excluída corretamente
            sessionRepository.deleteSession(session)

            _uiState.value.selectedProfileId?.let { profileId ->
                loadSessions(profileId)
            }
        }
    }
}