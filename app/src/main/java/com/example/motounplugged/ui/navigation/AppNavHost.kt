package com.example.motounplugged.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.motounplugged.ui.screens.ProfilesScreen
import com.example.motounplugged.ui.screens.ScheduleScreen
import com.example.motounplugged.ui.features.createprofile.CreateProfileScreen
import com.example.motounplugged.ui.features.createprofile.CreateProfileViewModel
import com.example.motounplugged.ui.features.home.HomeScreen
import com.example.motounplugged.ui.features.login.LoginScreen
import com.example.motounplugged.ui.features.profiles.ProfilesScreenViewModel
import com.example.motounplugged.ui.features.register.RegisterScreen
import com.example.motounplugged.ui.features.settings.SettingsScreen
import com.example.motounplugged.ui.features.stats.StatsScreen
import com.example.motounplugged.ui.features.streak.StreakScreen
import com.example.motounplugged.ui.features.selectapps.SelectAppsScreen
import com.example.motounplugged.ui.features.schedule.ScheduleScreenViewModel
import com.example.motounplugged.ui.features.splashscreen.SplashScreen
import com.example.motounplugged.ui.features.settings.UserScreen
import com.example.motounplugged.ui.features.terms_and_conditions.TermsAndConditionsScreen
import org.koin.androidx.compose.koinViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    onLogout: () -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.Home.route,
        modifier = modifier
    ) {

        composable(AppScreens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(AppScreens.Home.route) {
            HomeScreen( navController = navController,onClick = {})
        }
        composable(AppScreens.Schedule.route) {
            val ScheduleScreenViewModel: ScheduleScreenViewModel = koinViewModel()
            ScheduleScreen( viewModel = ScheduleScreenViewModel)
        }
        composable(AppScreens.Stats.route) {
            StatsScreen()
        }
        composable(AppScreens.Profiles.route) {
            val profilesViewModel: ProfilesScreenViewModel = koinViewModel()
            ProfilesScreen( viewModel =  profilesViewModel, navController)
        }
        composable(AppScreens.User.route) {
            UserScreen()
        }
        composable(AppScreens.Settings.route) {
            SettingsScreen()
        }
        composable(AppScreens.Streak.route) {
            StreakScreen(
                streakCount = 28
            )
        }
        composable(
            route = "create_profile_screen?profileId={profileId}", // Rota com argumento opcional
            arguments = listOf(
                navArgument("profileId") {
                    type = NavType.IntType
                    defaultValue = -1 // Valor padrão se não for fornecido
                }
            )
        ) { backStackEntry ->
            val profileId = backStackEntry.arguments?.getInt("profileId") ?: -1
            CreateProfileScreen(navController, profileId)
        }

        composable(
            route = AppScreens.SelectApps.route + "/{profileId}",
            arguments = listOf(
                navArgument("profileId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            val id = it.arguments?.getInt("profileId") ?: -1
            SelectAppsScreen(navController = navController, profileId = id)
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
