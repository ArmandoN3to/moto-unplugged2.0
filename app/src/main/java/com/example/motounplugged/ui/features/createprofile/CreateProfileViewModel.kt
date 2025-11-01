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

//guarda o estado da minha tela para caso eu saia os dados sejam mantidos
data class CreateProfileUiState(
    val profileName: String = "",
    //será implementado futuramente
    val selectedApps: List<String> = emptyList(), // lista de app bloqueados
//     val isImmediatelyActive: Boolean = true, // switch de ativar perfil logo ao criar
//    val isLoading: Boolean = false, // carregamento
    val appCount: Int = 0// provisorio
)

// para teste mas é para remover
val sampleProfiles = listOf(
    CreateProfileUiState(profileName = "Trabalho", appCount = 4 ),
    CreateProfileUiState(profileName = "Academia", appCount = 4 ),
    CreateProfileUiState(profileName = "Estudo", appCount = 4 ),
    CreateProfileUiState(profileName = "teste", appCount = 4 ),
)



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
            //_uiState.update { it.copy(isLoading = true) }

            // variavel profile recebe a entidade de profiles com o estado
            // do meu profile name e appcount ( pega o que ta sendo digitado)
            val profile = ProfilesEntity(
                ProfileName = _uiState.value.profileName,
                appCount = _uiState.value.appCount
            )

            // salva o novo perfil no repository
            repository.save(profile)

            //_uiState.update { it.copy(isLoading = false) }
        }
    }
}