package com.example.motounplugged.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.motounplugged.database.entities.ProfilesEntity
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Flow as JavaUtilConcurrentFlow

@Dao
interface ProfilesDao {

    @Query("SELECT * FROM Profiles")
    fun findAll(): Flow<List<ProfilesEntity>>

    // cada vez que salvar se é algo que já existe ele substitui e se é algo novo ele gera um novo registro
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(profiles: ProfilesEntity)

    @Delete
    suspend fun delete(profiles: ProfilesEntity)




}