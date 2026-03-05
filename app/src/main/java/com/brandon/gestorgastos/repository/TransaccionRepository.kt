package com.brandon.gestorgastos.repository

import com.brandon.gestorgastos.model.Categoria
import com.brandon.gestorgastos.model.Transaccion
import com.brandon.gestorgastos.model.Usuario
import com.brandon.gestorgastos.ui.RetrofitClient


class TransaccionRepository {
    private val apiService = RetrofitClient.apiService

    // Categorias
    suspend fun crearCategoria(categoria: Categoria) = apiService.crearCategoria(categoria)

    suspend fun obtenerCategorias() = apiService.obtenerCategorias()

    // Transacciones
    suspend fun crearTransaccion(transaccion: Transaccion) = apiService.crearTransaccion(transaccion)

    suspend fun obtenerTransacciones(usuarioId: Long) = apiService.obtenerTransacciones(usuarioId)

    suspend fun eliminarTransaccionPorId(id: Long) = apiService.eliminarTransaccionPorId(id)
}