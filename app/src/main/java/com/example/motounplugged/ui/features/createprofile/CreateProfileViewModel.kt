package com.example.motounplugged.ui.features.createprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motounplugged.models.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Representa todo o estado que a nossa UI pode ter
data class CreateProfileUiState(
    val profileName: String = "",
    val selectedApps: List<String> = emptyList(),
    val isImmediatelyActive: Boolean = true,
    val isLoading: Boolean = false
)



// Eventos que a UI pode enviar para o ViewModel
sealed interface CreateProfileEvent {
    data class OnProfileNameChange(val name: String) : CreateProfileEvent
    data object OnSelectAppsClick : CreateProfileEvent
    data object OnSchedulingClick : CreateProfileEvent
    data class OnActivateImmediatelyChange(val isActive: Boolean) : CreateProfileEvent
    data object OnSaveProfileClick : CreateProfileEvent
}

class CreateProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CreateProfileUiState())
    val uiState: StateFlow<CreateProfileUiState> = _uiState.asStateFlow()

    fun onEvent(event: CreateProfileEvent) {
        when (event) {
            is CreateProfileEvent.OnProfileNameChange -> {
                _uiState.update { it.copy(profileName = event.name) }
            }
            is CreateProfileEvent.OnActivateImmediatelyChange -> {
                _uiState.update { it.copy(isImmediatelyActive = event.isActive) }
            }
            CreateProfileEvent.OnSaveProfileClick -> {
                saveProfile()
            }
            // A lógica de navegação para as outras telas seria tratada aqui
            CreateProfileEvent.OnSelectAppsClick -> { /* Navegar para seleção de apps */ }
            CreateProfileEvent.OnSchedulingClick -> { /* Navegar para agendamento */ }
        }
    }

    private fun saveProfile() {
        viewModelScope.launch {
            // Lógica para salvar o perfil em um banco de dados ou repositório
            _uiState.update { it.copy(isLoading = true) }
            // ... salvar dados ...
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}