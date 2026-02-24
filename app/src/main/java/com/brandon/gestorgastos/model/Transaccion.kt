package com.brandon.gestorgastos.model

data class Transaccion (
    val id: Long? = null,
    val usuario: Usuario,
    val tipo: TipoTransaccion,
    val monto: Int,
    val fecha: String,
    val categoria: Categoria? = null,
    val descripcion: String? = null
)