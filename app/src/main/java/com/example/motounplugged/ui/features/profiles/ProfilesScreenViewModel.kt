package com.example.motounplugged.ui.features.profiles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.repositories.ProfileRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfilesScreenViewModel(private val repository: ProfileRepository): ViewModel() {
    // objetivo aqui é mostrar o que atualizou na criação de perfis
    // vai observar o estado flow da minha profilesentity
    // que vem do meu repositorio de perfis
    val profiles: StateFlow<List<ProfilesEntity>> = repository.profiles
        // transforma o flow em stateflow para coletar o novo estado
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // manter ativo até 5s
            initialValue = emptyList() // para a lista não ser nula
        )

    fun save(profile: ProfilesEntity) {
        viewModelScope.launch {
            repository.save(profile)
        }
    }

    fun update(profile: ProfilesEntity) {
        viewModelScope.launch {
            repository.update(profile)
        }
    }

    fun delete(profile: ProfilesEntity) {
        viewModelScope.launch {
            repository.delete(profile)
        }
    }
}