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

    @GET("api/usuarios")
    suspend fun obtenerUsuarios(): Response<List<Usuario>>

    // Categorias
    @POST("api/categorias")
    suspend fun crearCategoria(@Body categoria: Categoria): Response<Categoria>

    @GET("api/categorias")
    suspend fun obtenerCategorias(): Response<List<Categoria>>

    // Transacciones
    @POST("api/transacciones")
    suspend fun crearTransaccion(@Body transaccion: Transaccion): Response<Transaccion>

    @GET("api/transacciones")
    suspend fun obtenerTransacciones(): Response<List<Transaccion>>

    @DELETE("api/transacciones/{id}")
    suspend fun eliminarTransaccionPorId(@Path("id") id: Long): Response<Unit>
}