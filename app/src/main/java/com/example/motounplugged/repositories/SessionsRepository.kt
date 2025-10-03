package com.example.motounplugged.repositories

import com.example.motounplugged.database.dao.SessionsDao
import com.example.motounplugged.database.entities.SessionsEntity

class SessionsRepository(
    private val dao: SessionsDao // utilizar o banco de dados
) {
    val sessions get() = dao.findAll() // retorna o Flow<List<SessionsEntity>>

    suspend fun save(session: SessionsEntity) {
        dao.save(session)
    }

    suspend fun update(session: SessionsEntity) {
        dao.update(session)
    }

    suspend fun delete(session: SessionsEntity) {
        dao.delete(session)
    }
}
