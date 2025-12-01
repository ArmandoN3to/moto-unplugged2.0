package com.example.motounplugged.ui.features.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motounplugged.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class RegisterScreenViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    //os dados que tem que ser mantidas ao navegar para os termos e condições
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var checkedState by mutableStateOf(false)

    private val _state = MutableStateFlow("")
    val state = _state.asStateFlow()

    fun registerUser(firstname: String, lastname: String) {
        viewModelScope.launch {
            try {
                userRepository.registerUser(firstname,lastname)
                _state.value = "success"
            } catch (e: Exception) {
                _state.value = "error: ${e.message}"
            }
        }
    }

}