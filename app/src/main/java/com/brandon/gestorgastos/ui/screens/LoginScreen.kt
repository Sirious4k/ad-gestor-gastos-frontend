package com.brandon.gestorgastos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.brandon.gestorgastos.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen (
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    val usuarioLogueado by viewModel.usuarioLogueado.observeAsState()
    val error by viewModel.error.observeAsState()
    val isOk by viewModel.isOk.observeAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var passwordVisible by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Iniciar Sesión") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo") },
                    modifier = Modifier.padding(16.dp),
                    placeholder = { Text("Ingrese su correo") }
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = if (passwordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                            )
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                )

                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        if (email.isEmpty() || password.isEmpty()) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Todos los campos son obligatorios")
                            }
                        } else {
                            viewModel.login(email, password)
                            scope.launch {
                                snackbarHostState.showSnackbar(viewModel.isOk.toString())
                            }
                        }
                    }
                ) {
                    Text("Iniciar sesión")
                }

                TextButton(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        navController.navigate("registro")
                    }
                ) {
                    Text("¿No tienes cuenta? Regístrate")
                }
            }
            LaunchedEffect(usuarioLogueado) {
                usuarioLogueado?.let {
                    navController.navigate("transacciones")
                }
            }
        }
    }
}