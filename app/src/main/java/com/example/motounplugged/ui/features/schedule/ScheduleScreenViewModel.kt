package com.example.motounplugged.ui.features.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motounplugged.database.entities.SessionsEntity
import com.example.motounplugged.repositories.SessionsRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class ScheduleUiState(
    val sessions: List<SessionsEntity> = emptyList(),
    val selectedDays: List<Int> = emptyList(),
    val startHour: Int = 8,
    val startMinute: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class ScheduleScreenViewModel(
    private val repository: SessionsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState: StateFlow<ScheduleUiState> = _uiState.asStateFlow()

    fun loadSessions(profileId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val sessions = repository.getSessionsForProfile(profileId)
                _uiState.update { it.copy(sessions = sessions, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = e.message, isLoading = false)
                }
            }
        }
    }

    fun toggleDay(day: Int) {
        _uiState.update { state ->
            val newList = state.selectedDays.toMutableList()
            if (day in newList) newList.remove(day) else newList.add(day)
            state.copy(selectedDays = newList)
        }
    }

    fun setHour(hour: Int) {
        _uiState.update { it.copy(startHour = hour) }
    }

    fun setMinute(minute: Int) {
        _uiState.update { it.copy(startMinute = minute) }
    }

    fun saveSession(profileId: Int) {
        viewModelScope.launch {
            val state = _uiState.value

            if (state.selectedDays.isEmpty()) return@launch

            val session = SessionsEntity(
                idProfile = profileId,
                daysOfWeek = state.selectedDays.sorted(),
                startHour = state.startHour,
                startMinute = state.startMinute
            )

            repository.addSession(session)
            loadSessions(profileId)
        }
    }

    fun deleteSession(session: SessionsEntity, profileId: Int) {
        viewModelScope.launch {
            repository.deleteSession(session)
            loadSessions(profileId)
        }
    }
}
