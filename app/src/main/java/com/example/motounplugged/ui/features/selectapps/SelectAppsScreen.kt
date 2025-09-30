// ui/features/selectapps/SelectAppsScreen.kt
package com.example.motounplugged.ui.features.selectapps

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import com.example.motounplugged.ui.components.AppListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAppsScreen(
    navController: NavController,
    viewModel: SelectAppsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is SelectAppsNavigationEvent.NavigateBackWithResult -> {
                    // Envia o resultado para a tela anterior
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("selected_apps", event.selectedApps)
                    navController.popBackStack()
                }
            }
        }
    }

        Column(modifier = Modifier.padding(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

            Column (  modifier = Modifier.padding(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
            // Textos de Título e Subtítulo
            Text(
                text = "Aplicativos Permitidos",
                fontStyle = FontStyle.Normal,
                fontSize = 25.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "Selecione os apps permitidos na sessão Moto Unplugged",
                fontStyle = FontStyle.Normal,
                fontSize = 20.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 5.dp, bottom = 16.dp)
            )
            }
            // Search Bar
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.onEvent(SelectAppsEvent.OnSearchQueryChange(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search app") },
                leadingIcon = { Icon(Icons.Default.Search, null) },
                singleLine = true
            )

            // Select All
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Select all", style = MaterialTheme.typography.bodyLarge)
                Checkbox(
                    checked = uiState.filteredApps.isNotEmpty() && uiState.selectedAppPackages.containsAll(uiState.filteredApps.map { it.packageName }),
                    onCheckedChange = { viewModel.onEvent(SelectAppsEvent.OnSelectAllClick) }
                )
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        items = uiState.filteredApps,
                        key = { it.packageName }
                    ) { app ->
                        AppListItem(
                            appInfo = app,
                            isSelected = uiState.selectedAppPackages.contains(app.packageName),
                            onCheckedChange = { isSelected ->
                                viewModel.onEvent(SelectAppsEvent.OnAppSelectionChange(app.packageName, isSelected))
                            }
                        )
                    }
                }
            }
        }
    }
