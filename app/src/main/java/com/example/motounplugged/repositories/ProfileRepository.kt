package com.example.motounplugged.repositories

import com.example.motounplugged.database.dao.BlockedAppsDao
import com.example.motounplugged.database.dao.ProfilesDao
import com.example.motounplugged.database.entities.BlockedAppsEntity
import com.example.motounplugged.database.entities.ProfilesEntity
import kotlinx.coroutines.flow.Flow

class ProfileRepository(
    private val profilesDao: ProfilesDao,
    private val blockedAppsDao: BlockedAppsDao
) {

    fun getAllProfiles(): Flow<List<ProfilesEntity>> = profilesDao.findAll()

    suspend fun getProfileById(id: Int): ProfilesEntity? =
        profilesDao.getProfileById(id)

    suspend fun getProfileWithBlockedApps(id: Int) =
        profilesDao.getProfileWithBlockedApps(id)

    suspend fun save(profile: ProfilesEntity, selectedApps: List<String>) {
        // Insere o perfil e obtém o ID gerado
        val profileId = profilesDao.insert(profile).toInt()

        // Cria entidades BlockedAppsEntity vinculadas a esse perfil
        val blockedApps = selectedApps.map { packageName ->
            BlockedAppsEntity(
                nameApp = packageName.substringAfterLast('.'),
                categoryApp = "Unknown", // ou pode ser preenchido via PackageManager
                packageName = packageName,
                blockedProfileId = profileId
            )
        }

        // Insere os aplicativos bloqueados
        blockedApps.forEach { blockedAppsDao.insert(it) }
    }

    suspend fun update(profile: ProfilesEntity, selectedApps: List<String>) {
        profilesDao.update(profile)

        // Atualiza lista de apps bloqueados
        blockedAppsDao.deleteByProfileId(profile.idProfile)
        val newBlockedApps = selectedApps.map { pkg ->
            BlockedAppsEntity(
                nameApp = pkg.substringAfterLast('.'),
                categoryApp = "Unknown",
                packageName = pkg,
                blockedProfileId = profile.idProfile
            )
        }
        newBlockedApps.forEach { blockedAppsDao.insert(it) }
    }

    suspend fun delete(profile: ProfilesEntity) {
        profilesDao.delete(profile)
        blockedAppsDao.deleteByProfileId(profile.idProfile)
    }
}
