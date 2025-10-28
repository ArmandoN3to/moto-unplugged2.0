package com.example.motounplugged.ui.features.createprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motounplugged.database.entities.ProfilesEntity
//import com.example.motounplugged.models.Profile
import com.example.motounplugged.repositories.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


// Eventos que a UI pode enviar para o ViewModel ex: clicar em salvar
sealed interface CreateProfileEvent {
    data class OnProfileNameChange(val name: String) : CreateProfileEvent
    //data object OnSelectAppsClick : CreateProfileEvent
    //data object OnSchedulingClick : CreateProfileEvent
    //data class OnActivateImmediatelyChange(val isActive: Boolean) : CreateProfileEvent
    data object OnSaveProfileClick : CreateProfileEvent
}

class CreateProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateProfileUiState())
    val uiState: StateFlow<CreateProfileUiState> = _uiState.asStateFlow()

    data class CreateProfileUiState(
        val profileId: Int? = null,
        val profileName: String = "",
        val isImmediatelyActive: Boolean = false,
        val appCount: Int = 0,
        val isEditing: Boolean = false,
    )

    fun onEvent(event: CreateProfileEvent) {
        when (event) {
            is CreateProfileEvent.OnProfileNameChange -> {
                _uiState.update { it.copy(profileName = event.name) }
            }
            /*is CreateProfileEvent.OnActivateImmediatelyChange -> {
                _uiState.update { it.copy(isImmediatelyActive = event.isActive) }
            }*/
            CreateProfileEvent.OnSaveProfileClick -> {
                saveProfile()
            }
            // A lógica de navegação para as outras telas seria tratada aqui
           // CreateProfileEvent.OnSelectAppsClick -> { /* Navegar para seleção de apps */ }
           // CreateProfileEvent.OnSchedulingClick -> { /* Navegar para agendamento */ }
        }
    }

    private fun saveProfile() {
        viewModelScope.launch {
            val currentState = _uiState.value

            val profile = ProfilesEntity(
                idProfile = currentState.profileId ?: 0,
                ProfileName = currentState.profileName,
                appCount = currentState.appCount
            )

            if (currentState.isEditing) {
                repository.update(profile)
            } else {
                repository.save(profile)
            }
        }
    }

    fun loadProfile(id: Int) {
        viewModelScope.launch {
            val profile = repository.getProfileById(id)
            if (profile != null) {
                _uiState.update {
                    it.copy(
                        profileId = profile.idProfile,
                        profileName = profile.ProfileName,
                        appCount = profile.appCount,
                        isEditing = true
                    )
                }
            }
        }
    }
}