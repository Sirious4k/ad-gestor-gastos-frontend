package com.brandon.gestorgastos.repository

import com.brandon.gestorgastos.model.LoginRequest
import com.brandon.gestorgastos.model.RegisterRequest
import com.brandon.gestorgastos.ui.RetrofitClient

class AuthRepository {
    private val apiService = RetrofitClient.apiService

    // Usuarios
    suspend fun register(registerRequest: RegisterRequest) = apiService.register(registerRequest)

    suspend fun login(loginRequest: LoginRequest) = apiService.login(loginRequest)
}