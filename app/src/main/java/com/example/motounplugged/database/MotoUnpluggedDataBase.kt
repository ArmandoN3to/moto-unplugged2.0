package com.example.motounplugged.database

import androidx.room.Database
import com.example.motounplugged.database.dao.ProfilesDao
import com.example.motounplugged.database.entities.ProfilesEntity

//database vai guardar as infomações, precisa passar as entidades e a lista que tem a entidade fazendo referência da class dela e a versão
@Database(entities = [ProfilesEntity::class], version = 1)
abstract class MotoUnpluggedDataBase {

    abstract fun profilesDao(): ProfilesDao // cria uma função para retornar o DAO da entidade, é obrigatório retornar o DAO

}

// ksp faz a comunicação com o banco de dados e permite que o room salve as informações