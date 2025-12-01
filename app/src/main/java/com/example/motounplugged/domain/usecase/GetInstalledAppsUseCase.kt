// domain/usecase/GetInstalledAppsUseCase.kt
package com.example.motounplugged.domain.usecase

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.example.motounplugged.models.AppInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetInstalledAppsUseCase(private val context: Context) {

    fun execute(): List<AppInfo> {
        val pm = context.packageManager

        return pm.getInstalledApplications(0)
            .filter { app ->
                // NÃO PEGAR APPS DO SISTEMA
                pm.getLaunchIntentForPackage(app.packageName) != null
            }
            .map { app ->
                AppInfo(
                    name = pm.getApplicationLabel(app).toString(),
                    packageName = app.packageName
                )
            }
            .sortedBy { it.name.lowercase() }
    }
}
