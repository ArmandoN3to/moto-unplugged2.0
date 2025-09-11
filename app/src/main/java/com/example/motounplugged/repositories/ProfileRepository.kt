package com.example.motounplugged.repositories

import com.example.motounplugged.database.dao.ProfilesDao
import com.example.motounplugged.database.entities.ProfilesEntity
import kotlinx.coroutines.flow.Flow

//repository vai salvar meus dados da DAO
class ProfileRepository (
    private val dao: ProfilesDao //utilizar o banco de dados
){
    val profiles get() = dao.findAll() // puxa a nossa função do query

    //suspend fun pois usamos coroutines (atualizar dados com base em alterações - flow)
   suspend fun save(profiles: ProfilesEntity){
        dao.save(profiles)
    }

    suspend fun delete(profiles:ProfilesEntity){
        dao.delete(profiles)
    }

}