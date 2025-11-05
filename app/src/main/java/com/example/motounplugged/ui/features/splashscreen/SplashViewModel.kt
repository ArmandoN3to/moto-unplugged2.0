package com.example.motounplugged.ui.features.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState

    init {
        checkUserStatus()
    }

    private fun checkUserStatus() {
        viewModelScope.launch {

//            // Lógica de decisão
//            val isLoggedIn = false // Troque pela sua lógica real
//
//            if (isLoggedIn) {
//                _uiState.value = SplashUiState.NavigateToHome
//            } else {
//                _uiState.value = SplashUiState.NavigateToLogin
//            }
        }
    }
}

// Estados da UI
sealed interface SplashUiState {
    object Loading : SplashUiState
    object NavigateToHome : SplashUiState
    object NavigateToLogin : SplashUiState
}