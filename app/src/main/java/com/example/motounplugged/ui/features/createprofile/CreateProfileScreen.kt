package com.example.motounplugged.ui.features.createprofile

import android.app.Activity
import android.app.KeyguardManager
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.filled.BatterySaver
import androidx.compose.material.icons.filled.Close


import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.motounplugged.ui.navigation.AppScreens
import com.example.motounplugged.models.AppInfo
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import coil.compose.AsyncImage



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
            viewModel.loadProfile(profileId)  // agora só roda se não tiver carregado antes
        }
    }

    // Aplicativos salvos
    val selectedApps = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow<List<AppInfo>?>("selectedApps", null)
        ?.collectAsState()
        ?.value

    // Retorna aplicativos salvos
    LaunchedEffect(selectedApps) {
        selectedApps?.let { viewModel.setSelectedApps(it) }
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
    // Launcher para seleção de wallpaper
    val wallpaperPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            onEvent(CreateProfileEvent.OnWallpaperSelected(uri.toString()))
        }
    }

    var durationText by remember { mutableStateOf("") }

    LaunchedEffect(uiState.duration) {
        if (uiState.isEditing || uiState.duration != 0) {
            durationText = uiState.duration.toString()
        }
    }
    // Salva se o usuário confirmou sua senha
    var pendingPasswordRequired by remember { mutableStateOf<Boolean?>(null) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = uiState.profileName,
            onValueChange = { onEvent(CreateProfileEvent.OnProfileNameChange(it)) },
            label = { Text("Nome do Perfil") },
            leadingIcon = { Icon(Icons.Default.AppRegistration, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Card para selecionar apps
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        "selectedApps",
                        uiState.selectedApps   // LIST<AppInfo>
                    )
                    val id = uiState.profileId ?: -1
                    navController.navigate("${AppScreens.SelectApps.route}/$id")
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

        Column {
            SettingsRow(
                icon = Icons.Default.Wallpaper,
                title = "Wallpaper",
                subtitle = if (uiState.wallpaperUri != null) "Selecionado" else "Nenhum selecionado",
                onClick = { wallpaperPicker.launch("image/*") }
            )

            if (uiState.wallpaperUri != null) {
                Spacer(Modifier.height(8.dp))
                Box {
                    AsyncImage(
                        model = uiState.wallpaperUri,
                        contentDescription = "Preview wallpaper",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )

                    IconButton(
                        modifier = Modifier.align(Alignment.TopEnd),
                        onClick = {onEvent(CreateProfileEvent.OnClearWallpaper)}
                    ){
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remove Wallpaper"
                        )
                    }
                }
            }
        }

        val context = LocalContext.current

        SettingsSwitchRow(
            icon = Icons.Default.NotificationsNone,
            title = "Interrupções",
            subtitle = "Gerenciar alertas e notificações",
            checked = uiState.interruptions,
            onCheckedChange = {
                val notificationsManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as
                            NotificationManager
                if (!notificationsManager.isNotificationPolicyAccessGranted){
                    // Força o app a abrir o dnd para permissão
                    try {
                        notificationsManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE)
                    }catch (e: SecurityException){
                    }
                    context.startActivity(
                        Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                    )
                } else {
                    onEvent(CreateProfileEvent.OnInterruptionsClick(it))
                }

            }
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp))

        OutlinedTextField(
            value = durationText,
            onValueChange = { value ->
                durationText = value

                val number = value.toIntOrNull()
                if (number != null) {
                    onEvent(CreateProfileEvent.OnDurationChanged(number))
                }
            },
            label = { Text("Duração (minutos)") },
            leadingIcon = { Icon(Icons.Default.Alarm, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused && durationText.isBlank()) {
                        // volta ao valor real salvo no viewmodel
                        durationText = "0"
                    }
                },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true
        )


        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp))

        val keyguardManager =
            context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        val unlockLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // agora sim altera o estado real
                pendingPasswordRequired?.let {
                    onEvent(CreateProfileEvent.OnRequirePasswordChange(it))
                }
            }

            // limpa o estado temporário
            pendingPasswordRequired = null
        }

        SettingsSwitchRow(
            icon = Icons.Default.Password,
            title = "Requer senha",
            subtitle =
                if (uiState.passwordRequired)
                    "Senha do dispositivo será exigida para sair do modo"
                else
                    "Desativado",
            checked = uiState.passwordRequired,
            onCheckedChange = { newValue ->

                // guarda a intenção do usuário
                pendingPasswordRequired = newValue

                if (keyguardManager.isDeviceSecure) {
                    val intent = keyguardManager.createConfirmDeviceCredentialIntent(
                        "Confirmar identidade",
                        "Digite a senha do dispositivo para alterar esta configuração"
                    )
                    unlockLauncher.launch(intent)
                }
            }
        )

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
