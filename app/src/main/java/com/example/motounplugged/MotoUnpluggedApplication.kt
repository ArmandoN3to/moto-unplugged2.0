package com.example.motounplugged

import android.app.Application
import com.example.motounplugged.di.databaseModule
import com.example.motounplugged.di.repositoryModule
import com.example.motounplugged.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MotoUnpluggedApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@MotoUnpluggedApplication)
            modules(listOf(databaseModule,repositoryModule, viewModelModule))
        }
    }

}