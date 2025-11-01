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
//import java.util.concurrent.Flow as JavaUtilConcurrentFlow

@Dao
interface ProfilesDao {
    @Query("SELECT * FROM Profiles")
    fun findAll(): Flow<List<ProfilesEntity>>

    @Query("SELECT * FROM Profiles WHERE idProfile== :id LIMIT 1")
    suspend fun getProfileById(id: Int): ProfilesEntity?

    @Transaction
    @Query("SELECT * FROM Profiles WHERE idProfile = :id")
    suspend fun getProfileWithBlockedApps(id: Int): ProfileBlockedApps?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(profiles: ProfilesEntity)

    @Update
    suspend fun update(profiles: ProfilesEntity)

    @Delete
    suspend fun delete(profiles: ProfilesEntity)

}