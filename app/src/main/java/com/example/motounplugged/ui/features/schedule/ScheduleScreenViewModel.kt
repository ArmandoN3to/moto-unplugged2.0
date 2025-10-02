package com.example.motounplugged.ui.features.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.database.entities.SessionsEntity
import com.example.motounplugged.repositories.ProfileRepository
import com.example.motounplugged.repositories.SessionsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScheduleScreenViewModel (private val repository: SessionsRepository): ViewModel(){
    //Perfis que aparecerão em selecione o perfil
    val session: StateFlow<List<SessionsEntity>> = repository.sessions
        // transforma o flow em stateflow para coletar o novo estado
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // manter ativo até 5s
            initialValue = emptyList() // para a lista não ser nula
        )

    fun save(session: SessionsEntity) {
        viewModelScope.launch {
            repository.save(session)
        }
    }

    fun update(session: SessionsEntity) {
        viewModelScope.launch {
            repository.update(session)
        }
    }

    fun delete(session: SessionsEntity) {
        viewModelScope.launch {
            repository.delete(session)
        }
    }

}