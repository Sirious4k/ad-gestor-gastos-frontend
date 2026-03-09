package com.brandon.gestorgastos.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
fun RegisterScreen (
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    val usuarioRegistrado by viewModel.usuarioRegistrado.observeAsState()
    val error by viewModel.error.observeAsState()
    val isOk by viewModel.isOk.observeAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordConfirmVisible by remember { mutableStateOf(false) }

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordConfirm by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Registro") },
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
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.padding(16.dp),
                    placeholder = { Text("Ingrese su nombre de usuario") }
                )

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

                OutlinedTextField(
                    value = passwordConfirm,
                    onValueChange = { passwordConfirm = it },
                    label = { Text("Repetir contraseña") },
                    visualTransformation = if (passwordConfirmVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordConfirmVisible = !passwordConfirmVisible }) {
                            Icon(
                                imageVector = if (passwordConfirmVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (passwordConfirmVisible) "Ocultar contraseña" else "Mostrar contraseña"
                            )
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                )

                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Todos los campos son obligatorios")
                            }
                        } else if (password != passwordConfirm) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Las contraseñas no coinciden ⚠️")
                            }
                        } else {
                            viewModel.register(nombre, email, password)
                        }
                    }
                ) {
                    Text("Registrarse")
                }

                TextButton(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        navController.navigate("login")
                    }
                ) {
                    Text("¿Ya tienes cuenta? Inicia sesión")
                }
            }
            LaunchedEffect(isOk) {
                isOk?.let {
                    snackbarHostState.showSnackbar(it)
                }
            }
            LaunchedEffect(usuarioRegistrado) {
                usuarioRegistrado?.let {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            }
            LaunchedEffect(error) {
                error?.let {
                    snackbarHostState.showSnackbar(it)
                }
            }
        }
    }
}