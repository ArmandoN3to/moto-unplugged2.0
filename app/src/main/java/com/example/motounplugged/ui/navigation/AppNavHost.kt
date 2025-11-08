package com.example.motounplugged.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.motounplugged.ui.features.createprofile.CreateProfileScreen
import com.example.motounplugged.ui.features.createprofile.CreateProfileViewModel
import com.example.motounplugged.ui.features.home.HomeScreen
import com.example.motounplugged.ui.features.profiles.ProfilesScreen
import com.example.motounplugged.ui.features.profiles.ProfilesScreenViewModel
import com.example.motounplugged.ui.features.schedule.ScheduleScreenViewModel
import com.example.motounplugged.ui.features.selectapps.SelectAppsScreen
import com.example.motounplugged.ui.screens.ScheduleScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Home.route,
        modifier = modifier
    ) {

        // Home Screen
        composable(AppScreens.Home.route) {
            HomeScreen(navController = navController, onClick = {})
        }

        // Profiles Screen
        composable(AppScreens.Profiles.route) {
            val profilesViewModel: ProfilesScreenViewModel = koinViewModel()
            ProfilesScreen(viewModel = profilesViewModel, navController = navController)
        }
        composable(route= AppScreens.Schedule.route){
            val scheduleViewModel: ScheduleScreenViewModel = koinViewModel()
            ScheduleScreen(viewModel = scheduleViewModel)
        }

        // Create/Edit Profile Screen (profileId opcional)
        composable(
            route = "create_profile_screen?profileId={profileId}",
            arguments = listOf(
                navArgument("profileId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val profileId = backStackEntry.arguments?.getInt("profileId")?.takeIf { it != -1 }
            val viewModel: CreateProfileViewModel = koinViewModel()
            CreateProfileScreen(navController, profileId, viewModel)
        }

        // Select Apps sem perfil (novo)
        composable("select_apps") {
            val viewModel: CreateProfileViewModel = koinViewModel()
            SelectAppsScreen(
                navController = navController,
                viewModel = viewModel,
                profileId = null
            )
        }

        // Select Apps com perfil existente
        composable(
            route = "select_apps/{profileId}",
            arguments = listOf(
                navArgument("profileId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val profileId = backStackEntry.arguments!!.getInt("profileId")
            val viewModel: CreateProfileViewModel = koinViewModel()
            SelectAppsScreen(
                navController = navController,
                viewModel = viewModel,
                profileId = profileId
            )
        }
    }
}

@Composable
fun GenericScreen(title: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = title)
    }
}
