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

    @Query("SELECT * FROM BlockedApps where uniqueIdApp == :id LIMIT 1")
    fun findAppbyId(id: Int): BlockedAppsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(profiles: BlockedAppsEntity)

    @Update
    suspend fun update(profiles: BlockedAppsEntity)

    @Delete
    suspend fun delete(profiles: BlockedAppsEntity)

}