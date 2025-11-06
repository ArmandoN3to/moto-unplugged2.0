package com.example.motounplugged.di

import androidx.room.Room
import com.example.motounplugged.database.MotoUnpluggedDataBase
import com.example.motounplugged.repositories.BlockedAppsRepository
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.domain.usecase.GetInstalledAppsUseCase
import com.example.motounplugged.repositories.ProfileRepository
import com.example.motounplugged.repositories.SessionsRepository
import com.example.motounplugged.ui.features.createprofile.CreateProfileViewModel
import com.example.motounplugged.ui.features.profiles.ProfilesScreenViewModel
import com.example.motounplugged.ui.features.selectapps.SelectAppsViewModel

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.android.ext.koin.androidApplication

//import org.koin.core.module.dsl.viewModel
import com.example.motounplugged.ui.features.schedule.ScheduleScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// --- Banco de dados ---
val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            MotoUnpluggedDataBase::class.java,
            "profile.db"
        ).build()
    }

    // DAOs
    single { get<MotoUnpluggedDataBase>().profilesDao() }
    single { get<MotoUnpluggedDataBase>().sessionsDao() }
    single { get<MotoUnpluggedDataBase>().blockedAppsDao() }

}

// --- Repositórios ---
val repositoryModule = module {
    single { ProfileRepository(get(), blockedAppsDao = get()) }
    single { SessionsRepository(get()) }
    single { BlockedAppsRepository(get()) }
}

val useCaseModule = module {
    factory { GetInstalledAppsUseCase(androidApplication()) }
}

val viewModelModule = module {
    viewModel {
        SelectAppsViewModel(get())
    }
    viewModel {
        CreateProfileViewModel(get())
    }
    viewModel {
        ProfilesScreenViewModel(get())
    }
    viewModel{
        ScheduleScreenViewModel(
            repository = get() ,
            profilesRepository = get()
        )
    }

}
