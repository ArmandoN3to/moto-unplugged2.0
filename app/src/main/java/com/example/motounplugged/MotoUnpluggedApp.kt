package com.example.motounplugged

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.motounplugged.ui.navigation.AppDrawer
import com.example.motounplugged.ui.navigation.AppNavHost
import com.example.motounplugged.ui.navigation.AppScreens
import kotlinx.coroutines.launch

import com.example.motounplugged.ui.screens.FAB_new_profile

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MotoUnpluggedApp(
    onLogout: () -> Unit
    ) {
    Surface(
        modifier = Modifier .fillMaxSize(),
        color = Color.White
    ) {

    }
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedRoute = navBackStackEntry?.destination?.route ?: AppScreens.Home.route


    // Verifica se rota atual é uma tela secundária
    val isSecondaryScreen = remember(selectedRoute) {
        selectedRoute in listOf(  //lista de rotas secundarias
            AppScreens.CreateProfile.route,
            "edit_profile/{profileTitle}",
            AppScreens.SelectApps.route,


            )
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                selectedItem = selectedRoute,
                onItemSelected = { item ->
                    scope.launch { drawerState.close() }
                    if (item.route == "logout_action") {

                    } else {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    // --- 1. ÍCONE DE NAVEGAÇÃO DINÂMICO ---
                    navigationIcon = {
                        if (isSecondaryScreen) {
                            // Se for uma tela secundária, mostra o btn "voltar"
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Voltar"
                                )
                            }
                        } else {
                            // Senão, mostra o menu gaveta
                            IconButton(onClick = {
                                scope.launch { drawerState.open() }
                            }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu")
                            }
                        }
                    },

                 //utilizar essa seçao quando for mudar algum comportamento na topappbar

//                    actions = {
//                        // Verificamos a rota para adicionar ações específicas
//                        when (selectedRoute) {
//                            AppScreens.CreateProfile.route -> {
//                                TextButton(
//                                    onClick = {
//                                        println("Botão Salvar clicado!")
//                                    }
//                                ) {
//                                    Text("Salvar", fontWeight = FontWeight.Bold)
//                                }
//                            }
//                            // Adicionar ações para outras telas
//                        }
//                    }

                )
            },

            floatingActionButton = {
                when (selectedRoute) {
                    AppScreens.Profiles.route-> {
                        FAB_new_profile {
                            navController.navigate(AppScreens.CreateProfile.route) // vai pra rota createprofile
                        }
                    }
                }
            }

        ) { paddingValues ->
            AppNavHost(
                onLogout = {
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                },
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}