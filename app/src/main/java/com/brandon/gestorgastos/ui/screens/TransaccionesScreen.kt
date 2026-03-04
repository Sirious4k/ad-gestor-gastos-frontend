package com.brandon.gestorgastos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.brandon.gestorgastos.ui.components.TransaccionCard
import com.brandon.gestorgastos.viewmodel.TransaccionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransaccionesScreen (
    viewModel: TransaccionViewModel = viewModel(),
    navController: NavController
) {
    // Observar los estados del ViewModel
    val transacciones by viewModel.transacciones.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState()
    val isOk by viewModel.isOk.observeAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Cargar transacciones al iniciar
    LaunchedEffect(Unit) {
        viewModel.obtenerTransacciones()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Mis Transacciones") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("crearTransaccion") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                error != null -> {
                    Text(
                        text = error ?: "Error desconocido",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                transacciones.isEmpty() -> {
                    Text(
                        text = "No hay transacciones",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(transacciones) { transaccion ->
                            TransaccionCard(
                                transaccion = transaccion,
                                snackbarHostState = snackbarHostState
                            )
                        }
                    }
                }
            }
        }
    }
    LaunchedEffect(isOk) {
        isOk?.let {
            snackbarHostState.showSnackbar(it)
        }
    }
}