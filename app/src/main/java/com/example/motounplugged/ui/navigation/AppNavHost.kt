package com.example.motounplugged.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.motounplugged.ui.screens.ProfilesScreen
import com.example.motounplugged.ui.features.createprofile.CreateProfileScreen
import com.example.motounplugged.ui.features.createprofile.CreateProfileViewModel
import com.example.motounplugged.ui.features.settings.about.AboutSettingsScreen
import com.example.motounplugged.ui.features.home.HomeScreen
import com.example.motounplugged.ui.features.login.LoginScreen
import com.example.motounplugged.ui.features.profiles.ProfilesScreenViewModel
import com.example.motounplugged.ui.features.register.RegisterScreen
import com.example.motounplugged.ui.features.schedule.ScheduleScreen
import com.example.motounplugged.ui.features.settings.SettingsScreen
import com.example.motounplugged.ui.features.stats.StatsScreen
import com.example.motounplugged.ui.features.streak.StreakScreen
import com.example.motounplugged.ui.features.selectapps.SelectAppsScreen
import com.example.motounplugged.ui.features.schedule.ScheduleScreenViewModel
import com.example.motounplugged.ui.features.splashscreen.SplashScreen

import com.example.motounplugged.ui.features.settings.general.GeneralSettingsScreen

import org.koin.androidx.compose.koinViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    onLogout: () -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    // ViewModel global para consultar perfis
    val profilesViewModel: ProfilesScreenViewModel = koinViewModel()
    val profiles by profilesViewModel.profiles.collectAsState(initial = emptyList())

    // PERFIL ATIVO (se houver)
    val activeProfile = profiles.firstOrNull { it.isImmediatelyActive }
    val activeProfileId = activeProfile?.idProfile

    NavHost(
        navController = navController,
        startDestination = AppScreens.Home.route,
        modifier = modifier
    ) {

        composable(AppScreens.SplashScreen.route) {
            SplashScreen(navController = navController)
        }

        composable(AppScreens.Home.route) {
            HomeScreen(navController = navController, onClick = {})
        }

        composable(AppScreens.Stats.route) {
            StatsScreen()
        }

        composable(AppScreens.Profiles.route) {
            ProfilesScreen(
                viewModel = profilesViewModel,
                navController = navController
            )
        }

        composable(AppScreens.Settings.route) {
            SettingsScreen(navController = navController)
        }

        composable(AppScreens.GeneralSettings.route) {
            GeneralSettingsScreen()
        }

        composable(AppScreens.AboutSettings.route) {
            AboutSettingsScreen()
        }

        composable(AppScreens.Streak.route) {
            StreakScreen(streakCount = 28)
        }

        // ---- CREATE PROFILE ----
        composable(
            route = "create_profile_screen?profileId={profileId}",
            arguments = listOf(
                navArgument("profileId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val profileId = backStackEntry.arguments?.getInt("profileId") ?: -1
            CreateProfileScreen(navController, profileId)
        }

        // ---- SELECT APPS ----
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
            SelectAppsScreen(navController, id)
        }

        // ---- SCHEDULE SCREEN ----
        composable(
            route = AppScreens.Schedule.route, // "schedule"
        ) {

            // Removida a lógica de redirecionamento agressivo (LaunchedEffect).
            // A tela Schedule é sempre exibida. O ViewModel (koinViewModel) se encarrega
            // de buscar o perfil ativo no 'init' e selecionar o ID correto.

            // Usamos o ID ativo se existir, ou -1 como um placeholder.
            val idToUse = activeProfileId ?: -1

            ScheduleScreen(
                profileId = idToUse, // Passa o ID (ativo ou -1)
                viewModel = koinViewModel()
            )
        }

        // ---- SCHEDULE WITH PROFILE ID (edição manual) ----
        composable(
            route = "schedule/{profileId}",
            arguments = listOf(navArgument("profileId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("profileId") ?: -1
            ScheduleScreen(
                profileId = id,
                viewModel = koinViewModel()
            )
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
