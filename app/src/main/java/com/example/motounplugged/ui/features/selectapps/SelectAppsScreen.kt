// ui/features/selectapps/SelectAppsScreen.kt
package com.example.motounplugged.ui.features.selectapps

import android.content.res.Resources
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import kotlin.math.round

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAppsScreen(
    navController: NavController,
    profileId: Int?,
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
                        ?.set("selectedApps", event.selectedApps)
                    navController.popBackStack()
                }
            }
        }
    }

    Column(modifier = Modifier.padding(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

        TopAppBar(
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text("Aplicativos Bloqueados")
                }
            },
            navigationIcon = {
                IconButton(onClick = { viewModel.onEvent(SelectAppsEvent.OnSaveClick)}) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                }
            }
        )

        // Search Bar
        OutlinedTextField(
            value = uiState.searchQuery,
            onValueChange = { viewModel.onEvent(SelectAppsEvent.OnSearchQueryChange(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search app") },
            shape = RoundedCornerShape(30.dp),
            leadingIcon = { Icon(Icons.Default.Search, null) },
            singleLine = true ,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = com.example.motounplugged.ui.theme.Gray20, // Fill color when focused
                focusedBorderColor = com.example.motounplugged.ui.theme.Gray20,
                unfocusedContainerColor = com.example.motounplugged.ui.theme.Gray20,  // Fill color when not focused
                unfocusedBorderColor = com.example.motounplugged.ui.theme.Gray20

            )
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
