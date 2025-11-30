package com.example.motounplugged.repositories

import com.example.motounplugged.database.dao.UserDao
import com.example.motounplugged.database.entities.UserEntity

class UserRepository(
    private val userDao: UserDao
) {
    suspend fun registerUser(firstName: String, lastName: String, email: String, password: String){
        val user = UserEntity(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password
        )
        //insere os dados por parametro
        userDao.registerUser(user)
    }
    //busca o email por parametro
    suspend fun getUser(email: String) = userDao.buscarPorEmail(email)

    //busca o id do usuário
    suspend fun getUser(id: Int) = userDao.getUser(id)

    //faz o update do usuario
    suspend fun updateUser(user: UserEntity) = userDao.updateUser(user)
}