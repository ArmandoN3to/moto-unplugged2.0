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

    @Query("SELECT * FROM BlockedApps WHERE blockedProfileId = :profileId")
    suspend fun getBlockedAppsByProfileId(profileId: Int): List<BlockedAppsEntity>

    @Query("SELECT * FROM BlockedApps WHERE packageName = :packageName LIMIT 1")
    suspend fun findAppByPackageName(packageName: String): BlockedAppsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(app: BlockedAppsEntity)

    @Update
    suspend fun update(app: BlockedAppsEntity)

    @Delete
    suspend fun delete(app: BlockedAppsEntity)

    @Query("DELETE FROM BlockedApps WHERE blockedProfileId = :profileId")
    suspend fun deleteByProfileId(profileId: Int)
}
