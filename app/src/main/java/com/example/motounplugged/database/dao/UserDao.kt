package com.example.motounplugged.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.motounplugged.database.entities.UserEntity

@Dao
interface UserDao{

    // garante se houver conflito haja uma sobrescrição (utilizar outro metodo de conflito)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun registerUser(user: UserEntity)

    //consulta por email apenas se não estiver vazia
    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun buscarPorEmail(email: String): UserEntity?

}