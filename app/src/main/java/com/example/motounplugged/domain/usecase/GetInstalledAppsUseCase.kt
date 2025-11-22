// domain/usecase/GetInstalledAppsUseCase.kt
package com.example.motounplugged.domain.usecase

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.example.motounplugged.models.AppInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetInstalledAppsUseCase(private val context: Context) {

    suspend fun execute(): List<AppInfo> = withContext(Dispatchers.IO) {
        val packageManager = context.packageManager
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        apps.filter { it.flags and ApplicationInfo.FLAG_SYSTEM == 0 } // Filtra apps de sistema
            .map {
                AppInfo(
                    name = it.loadLabel(packageManager).toString(),
                    packageName = it.packageName,
                )
            }
            .sortedBy { it.name.lowercase() } // Ordena por nome
    }
}