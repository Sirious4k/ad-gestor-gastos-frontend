package com.brandon.gestorgastos.api

import com.brandon.gestorgastos.model.Categoria
import com.brandon.gestorgastos.model.Transaccion
import com.brandon.gestorgastos.model.Usuario
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
     // Usuarios
    @POST("api/usuarios")
    suspend fun crearUsuario(@Body usuario: Usuario): Response<Usuario>

    @GET
    suspend fun obtenerUsuarios(): Response<List<Usuario>>

    // Categorias
    @POST("api/categorias")
    suspend fun crearCategoria(@Body categoria: Categoria): Response<Categoria>

    @GET
    suspend fun obtenerCategorias(): Response<List<Categoria>>

    // Transacciones
    @POST("api/transacciones")
    suspend fun crearTransaccion(@Body transaccion: Transaccion): Response<Transaccion>

    @GET
    suspend fun obtenerTransacciones(): Response<List<Transaccion>>
}