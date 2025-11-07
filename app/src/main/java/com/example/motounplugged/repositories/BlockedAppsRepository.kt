package com.example.motounplugged.repositories

import com.example.motounplugged.database.dao.BlockedAppsDao
import com.example.motounplugged.database.entities.BlockedAppsEntity

class BlockedAppsRepository(
    private val dao: BlockedAppsDao
) {
    val blockedApps get() = dao.findAll()

    suspend fun getBlockedAppByPackageName(packageName: String): BlockedAppsEntity? {
        return dao.findAppByPackageName(packageName)
    }

    suspend fun save(blockedApp: BlockedAppsEntity) {
        dao.insert(blockedApp)
    }

    suspend fun update(blockedApp: BlockedAppsEntity) {
        dao.update(blockedApp)
    }

    suspend fun delete(blockedApp: BlockedAppsEntity) {
        dao.delete(blockedApp)
    }

    suspend fun deleteByProfile(profileId: Int) {
        dao.deleteByProfileId(profileId)
    }

    suspend fun saveAll(apps: List<BlockedAppsEntity>) {
        apps.forEach { dao.insert(it) }
    }
}
