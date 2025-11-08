package com.example.motounplugged.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.motounplugged.database.entities.BlockedAppsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BlockedAppsDao {
    @Query("SELECT * FROM BlockedApps")
    fun findAll(): Flow<List<BlockedAppsEntity>>

    @Query("SELECT * FROM BlockedApps where packageName == :packageName LIMIT 1")
    fun findAppByPackageName(packageName: String): BlockedAppsEntity?

    @Query("SELECT * FROM BlockedApps WHERE blockedProfileId = :profileId")
    suspend fun getBlockedAppsByProfile(profileId: Int): List<BlockedAppsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(blockedApps: BlockedAppsEntity)

    @Update
    suspend fun update(blockedApps: BlockedAppsEntity)

    @Delete
    suspend fun delete(blockedApps: BlockedAppsEntity)

    @Query("DELETE FROM BlockedApps WHERE blockedProfileId = :profileId")
    suspend fun deleteAllBlockedAppsFromProfile(profileId: Int)
}