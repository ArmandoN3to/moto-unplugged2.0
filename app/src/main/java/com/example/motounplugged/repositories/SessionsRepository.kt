package com.example.motounplugged.repositories

import com.example.motounplugged.database.dao.SessionsDao
import com.example.motounplugged.database.entities.SessionsEntity

class SessionsRepository (
    private val dao: SessionsDao //utilizar o banco de dados
){
    val sessions get() = dao.findAll() // puxa a nossa função do query

    //suspend fun pois usamos coroutines (atualizar dados com base em alterações - flow)
    suspend fun save(profiles: SessionsEntity){
        dao.save(profiles)
    }

    suspend fun update(profile: SessionsEntity) {
        dao.update(profile)
    }

    suspend fun delete(profiles: SessionsEntity){
        dao.delete(profiles)
    }

}