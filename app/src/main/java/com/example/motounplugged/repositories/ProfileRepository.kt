package com.example.motounplugged.repositories

import com.example.motounplugged.database.dao.BlockedAppsDao
import com.example.motounplugged.database.dao.ProfilesDao
import com.example.motounplugged.database.entities.BlockedAppsEntity
import com.example.motounplugged.database.entities.ProfileBlockedApps
import com.example.motounplugged.database.entities.ProfilesEntity
import kotlinx.coroutines.flow.Flow

//repository vai salvar meus dados da DAO
class ProfileRepository (
    private val dao: ProfilesDao, //utilizar o banco de dados
    private val blockedAppsDao: BlockedAppsDao

){
    val profiles get() = dao.findAll() // puxa a nossa função do query

    suspend fun getProfileById(id: Int): ProfilesEntity?{
        return dao.getProfileById(id)
    }
    //suspend fun pois usamos coroutines (atualizar dados com base em alterações - flow)
   suspend fun save(profiles: ProfilesEntity){
        dao.save(profiles)
    }

    suspend fun update(profile: ProfilesEntity) {
        dao.update(profile)
    }

    suspend fun delete(profiles:ProfilesEntity){
        dao.delete(profiles)
    }
    // Retorna Profile com os apps bloqueados
    suspend fun getProfileWithApps(id: Int): ProfileBlockedApps? {
        return dao.getProfileWithBlockedApps(id)
    }
    // Adiciona app bloqueado ao profile selecionado
    suspend fun addBlockedApp(profileId: Int, appName: String, packageName: String) {
        val app = BlockedAppsEntity(
            nameApp = appName,
            packageName = packageName,
            blockedProfileId = profileId
        )
        blockedAppsDao.save(app)
    }

}