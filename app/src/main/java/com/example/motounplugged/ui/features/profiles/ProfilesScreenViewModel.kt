package com.example.motounplugged.ui.features.profiles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.repositories.ProfileRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfilesScreenViewModel(
    private val repository: ProfileRepository
) : ViewModel() {

    // Observa todos os perfis (e atualiza automaticamente via Flow)
    val profiles: StateFlow<List<ProfilesEntity>> = repository.getAllProfiles()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * Salva um novo perfil no banco.
     * Caso seja necessário, você pode passar uma lista de apps bloqueados.
     */
    fun save(profile: ProfilesEntity, selectedApps: List<String> = emptyList()) {
        viewModelScope.launch {
            repository.save(profile, selectedApps)
        }
    }

    /**
     * Atualiza um perfil existente e suas listas de apps bloqueados.
     */
    fun update(profile: ProfilesEntity, selectedApps: List<String> = emptyList()) {
        viewModelScope.launch {
            repository.update(profile, selectedApps)
        }
    }

    /**
     * Exclui um perfil e automaticamente remove seus apps bloqueados
     * (graças ao `onDelete = CASCADE` no relacionamento do Room).
     */
    fun delete(profile: ProfilesEntity) {
        viewModelScope.launch {
            repository.delete(profile)
        }
    }
    fun toggleActiveProfile(profile: ProfilesEntity, newValue: Boolean) {
        viewModelScope.launch {
            if (newValue) {
                // Desativa outros perfis ativos
                profiles.value.forEach { other ->
                    if (other.idProfile != profile.idProfile && other.isImmediatelyActive) {
                        repository.update(
                            other.copy(
                                isImmediatelyActive = false,
                                selectedApps = other.selectedApps // mantém os apps do outro perfil
                            ),
                            other.selectedApps // <-- segundo parâmetro
                        )
                    }
                }
            }

            // Atualiza o perfil atual
            repository.update(
                profile.copy(
                    isImmediatelyActive = newValue,
                    selectedApps = profile.selectedApps
                ),
                profile.selectedApps // <-- segundo parâmetro
            )
        }
    }


}
