package com.example.motounplugged.ui.features.selectapps

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.motounplugged.ui.features.createprofile.CreateProfileEvent
import com.example.motounplugged.ui.features.createprofile.CreateProfileViewModel
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAppsScreen(
    navController: NavController,
    viewModel: CreateProfileViewModel,
    profileId: Int? // null para novo perfil
) {
    val context = LocalContext.current
    val packageManager = context.packageManager
    var selectedApps by remember {
        mutableStateOf(viewModel.uiState.value.selectedAppPackages.toSet())
    }

    // Lista apenas apps instaláveis (ignorando apps do sistema não iniciáveis)
    val installedApps by remember {
        mutableStateOf(
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
                .filter { app ->
                    packageManager.getLaunchIntentForPackage(app.packageName) != null
                            && app.packageName != context.packageName
                }
                .sortedBy { it.loadLabel(packageManager).toString() }
        )
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
                    viewModel.onEvent(CreateProfileEvent.OnAppsSelected(selectedApps.toList()))
                    navController.popBackStack()
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
                val icon = appInfo.loadIcon(packageManager)
                val isSelected = pkg in selectedApps

                AppItemRow(
                    appName = label,
                    packageName = pkg,
                    icon = icon,
                    isSelected = isSelected,
                    onToggle = {
                        selectedApps = if (pkg in selectedApps) {
                            selectedApps - pkg
                        } else {
                            selectedApps + pkg
                        }
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
    icon: android.graphics.drawable.Drawable,
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    bitmap = drawableToBitmap(icon).asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(text = appName, style = MaterialTheme.typography.bodyLarge)
                    Text(text = packageName, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Checkbox(checked = isSelected, onCheckedChange = { onToggle() })
        }
    }
}

fun drawableToBitmap(drawable: Drawable): Bitmap {
    return if (drawable is BitmapDrawable) {
        drawable.bitmap
    } else {
        // Cria bitmap com tamanho do drawable
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth.takeIf { it > 0 } ?: 48,
            drawable.intrinsicHeight.takeIf { it > 0 } ?: 48,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        bitmap
    }
}