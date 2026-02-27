package com.brandon.gestorgastos.utils

fun formatearMonto(valor: String): String {
    if (valor.isEmpty()) return ""
    val reversed = valor.reversed()
    val conPuntos = reversed.chunked(3).joinToString(".")
    return "$ ${conPuntos.reversed()}"
}