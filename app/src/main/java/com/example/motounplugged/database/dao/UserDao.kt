package com.example.motounplugged.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.motounplugged.database.entities.UserEntity

@Dao
interface UserDao{

    // garante se houver conflito haja uma sobrescrição (utilizar outro metodo de conflito)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerUser(user: UserEntity)

    //consulta por email apenas se não estiver vazia
    //@Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    //suspend fun buscarPorEmail(email: String): UserEntity?

    //consulta de dados por id de usuário
    @Query("SELECT * FROM usuarios WHERE id = :id")
    suspend fun getUser(id: Int): UserEntity?

    //faz o update do usuario
    @Update
    suspend fun updateUser(user: UserEntity)
}