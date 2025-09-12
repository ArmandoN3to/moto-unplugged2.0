package com.example.motounplugged.di

import androidx.room.Room
import com.example.motounplugged.database.MotoUnpluggedDataBase
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.repositories.ProfileRepository
import com.example.motounplugged.ui.features.createprofile.CreateProfileViewModel
import com.example.motounplugged.ui.features.profiles.ProfilesScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
//import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.koin.androidx.compose.koinViewModel


val databaseModule = module {
    single {
         Room.databaseBuilder(
            androidContext(),
            MotoUnpluggedDataBase::class.java,
            "profile.db"

        ).build()
    }
    single {
        get<MotoUnpluggedDataBase>().profilesDao()
    }

}

val repositoryModule = module {
    single {
        ProfileRepository(get())
    }
}

val viewModelModule = module {
    viewModel {
        CreateProfileViewModel(get())
    }
    viewModel {
        ProfilesScreenViewModel(get())
    }



}

