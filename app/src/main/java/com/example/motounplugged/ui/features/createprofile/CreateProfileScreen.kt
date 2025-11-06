package com.example.motounplugged.ui.features.createprofile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.motounplugged.ui.navigation.AppScreens
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.TopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProfileScreen(
    navController: NavController,
    profileId: Int?,
    viewModel: CreateProfileViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Carrega o perfil apenas uma vez se estiver em modo edição
    LaunchedEffect(profileId) {
        if (profileId != null && profileId != -1) {
            viewModel.loadProfile(profileId)
        }
    }

    // Navega após salvar
    LaunchedEffect(uiState.saveSuccess) {
        if (uiState.saveSuccess) {
            navController.navigate(AppScreens.Profiles.route) {
                popUpTo(AppScreens.Profiles.route) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (uiState.isEditing) "Editar Perfil" else "Criar Perfil")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        CreateProfileContent(
            modifier = Modifier.padding(innerPadding),
            uiState = uiState,
            onEvent = viewModel::onEvent,
            navController = navController
        )
    }
}


@Composable
private fun CreateProfileContent(
    modifier: Modifier = Modifier,
    uiState: CreateProfileUiState,
    onEvent: (CreateProfileEvent) -> Unit,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        OutlinedTextField(
            value = uiState.profileName,
            onValueChange = { onEvent(CreateProfileEvent.OnProfileNameChange(it)) },
            label = { Text("Nome do Perfil") },
            leadingIcon = { Icon(Icons.Default.AppRegistration, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate("${AppScreens.SelectApps.route}/${uiState.profileId}")
                }
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Apps, contentDescription = null, modifier = Modifier.size(28.dp))
                Spacer(Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Aplicativos Bloqueados",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "${uiState.appCount} aplicativos selecionados",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(Icons.AutoMirrored.Filled.NavigateNext, contentDescription = null)
            }
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }


        Spacer(Modifier.height(16.dp))

        SettingsRow(
            icon = Icons.Default.Apps,
            title = "App layout",
            subtitle = "4x5",
            onClick = { onEvent(CreateProfileEvent.OnSelectAppsClick) }
        )

        SettingsRow(
            icon = Icons.Default.Wallpaper,
            title = "Wallpaper",
            subtitle = "",
            onClick = { onEvent(CreateProfileEvent.OnSelectWallpaperClick) }
        )

        SettingsRow(
            icon = Icons.Default.Alarm,
            title = "Duração",
            subtitle = "Definir tempo de uso do perfil",
            onClick = { onEvent(CreateProfileEvent.OnSetDurationClick) }
        )

        SettingsRow(
            icon = Icons.Default.NotificationsNone,
            title = "Interrupções",
            subtitle = "Gerenciar alertas e notificações",
            onClick = { onEvent(CreateProfileEvent.OnInterruptionsClick) }
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))

        SettingsSwitchRow(
            icon = Icons.Default.Password,
            title = "Requer senha",
            subtitle = "Solicitar senha para sair do modo",
            checked = uiState.isImmediatelyActive,
            onCheckedChange = { onEvent(CreateProfileEvent.OnRequirePasswordChange(it)) }
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { onEvent(CreateProfileEvent.OnSaveProfileClick) },
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.profileName.isNotBlank() && !uiState.isLoading
        ) {
            Text(
                text = if (uiState.isEditing) "Atualizar Perfil" else "Salvar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}


@Composable
private fun SettingsRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Icon(Icons.AutoMirrored.Filled.NavigateNext, contentDescription = null)
    }
}

@Composable
private fun SettingsSwitchRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}


/*@Preview(showBackground = true)
@Composable
private fun CreateProfileScreenPreview() {
    MotoUnpluggedTheme {
        CreateProfileContent(
            uiState = CreateProfileUiState(profileName = "Trabalho", appCount = 4),
            onEvent = {}
        )
    }
}*/
