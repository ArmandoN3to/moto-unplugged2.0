package com.example.motounplugged.ui.features.selectapps

import android.content.pm.PackageManager
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.motounplugged.ui.features.createprofile.CreateProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAppsScreen(
    navController: NavHostController,
    viewModel: CreateProfileViewModel,
    profileId: Int?,
) {
    val context = LocalContext.current
    val packageManager = context.packageManager

    // Lista todos os apps instalados (somente uma vez)
    val installedApps by remember {
        mutableStateOf(
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
                .filter { app -> app.packageName != context.packageName } // ignora o próprio app
                .sortedBy { it.loadLabel(packageManager).toString() }
        )
    }

    // Estado local dos apps selecionados
    var selectedApps by remember {
        mutableStateOf(viewModel.uiState.value.selectedAppPackages.toMutableSet())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Selecionar Aplicativos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    // Envia os apps selecionados para o ViewModel
                    viewModel.onEvent(
                        com.example.motounplugged.ui.features.createprofile.CreateProfileEvent.OnAppsSelected(
                            selectedApps.toList()
                        )
                    )
                    navController.popBackStack() // volta à tela anterior
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Salvar Seleção (${selectedApps.size})")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(installedApps, key = { it.packageName }) { appInfo ->
                val label = appInfo.loadLabel(packageManager).toString()
                val pkg = appInfo.packageName
                val isSelected = pkg in selectedApps

                AppItemRow(
                    appName = label,
                    packageName = pkg,
                    isSelected = isSelected,
                    onToggle = {
                        if (isSelected) selectedApps.remove(pkg)
                        else selectedApps.add(pkg)
                    }
                )
            }
        }
    }
}

@Composable
fun AppItemRow(
    appName: String,
    packageName: String,
    isSelected: Boolean,
    onToggle: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() },
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = appName, fontWeight = FontWeight.Bold)
                Text(text = packageName, style = MaterialTheme.typography.bodySmall)
            }
            Checkbox(checked = isSelected, onCheckedChange = { onToggle() })
        }
    }
}
