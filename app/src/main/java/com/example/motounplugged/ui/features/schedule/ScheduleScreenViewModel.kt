package com.example.motounplugged.ui.features.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.repositories.ProfileRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScheduleScreenViewModel (private val repository: ProfileRepository){
    //Perfis que aparecerão em selecione o perfil
    val profiles: StateFlow<List<ProfilesEntity>> = repository.profiles
        // transforma o flow em stateflow para coletar o novo estado
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // manter ativo até 5s
            initialValue = emptyList() // para a lista não ser nula
        )


}