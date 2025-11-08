package com.example.motounplugged.ui.features.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motounplugged.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegisterScreenViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    private val _state = MutableStateFlow("")
    val state = _state.asStateFlow()

    fun registerUser(firstname: String, lastname: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                userRepository.registerUser(firstname,lastname, email, password)
                _state.value = "success"
            } catch (e: Exception) {
                _state.value = "error: ${e.message}"
            }
        }
    }

}