package com.example.motounplugged.database.dao

import androidx.room.*
import com.example.motounplugged.database.entities.SessionsEntity

@Dao
interface SessionsDao {

    @Query("SELECT * FROM Sessions WHERE idProfile = :profileId")
    suspend fun getSessionsByProfile(profileId: Int): List<SessionsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: SessionsEntity)

    @Delete
    suspend fun deleteSession(session: SessionsEntity)
}

