package com.example.motounplugged.ui.features.createprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.repositories.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CreateProfileUiState(
    val profileId: Int? = 0,
    val profileName: String = "",
    val appCount: Int = 0,
    val selectedAppPackages: Set<String> = emptySet(),
    val isLoading: Boolean = false,
    val saveSuccess: Boolean = false,
    val isEditing: Boolean = false,
    val isImmediatelyActive: Boolean = false,
    val errorMessage: String? = null
)

sealed interface CreateProfileEvent {
    data class OnProfileNameChange(val name: String) : CreateProfileEvent
    data object OnSaveProfileClick : CreateProfileEvent
    data object OnSelectAppsClick : CreateProfileEvent
    data object OnSelectWallpaperClick : CreateProfileEvent
    data object OnSetDurationClick : CreateProfileEvent
    data object OnInterruptionsClick : CreateProfileEvent
    data class OnRequirePasswordChange(val enabled: Boolean) : CreateProfileEvent
    data class OnAppsSelected(val apps: List<String>) : CreateProfileEvent
}

class CreateProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateProfileUiState())
    val uiState: StateFlow<CreateProfileUiState> = _uiState.asStateFlow()

    fun onEvent(event: CreateProfileEvent) {
        when (event) {
            is CreateProfileEvent.OnProfileNameChange ->
                _uiState.update { it.copy(profileName = event.name) }

            CreateProfileEvent.OnSaveProfileClick -> saveProfile()

            CreateProfileEvent.OnSelectAppsClick ->
                println("Usuário clicou em selecionar apps bloqueados")

            CreateProfileEvent.OnSelectWallpaperClick ->
                println("Usuário clicou em escolher wallpaper")

            CreateProfileEvent.OnSetDurationClick ->
                println("Usuário clicou em definir duração")

            CreateProfileEvent.OnInterruptionsClick ->
                println("Usuário clicou em gerenciar interrupções")

            is CreateProfileEvent.OnRequirePasswordChange ->
                _uiState.update { it.copy(isImmediatelyActive = event.enabled) }

            is CreateProfileEvent.OnAppsSelected ->
                _uiState.update {
                    it.copy(
                        selectedAppPackages = event.apps.toSet(),
                        appCount = event.apps.size
                    )
                }
        }
    }

    private fun saveProfile() {
        viewModelScope.launch {
            val currentState = _uiState.value
            val profile = ProfilesEntity(
                idProfile = currentState.profileId ?: 0,
                ProfileName = currentState.profileName,
                appCount = currentState.appCount,
                isImmediatelyActive = currentState.isImmediatelyActive
            )

            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                if (currentState.isEditing) {
                    repository.update(profile, currentState.selectedAppPackages.toList())
                } else {
                    repository.save(profile, currentState.selectedAppPackages.toList())
                }

                _uiState.update { it.copy(isLoading = false, saveSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun loadProfile(id: Int) {
        viewModelScope.launch {
            repository.getProfileWithBlockedApps(id)?.let { profileWithApps ->
                _uiState.update {
                    it.copy(
                        profileId = profileWithApps.profile.idProfile,
                        profileName = profileWithApps.profile.ProfileName,
                        appCount = profileWithApps.profile.appCount,
                        selectedAppPackages = profileWithApps.blockedApps.map { app -> app.packageName }.toSet(),
                        isEditing = true,
                        isLoading = false,
                        saveSuccess = false,
                        errorMessage = null
                    )
                }
            }
        }
    }
}
