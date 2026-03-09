package com.brandon.gestorgastos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.brandon.gestorgastos.ui.screens.CrearTransaccionScreen
import com.brandon.gestorgastos.ui.screens.LoginScreen
import com.brandon.gestorgastos.ui.screens.PerfilScreen
import com.brandon.gestorgastos.ui.screens.RegisterScreen
import com.brandon.gestorgastos.ui.screens.TransaccionesScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("transacciones/{usuarioId}") { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getString("usuarioId")?.toLongOrNull()

            if (usuarioId != null) {
                TransaccionesScreen(
                    navController = navController,
                    usuarioId = usuarioId
                )
            }
        }
        composable("crearTransaccion/{usuarioId}") { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getString("usuarioId")?.toLongOrNull()

            if (usuarioId != null) {
                CrearTransaccionScreen(
                    navController = navController,
                    usuarioId = usuarioId
                )
            }

        }
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("register") {
            RegisterScreen(navController = navController)
        }
        composable("perfil/{usuarioId}") { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getString("usuarioId")?.toLongOrNull()

            if (usuarioId != null) {
                PerfilScreen(
                    navController = navController,
                    usuarioId = usuarioId
                )
            }
        }
    }
}