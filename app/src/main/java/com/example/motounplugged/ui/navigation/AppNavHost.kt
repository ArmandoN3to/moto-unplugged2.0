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
import com.example.motounplugged.ui.features.register.RegisterScreen
import com.example.motounplugged.ui.features.settings.SettingsScreen
import com.example.motounplugged.ui.features.stats.StatsScreen
import com.example.motounplugged.ui.features.streak.StreakScreen
import com.example.motounplugged.ui.features.user.UserScreen
import com.example.motounplugged.ui.features.selectapps.SelectAppsScreen
import com.example.motounplugged.ui.features.schedule.ScheduleScreenViewModel
import com.example.motounplugged.ui.features.terms_and_conditions.TermsAndConditionsScreen
import org.koin.androidx.compose.koinViewModel


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppScreensRouter.Home.route,
        modifier = modifier
    ) {
        composable(AppScreensRouter.Home.route) {
            HomeScreen( navController = navController,onClick = {})
        }
        composable(AppScreensRouter.Schedule.route) {
            val ScheduleScreenViewModel: ScheduleScreenViewModel = koinViewModel()
            ScheduleScreen( viewModel = ScheduleScreenViewModel)
        }
        composable(AppScreensRouter.Stats.route) {
            StatsScreen()
        }
        composable(AppScreensRouter.Profiles.route) {
            val profilesViewModel: ProfilesScreenViewModel = koinViewModel()
            ProfilesScreen( viewModel =  profilesViewModel)
        }
        composable(AppScreensRouter.User.route) {
            UserScreen()
        }
        composable(AppScreensRouter.Settings.route) {
            SettingsScreen()
        }
        composable(AppScreensRouter.Streak.route) {
            StreakScreen(
                streakCount = 28
            )
        }
        composable( AppScreensRouter.Register.route){
            RegisterScreen(onRegisterComplete = {})
        }
        composable(AppScreensRouter.TermsAndConditionsScreen.route){
            TermsAndConditionsScreen()
        }
        composable(AppScreensRouter.CreateProfile.route) {
            CreateProfileScreen(navController = navController)
        }
        composable(AppScreensRouter.SelectApps.route) {
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
