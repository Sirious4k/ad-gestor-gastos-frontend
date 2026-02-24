package com.brandon.gestorgastos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.brandon.gestorgastos.ui.screens.CrearTransaccionScreen
import com.brandon.gestorgastos.ui.screens.TransaccionesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "crear transacciones"
    ) {
        composable("transacciones") {
            TransaccionesScreen()
        }
        composable("crear transacciones") {
            CrearTransaccionScreen()
        }
    }
}