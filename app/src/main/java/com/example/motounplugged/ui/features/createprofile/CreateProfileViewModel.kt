package com.example.motounplugged.ui.features.createprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.models.AppInfo
import com.example.motounplugged.repositories.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * UI state for the Create/Edit Profile screen.
 * Keep it flat and serializable (for debugging / tests).
 */
data class CreateProfileUiState(
    val profileId: Int? = null,          // id when editing, null when creating
    val profileName: String = "",        // controlled input value
    val appCount: Int = 0,               // number of selected apps (display-only)
    val isImmediatelyActive: Boolean = false, // if the profile is active
    val selectedApps: List<AppInfo> = emptyList(), // selected apps of the profile
    val wallpaperUri: String? = null,    // wallpaper selected by user
    val duration: Int = 0,               // duration of the profile
    val passwordRequired: Boolean = false, // if password is required to deactivate the profile
    val isEditing: Boolean = false,      // true = edit mode
    val isLoading: Boolean = false,      // show progress indicator
    val saveSuccess: Boolean = false,    // one-time success flag (UI should handle reset)
    val errorMessage: String? = null,    // last error message to display
    val hasLoadedProfile: Boolean = false // Indica se o perfil foi carregado para edições
    )

/**
 * Events that the UI can send to the ViewModel.
 * Using a sealed type makes it easy to extend safely.
 */
sealed interface CreateProfileEvent {
    data class OnProfileNameChange(val name: String) : CreateProfileEvent
    data object OnSaveProfileClick : CreateProfileEvent
    data object OnSelectAppsClick : CreateProfileEvent
    data class OnWallpaperSelected(val uri: String) : CreateProfileEvent
    data object OnSetDurationClick : CreateProfileEvent
    data object OnInterruptionsClick : CreateProfileEvent
    data class OnRequirePasswordChange(val enabled: Boolean) : CreateProfileEvent
    data class OnAppsSelected(val apps: List<AppInfo>) : CreateProfileEvent

}

class CreateProfileViewModel(
    private val repository: ProfileRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateProfileUiState())
    val uiState: StateFlow<CreateProfileUiState> = _uiState.asStateFlow()

    /**
     * Receives events from the UI and routes them.
     * Keep event handling small and side-effect free (except calling save/load).
     */
    fun onEvent(event: CreateProfileEvent) {
        when (event) {
            is CreateProfileEvent.OnProfileNameChange ->
                _uiState.update { it.copy(profileName = event.name) }

            CreateProfileEvent.OnSaveProfileClick -> saveProfile()

            CreateProfileEvent.OnSelectAppsClick -> {
                println("Usuário clicou em selecionar apps bloqueados")
                // aqui você pode sinalizar navegação ou abrir uma tela
            }

            is CreateProfileEvent.OnWallpaperSelected ->
                _uiState.update { it.copy(wallpaperUri = event.uri) }

            CreateProfileEvent.OnSetDurationClick -> {
                _uiState.update { it.copy() } // Adicionar tela de duraç~ao
            }

            CreateProfileEvent.OnInterruptionsClick -> {
                println("Usuário clicou em gerenciar interrupções")
            }

            is CreateProfileEvent.OnRequirePasswordChange ->
                _uiState.update { it.copy(passwordRequired = event.enabled) }

            is CreateProfileEvent.OnAppsSelected -> {
                _uiState.update {
                    it.copy(
                        selectedApps = event.apps,
                        appCount = event.apps.size
                    )
                }
            }
        }

    }


    /**
     * Save or update the profile.
     * - reads current state
     * - builds ProfilesEntity
     * - updates uiState loading / success / error
     */
    private fun saveProfile() {
        viewModelScope.launch {
            val currentState = _uiState.value

            _uiState.update { it.copy(isLoading = true) }

            // Build entity from current state. Use 0 or the provided id depending on edit/create.
            val profile = ProfilesEntity(
                idProfile = currentState.profileId ?: 0,
                profileName = currentState.profileName,
                appCount = currentState.appCount,
                wallpaperUri = currentState.wallpaperUri,
                passwordRequired = currentState.passwordRequired,
                duration = currentState.duration
            )

            // set loading state before making repository call
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                // decide whether to insert or update based on isEditing flag
                val profileId =
                    if (currentState.isEditing) {
                        repository.update(profile)
                        currentState.profileId!!
                    } else {
                        repository.save(profile)
                    }

                repository.replaceBlockedApps(profileId, currentState.selectedApps)

                // success: stop loading and mark success (UI can navigate/respond to this)
                _uiState.update { it.copy(isLoading = false, saveSuccess = true) }

            } catch (e: Exception) {
                // failure: stop loading and store a user-friendly message
                val message = e.message ?: "Unknown error while saving profile"
                _uiState.update { it.copy(isLoading = false, errorMessage = message) }
            }
        }
    }

    /**
     * Load an existing profile from repository and populate uiState.
     * Called when screen opens in edit mode (profileId provided).
     */
    fun loadProfile(id: Int) {
        val current = _uiState.value
        if (current.hasLoadedProfile) return  // NÃO sobrescreve após edição

        viewModelScope.launch {
            repository.getProfileById(id)?.let { profile ->
                _uiState.update {
                    it.copy(
                        profileId = profile.idProfile,
                        profileName = profile.profileName,
                        appCount = profile.appCount,
                        wallpaperUri = profile.wallpaperUri,
                        passwordRequired = profile.passwordRequired,
                        duration = profile.duration,
                        isEditing = true,
                        isLoading = false,
                        saveSuccess = false,
                        errorMessage = null,
                        hasLoadedProfile = true // marca como carregado
                    )
                }

                // Carregar apps bloqueados do banco
                val apps = repository.getProfileWithApps(id)?.blockedApps?.map { blocked ->
                    AppInfo(
                        name = blocked.nameApp,
                        packageName = blocked.packageName
                    )
                } ?: emptyList()

                _uiState.update {
                    it.copy(
                        selectedApps = apps,
                        appCount = apps.size
                    )
                }
            }
        }
    }


    fun setSelectedApps(apps: List<AppInfo>) {
        _uiState.update {
            it.copy(
                selectedApps = apps,
                appCount = apps.size
            )
        }
    }

}
