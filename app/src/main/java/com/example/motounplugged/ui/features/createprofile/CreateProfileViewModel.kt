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

/**
 * UI state for the Create/Edit Profile screen.
 * Keep it flat and serializable (for debugging / tests).
 */
data class CreateProfileUiState(
    val profileId: Int? = null,          // id when editing, null when creating
    val profileName: String = "",        // controlled input value
    val appCount: Int = 0,               // number of selected apps (display-only)
    val isEditing: Boolean = false,      // true = edit mode
    val isLoading: Boolean = false,      // show progress indicator
    val saveSuccess: Boolean = false,    // one-time success flag (UI should handle reset)
    val errorMessage: String? = null     // last error message to display
)

/**
 * Events that the UI can send to the ViewModel.
 * Using a sealed type makes it easy to extend safely.
 */
sealed interface CreateProfileEvent {
    data class OnProfileNameChange(val name: String) : CreateProfileEvent
    data object OnSaveProfileClick : CreateProfileEvent
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
            is CreateProfileEvent.OnProfileNameChange -> {
                // update the field, also reset transient UI flags
                _uiState.update {
                    it.copy(
                        profileName = event.name,
                        saveSuccess = false,    // reset previous success when user edits
                        errorMessage = null     // clear previous error when user edits
                    )
                }
            }

            CreateProfileEvent.OnSaveProfileClick -> {
                saveProfile()
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

            // Build entity from current state. Use 0 or the provided id depending on edit/create.
            val profile = ProfilesEntity(
                idProfile = currentState.profileId ?: 0,
                ProfileName = currentState.profileName,
                appCount = currentState.appCount
            )

            // set loading state before making repository call
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                // decide whether to insert or update based on isEditing flag
                if (currentState.isEditing) {
                    repository.update(profile)
                } else {
                    repository.save(profile)
                }

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
        viewModelScope.launch {
            // repository.getProfileById should be a suspend function returning ProfilesEntity?
            repository.getProfileById(id)?.let { profile ->
                _uiState.update {
                    it.copy(
                        profileId = profile.idProfile,
                        profileName = profile.ProfileName,
                        appCount = profile.appCount,
                        isEditing = true,
                        // reset transient flags when loading existing data
                        isLoading = false,
                        saveSuccess = false,
                        errorMessage = null
                    )
                }
            }
        }
    }
}
