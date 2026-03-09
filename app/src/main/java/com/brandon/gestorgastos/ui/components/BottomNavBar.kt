package com.brandon.gestorgastos.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomNavBar (
    navController: NavController,
    usuarioId: Long,
    currentRoute: String
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentRoute == "transacciones",
            onClick = {
                navController.navigate("transacciones/$usuarioId") {
                    popUpTo("transacciones/$usuarioId") { inclusive = true }
                }
            },
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") }
        )

        NavigationBarItem(
            selected = currentRoute == "crearTransaccion",
            onClick = { navController.navigate("crearTransaccion/$usuarioId") },
            icon = { Icon(Icons.Default.Add, contentDescription = "Agregar") },
            label = { Text("Agregar") }
        )

        NavigationBarItem(
            selected = currentRoute == "perfil",
            onClick = { navController.navigate("perfil/$usuarioId") },
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") }
        )
    }
}