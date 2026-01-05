package com.example.motounplugged.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.database.entities.SessionsEntity
import com.example.motounplugged.repositories.ProfileRepository
import com.example.motounplugged.repositories.SessionsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = true,
    val activeProfile: ProfilesEntity? = null,
    val nextSession: SessionsEntity? = null,
    val blockedAppsCount: Int = 0,
    val errorMessage: String? = null
)


class HomeScreenViewModel(
    private val profileRepository: ProfileRepository,
    private val sessionRepository: SessionsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHome()
    }

    fun loadHome() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }

                val activeProfile = profileRepository.getActiveProfile()

                if (activeProfile == null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            activeProfile = null
                        )
                    }
                    return@launch
                }

                val blockedApps =
                    profileRepository.getProfileWithApps(activeProfile.idProfile)
                        ?.blockedApps
                        ?.size ?: 0


                _uiState.update {
                    it.copy(
                        isLoading = false,
                        activeProfile = activeProfile,
                        blockedAppsCount = blockedApps,
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    fun onActivateProfile() {
        // aqui futuramente você ativa o modo foco
    }
}
