package com.example.motounplugged.ui.features.createprofile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.AppRegistration
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.navigation.compose.rememberNavController
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateProfileScreen(
    navController: NavController,
    viewModel: CreateProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.
        padding(horizontal = 1.dp),

        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                actions = {
                    TextButton(onClick = { viewModel.onEvent(CreateProfileEvent.OnSaveProfileClick) }) {
                        Text("Salvar", fontWeight = FontWeight.Bold)
                    }
                }
            )
        }
    ) { innerPadding ->
        CreateProfileContent(
            modifier = Modifier.padding(innerPadding),
            uiState = uiState,
            onEvent = viewModel::onEvent // Passa a referência da função de eventos
        )
    }
}

@Composable
private fun CreateProfileContent(
    modifier: Modifier = Modifier,
    uiState: CreateProfileUiState,
    onEvent: (CreateProfileEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Criar Novo Perfil",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Seção para o nome do perfil
        OutlinedTextField(
            value = uiState.profileName,
            onValueChange = { onEvent(CreateProfileEvent.OnProfileNameChange(it)) },
            label = { Text("Nome do Perfil") },
            leadingIcon = { Icon(Icons.Default.AppRegistration, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(Modifier.height(24.dp))

        // Card para selecionar apps
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.clickable { onEvent(CreateProfileEvent.OnSelectAppsClick) }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.Apps, contentDescription = null, modifier = Modifier.size(28.dp))
                Spacer(Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("Aplicativos Bloqueados", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
                    Text(
                        if (uiState.selectedApps.isEmpty()) "Nenhum aplicativo selecionado" else "${uiState.selectedApps.size} aplicativos selecionados",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(Icons.AutoMirrored.Filled.NavigateNext, contentDescription = null)
            }
        }

        Spacer(Modifier.height(16.dp))


        // Item para Agendamento
        SettingsRow(
            icon = Icons.Default.Notifications,
            title = "Agendamento",
            subtitle = "Definir horários de ativação",
            onClick = { onEvent(CreateProfileEvent.OnSchedulingClick) }
        )

        Divider(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp))

        // Item para o Switch
        SettingsSwitchRow(
            icon = Icons.Default.Password,
            title = "Ativar ao salvar",
            subtitle = "O perfil será ativado imediatamente",
            checked = uiState.isImmediatelyActive,
            onCheckedChange = { onEvent(CreateProfileEvent.OnActivateImmediatelyChange(it)) }
        )
    }
}

// --- Componentes Reutilizáveis para a Tela ---

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
            Text(title, style = MaterialTheme.typography.bodyLarge)
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


@Preview(showBackground = true)
@Composable
private fun CreateProfileScreenPreview() {
    MotoUnpluggedTheme {
        CreateProfileContent(
            uiState = CreateProfileUiState(profileName = "Trabalho"),
            onEvent = {}
        )
    }
}