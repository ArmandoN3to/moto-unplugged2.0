package com.example.motounplugged

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.motounplugged.ui.navigation.AppDrawer
import com.example.motounplugged.ui.navigation.AppNavHost
import com.example.motounplugged.ui.navigation.AppScreens
import kotlinx.coroutines.launch

import com.example.motounplugged.ui.screens.FAB_new_profile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MotoUnpluggedApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedRoute = navBackStackEntry?.destination?.route ?: AppScreens.Home.route

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
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply { if (isClosed) open() else close() }
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
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
                navController = navController,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}