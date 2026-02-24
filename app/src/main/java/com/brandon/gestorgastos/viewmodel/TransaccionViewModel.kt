package com.brandon.gestorgastos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brandon.gestorgastos.model.Transaccion
import com.brandon.gestorgastos.repository.TransaccionRepository
import kotlinx.coroutines.launch

class TransaccionViewModel : ViewModel() {
    private val repository = TransaccionRepository()

    // LiveData para observar la lista de transacciones
    private val _transacciones = MutableLiveData<List<Transaccion>>()
    val transacciones: LiveData<List<Transaccion>> = _transacciones

    // LiveData para mensajes de éxito
    private val _isOk = MutableLiveData<String>()
    val isOk: LiveData<String> = _isOk

    // LiveData para mensajes de error
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // LiveData para estado de carga
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // Obtener todas las transacciones
    fun obtenerTransacciones() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.obtenerTransacciones()
                if (response.isSuccessful) {
                    _transacciones.value = response.body() ?: emptyList()
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

    fun crearTransaccion(transaccion: Transaccion) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.crearTransaccion(transaccion)
                if (response.isSuccessful) {
                    _isOk.value = "Transacción creada con éxito ✅"
                    obtenerTransacciones()
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