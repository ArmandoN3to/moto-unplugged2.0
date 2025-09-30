package com.example.motounplugged.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.motounplugged.ui.screens.ProfilesScreen
import com.example.motounplugged.ui.screens.ScheduleScreen
import com.example.motounplugged.ui.features.createprofile.CreateProfileScreen
import com.example.motounplugged.ui.features.home.HomeScreen
import com.example.motounplugged.ui.features.profiles.ProfilesScreenViewModel
import com.example.motounplugged.ui.features.settings.SettingsScreen
import com.example.motounplugged.ui.features.stats.StatsScreen
import com.example.motounplugged.ui.features.streak.StreakScreen
import com.example.motounplugged.ui.features.user.UserScreen
import com.example.motounplugged.ui.features.selectapps.SelectAppsScreen
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
            ScheduleScreen()
        }
        composable(AppScreens.Stats.route) {
            StatsScreen()
        }
        composable(AppScreens.Profiles.route) {
            val profilesViewModel: ProfilesScreenViewModel = koinViewModel()
            ProfilesScreen( viewModel =  profilesViewModel)
        }
        composable(AppScreens.User.route) {
            UserScreen()
        }
        composable(AppScreens.Settings.route) {
            SettingsScreen()
        }
        composable(AppScreens.Streak.route) {
            StreakScreen()
        }
        composable(AppScreens.CreateProfile.route) {
            CreateProfileScreen(navController = navController)
        }
        composable(AppScreens.SelectApps.route) {
            SelectAppsScreen(navController = navController)
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
