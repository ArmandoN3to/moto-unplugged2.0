package com.example.motounplugged.repositories

import com.example.motounplugged.database.dao.SessionsDao
import com.example.motounplugged.database.entities.SessionsEntity
import java.time.LocalDateTime

class SessionsRepository(
    private val dao: SessionsDao
) {

    suspend fun getSessionsForProfile(profileId: Int): List<SessionsEntity> {
        return dao.getSessionsByProfile(profileId)
    }

    suspend fun addSession(session: SessionsEntity) {
        dao.insertSession(session)
    }

    suspend fun deleteSession(session: SessionsEntity) {
        dao.deleteSession(session)
    }

}
