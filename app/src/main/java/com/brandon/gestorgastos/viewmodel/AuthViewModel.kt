package com.brandon.gestorgastos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.gestorgastos.model.LoginRequest
import com.brandon.gestorgastos.model.RegisterRequest
import com.brandon.gestorgastos.model.Usuario
import com.brandon.gestorgastos.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    private val repository = AuthRepository()

    private val _usuarioLogueado = MutableLiveData<Usuario?>()
    val usuarioLogueado: LiveData<Usuario?> = _usuarioLogueado

    private val _usuarioRegistrado = MutableLiveData<Usuario?>()
    val usuarioRegistrado: LiveData<Usuario?> = _usuarioRegistrado

    private val _isOk = MutableLiveData<String>()
    val isOk: LiveData<String> = _isOk

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val usuario = LoginRequest(email, password)
                val response = repository.login(usuario)
                if (response.isSuccessful) {
                    _usuarioLogueado.value = response.body()
                    _isOk.value = "Inicio de sesión exitoso ✅"
                } else if (response.code() == 401) {
                    _error.value = "Credenciales inválidas"
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun register(nombre: String, email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val nuevoUsuario = RegisterRequest(nombre, email, password)
                val response = repository.register(nuevoUsuario)
                if (response.isSuccessful) {
                    _isOk.value = "Cuenta creada exitosamente ✅. Inicia sesión para continuar"
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}