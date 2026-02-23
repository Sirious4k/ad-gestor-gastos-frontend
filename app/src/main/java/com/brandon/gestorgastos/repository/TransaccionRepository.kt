package com.brandon.gestorgastos.repository

import com.brandon.gestorgastos.model.Categoria
import com.brandon.gestorgastos.model.Transaccion
import com.brandon.gestorgastos.model.Usuario
import com.brandon.gestorgastos.ui.RetrofitClient


class TransaccionRepository {
    private val apiService = RetrofitClient.apiService

    // Usuarios
    suspend fun crearUsuario(usuario: Usuario) = apiService.crearUsuario(usuario)

    suspend fun obtenerUsuarios() = apiService.obtenerUsuarios()

    // Categorias
    suspend fun crearCategoria(categoria: Categoria) = apiService.crearCategoria(categoria)

    suspend fun obtenerCategorias() = apiService.obtenerCategorias()

    // Transacciones
    suspend fun crearTransaccion(transaccion: Transaccion) = apiService.crearTransaccion(transaccion)

    suspend fun obtenerTransacciones() = apiService.obtenerTransacciones()
}