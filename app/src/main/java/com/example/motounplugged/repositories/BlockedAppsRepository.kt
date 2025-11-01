package com.example.motounplugged.repositories

import com.example.motounplugged.database.dao.BlockedAppsDao
import com.example.motounplugged.database.entities.BlockedAppsEntity

class BlockedAppsRepository(
    private val dao: BlockedAppsDao
) {
    val blockedApps get() = dao.findAll() // puxa a nossa função do query

    suspend fun getBlockedAppById(id: Int): BlockedAppsEntity?{
        return dao.findAppbyId(id)
    }
    //suspend fun pois usamos coroutines (atualizar dados com base em alterações - flow)
    suspend fun save(blockedApp: BlockedAppsEntity){
        dao.save(blockedApp)
    }

    suspend fun update(blockedApp: BlockedAppsEntity) {
        dao.update(blockedApp)
    }

    suspend fun delete(blockedApp: BlockedAppsEntity){
        dao.delete(blockedApp)
    }

}
