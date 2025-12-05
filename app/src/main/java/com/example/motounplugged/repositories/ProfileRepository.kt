package com.example.motounplugged.repositories

import com.example.motounplugged.database.dao.BlockedAppsDao
import com.example.motounplugged.database.dao.ProfilesDao
import com.example.motounplugged.database.entities.BlockedAppsEntity
import com.example.motounplugged.database.entities.ProfileBlockedApps
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.models.AppInfo
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
    suspend fun save(profile: ProfilesEntity): Int {
        return dao.save(profile).toInt()
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

    suspend fun replaceBlockedApps(profileId: Int, apps: List<AppInfo>) {
        // Remove todos os apps atuais do perfil
        blockedAppsDao.deleteAllBlockedAppsFromProfile(profileId)

        // Cria entidades para cada app
        val entities = apps.map {
            BlockedAppsEntity(
                nameApp = it.name,
                packageName = it.packageName,
                blockedProfileId = profileId
            )
        }

        // Salva tudo de uma vez
        blockedAppsDao.saveBlockedApps(entities)

    }

    suspend fun getDurationFromProfile(profileId: Int): Int{
        return dao.getDurationFromProfile(profileId)

    }

}