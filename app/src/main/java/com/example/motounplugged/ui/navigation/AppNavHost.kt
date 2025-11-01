package com.example.motounplugged.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.motounplugged.ui.screens.ProfilesScreen
import com.example.motounplugged.ui.screens.ScheduleScreen
import com.example.motounplugged.ui.features.createprofile.CreateProfileScreen
import com.example.motounplugged.ui.features.createprofile.CreateProfileViewModel
import com.example.motounplugged.ui.features.home.HomeScreen
import com.example.motounplugged.ui.features.profiles.ProfilesScreenViewModel
import com.example.motounplugged.ui.features.schedule.ScheduleScreenViewModel
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
        composable(AppScreens.Home.route) {
            HomeScreen()
        }
        composable(AppScreens.Schedule.route) {
            val ScheduleScreenViewModel: ScheduleScreenViewModel = koinViewModel()
            ScheduleScreen( viewModel = ScheduleScreenViewModel)
        }
        composable(AppScreens.Stats.route) {
            GenericScreen("Tela estatisticas")

        }
        composable(AppScreens.Profiles.route) {
            val profilesViewModel: ProfilesScreenViewModel = koinViewModel()
            ProfilesScreen( viewModel =  profilesViewModel, navController)
        }
        composable(AppScreens.User.route) {
            GenericScreen("Tela Usuário")
        }
        composable(AppScreens.Settings.route) {
            GenericScreen("Tela Configurações")
        }
        composable(AppScreens.Streak.route) {
            GenericScreen("Tela Streak")
        }
        composable(
            route = "create_profile_screen?profileId={profileId}",
            arguments = listOf(
                navArgument("profileId") {
                    type = NavType.IntType
                    defaultValue = -1  // valor padrão se não passar
                }
            )
        ) { backStackEntry ->
            val profileId = backStackEntry.arguments?.getInt("profileId") ?: -1
            CreateProfileScreen(navController, profileId)
        }
    }
}

@Composable
fun GenericScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = title)
    }
}
