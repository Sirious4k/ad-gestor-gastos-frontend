package com.brandon.gestorgastos.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brandon.gestorgastos.model.TipoTransaccion
import com.brandon.gestorgastos.model.Transaccion
import com.brandon.gestorgastos.model.Usuario
import com.brandon.gestorgastos.viewmodel.TransaccionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearTransaccionScreen (
    viewModel: TransaccionViewModel = viewModel(),
    onClick: () -> Unit
    // usuario: Usuario ---- para mas adelante
) {
    // Observar los estados del ViewModel
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isOk by viewModel.isOk.observeAsState()
    val error by viewModel.error.observeAsState()

    var monto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var usuario: Usuario
    // var nombreUsuario = usuario.nombre ---- usar mas adelante
    var fecha by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val options = listOf(TipoTransaccion.GASTO, TipoTransaccion.INGRESO)
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    val transaccion: Transaccion


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Agregar nueva Transacción") },
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
                else -> {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        OutlinedTextField(
                            value = usuario,
                            onValueChange = { usuario = it },
                            label = { Text("Usuario") },
                            modifier = Modifier.padding(16.dp),
                            singleLine = true,
                            placeholder = { Text("Ingrese su usuario") }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            TextField(
                                value = selectedOptionText,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Seleccionar el tipo de transacción") },
                                trailingIcon = { expanded },
                                modifier = Modifier.menuAnchor()
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                options.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        text = { Text(selectionOption) },
                                        onClick = {
                                            selectedOptionText = selectionOption
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = monto,
                            onValueChange = { monto = it },
                            label = { Text("Monto") },
                            modifier = Modifier.padding(16.dp),
                            singleLine = true,
                            placeholder = { Text("Ingrese el monto") }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = fecha,
                            onValueChange = { fecha = it },
                            label = { Text("Fecha") },
                            modifier = Modifier.padding(16.dp),
                            singleLine = true,
                            placeholder = { Text("Ingrese la fecha") }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = categoria,
                            onValueChange = { categoria = it }, // crear categoria mas adelante
                            label = { Text("Categoría") },
                            modifier = Modifier.padding(16.dp),
                            singleLine = true,
                            placeholder = { Text("Ingrese la categoría") }
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = descripcion,
                            onValueChange = { descripcion = it }, // crear categoria mas adelante
                            label = { Text("Descripción") },
                            modifier = Modifier.padding(16.dp),
                            singleLine = true,
                            placeholder = { Text("Descripción (opcional)") }
                        )

                        val usuarioFinal = usuario
                        val tipoFinal = selectedOptionText
                        val montoFinal = monto
                        val fechaFinal = fecha
                        val categoriaFinal = categoria
                        val descripcionFinal = descripcion

                        transaccion.usuario = usuarioFinal
                        transaccion.tipo = tipoFinal
                        transaccion.monto = montoFinal
                        transaccion.fecha = fechaFinal
                        transaccion.categoria = categoriaFinal
                        transaccion.descripcion = descripcionFinal

                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = {
                                val usuarioObj = Usuario(id = 1, nombre = usuario, email = "", password = "")
                                // Aquí CREAS la transacción y la envías
                                viewModel.crearTransaccion(transaccion) // ❌ Ejecuta INMEDIATAMENTE
                            }
                        ) {
                            Text("Crear transacción")
                        }
                    }
                }
            }
        }
    }
}