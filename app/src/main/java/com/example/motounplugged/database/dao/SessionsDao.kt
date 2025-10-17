package com.example.motounplugged.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.motounplugged.database.entities.SessionsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionsDao {

    @Query("SELECT * FROM Sessions")
    fun findAll(): Flow<List<SessionsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(session: SessionsEntity)

    @Update
    suspend fun update(session: SessionsEntity)

    @Delete
    suspend fun delete(session: SessionsEntity)
}