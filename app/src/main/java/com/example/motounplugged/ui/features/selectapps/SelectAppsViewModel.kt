package com.example.motounplugged.ui.features.selectapps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.motounplugged.models.AppInfo
import com.example.motounplugged.domain.usecase.GetInstalledAppsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class SelectAppsUiState(
    val allApps: List<AppInfo> = emptyList(),
    val searchQuery: String = "",
    val selectedAppPackages: Set<String> = emptySet(),
    val isLoading: Boolean = true
) {
    val filteredApps: List<AppInfo>
        get() = if (searchQuery.isBlank()) {
            allApps
        } else {
            allApps.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }
}

sealed interface SelectAppsEvent {
    // Evento para o texto da busca
    data class OnSearchQueryChange(val query: String) : SelectAppsEvent

    // Evento checkbox de app é marcado/desmarcado
    data class OnAppSelectionChange(val packageName: String, val isSelected: Boolean) : SelectAppsEvent

    // Evento para checkbox "Selecionar Tudo"
    data object OnSelectAllClick : SelectAppsEvent

    // Evento para o clique no botão Salvar
    data object OnSaveClick : SelectAppsEvent

}

sealed interface SelectAppsNavigationEvent {
    data class NavigateBackWithResult(val selectedApps: List<AppInfo>) : SelectAppsNavigationEvent
}

class SelectAppsViewModel(
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SelectAppsUiState())
    val uiState: StateFlow<SelectAppsUiState> = _uiState.asStateFlow()

    private val _navigationEvent = Channel<SelectAppsNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            val apps = getInstalledAppsUseCase.execute()
            _uiState.update { it.copy(allApps = apps, isLoading = false) }
        }
    }

    fun onEvent(event: SelectAppsEvent) {
        when (event) {
            is SelectAppsEvent.OnSearchQueryChange -> _uiState.update { it.copy(searchQuery = event.query) }
            is SelectAppsEvent.OnAppSelectionChange -> {
                _uiState.update { currentState ->
                    val newSelection = currentState.selectedAppPackages.toMutableSet()
                    if (event.isSelected) newSelection.add(event.packageName)
                    else newSelection.remove(event.packageName)
                    currentState.copy(selectedAppPackages = newSelection)
                }
            }
            SelectAppsEvent.OnSelectAllClick -> {
                _uiState.update { currentState ->
                    val filteredPackages = currentState.filteredApps.map { it.packageName }.toSet()
                    val currentSelection = currentState.selectedAppPackages
                    val newSelection = if (currentSelection.containsAll(filteredPackages)) {
                        currentSelection - filteredPackages
                    } else {
                        currentSelection + filteredPackages
                    }
                    currentState.copy(selectedAppPackages = newSelection)
                }
            }
            SelectAppsEvent.OnSaveClick -> {
                viewModelScope.launch {
                    val selected = _uiState.value.allApps
                        .filter { _uiState.value.selectedAppPackages.contains(it.packageName) }

                    _navigationEvent.send(
                        SelectAppsNavigationEvent.NavigateBackWithResult(selected)
                    )
                }
            }
        }
    }

    fun preselectApps(apps: List<AppInfo>) {
        val pkgs = apps.map { it.packageName }.toSet()
        _uiState.update { it.copy(selectedAppPackages = pkgs) }
    }



}