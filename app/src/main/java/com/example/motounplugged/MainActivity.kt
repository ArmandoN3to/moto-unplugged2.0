package com.example.motounplugged

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.motounplugged.ui.navigation.AppDrawer
import com.example.motounplugged.ui.navigation.NavigationItem
import com.example.motounplugged.ui.navigation.DrawerItems
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MotoUnpluggedTheme {
                var selectedRoute by remember { mutableStateOf("home") }
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope() //

                ModalNavigationDrawer(
                    drawerState = drawerState,
                    drawerContent = {
                        AppDrawer(
                            selectedItem = selectedRoute,
                            onItemSelected = { item: NavigationItem ->
                                selectedRoute = item.route
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("") },
                                navigationIcon = {
                                    IconButton(onClick = {
                                        scope.launch {
                                            if (drawerState.isClosed) {
                                                drawerState.open()
                                            } else {
                                                drawerState.close()
                                            }
                                        }
                                    }) {
                                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                                    }
                                }
                            )
                        }
                    ) { paddingValues ->
                        Surface(modifier = Modifier.padding(paddingValues)) {
                            when (selectedRoute) {
                                "home" -> Text("Tela Início")
                                "schedule" -> Text("Tela Agendar")
                                "stats" -> Text("Tela Estatísticas")
                                "profiles" -> Text("Tela Perfis")
                                "user" -> Text("Tela Usuário")
                                "settings" -> Text("Tela Configurações")
                            }
                        }
                    }
                }
            }
        }
    }
}
