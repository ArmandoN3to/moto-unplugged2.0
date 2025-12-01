/*package com.example.motounplugged.ui.features.login

import androidx.lifecycle.ViewModel
import com.example.motounplugged.repositories.UserRepository

class LoginScreenViewModel(
    private val repository: UserRepository
):ViewModel() {

    suspend fun login(email:String,password: String):Boolean{
        val user = repository.getUser(email)
        return user != null && user.password == password
    }

}*/