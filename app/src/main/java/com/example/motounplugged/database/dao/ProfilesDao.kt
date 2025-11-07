package com.example.motounplugged.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.motounplugged.database.entities.ProfileBlockedApps
import com.example.motounplugged.database.entities.ProfilesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfilesDao {

    // Retorna todos os perfis cadastrados
    @Query("SELECT * FROM Profiles")
    fun findAll(): Flow<List<ProfilesEntity>>

    // Retorna um perfil específico
    @Query("SELECT * FROM Profiles WHERE idProfile = :id LIMIT 1")
    suspend fun getProfileById(id: Int): ProfilesEntity?

    // Retorna um perfil com seus aplicativos bloqueados (JOIN implícito via @Relation)
    @Transaction
    @Query("SELECT * FROM Profiles WHERE idProfile = :id")
    suspend fun getProfileWithBlockedApps(id: Int): ProfileBlockedApps?

    // Insere um novo perfil e retorna o ID gerado
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: ProfilesEntity): Long

    // Atualiza um perfil existente
    @Update
    suspend fun update(profile: ProfilesEntity)

    // Remove um perfil
    @Delete
    suspend fun delete(profile: ProfilesEntity)
}
