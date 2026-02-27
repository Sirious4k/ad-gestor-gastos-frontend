package com.brandon.gestorgastos.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.brandon.gestorgastos.model.Categoria
import com.brandon.gestorgastos.model.TipoTransaccion
import com.brandon.gestorgastos.model.Transaccion
import com.brandon.gestorgastos.model.Usuario
import com.brandon.gestorgastos.utils.formatearMonto
import com.brandon.gestorgastos.viewmodel.TransaccionViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearTransaccionScreen (
    viewModel: TransaccionViewModel = viewModel(),
    navController: NavController
    // usuario: Usuario ---- para mas adelante
) {
    // Observar los estados del ViewModel
    val isLoading by viewModel.isLoading.observeAsState(false)
    val isOk by viewModel.isOk.observeAsState()
    val error by viewModel.error.observeAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var monto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var usuario by remember { mutableStateOf("") }
    // var nombreUsuario = usuario.nombre ---- usar mas adelante
    var fecha by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var categoria by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val options = listOf(TipoTransaccion.GASTO, TipoTransaccion.INGRESO)
    var selectedOptionText by remember { mutableStateOf(options[0]) }


    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                            modifier = Modifier.padding(16.dp)
                        ) {
                            TextField(
                                value = selectedOptionText.toString(),
                                onValueChange = { },
                                readOnly = true,
                                label = { Text("Seleccionar el tipo de transacción") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier.menuAnchor()
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false }
                            ) {
                                options.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        text = { Text(selectionOption.toString()) },
                                        onClick = {
                                            selectedOptionText = selectionOption
                                            expanded = false
                                        }
                                    )
                                }
                            }
                        }

                        OutlinedTextField(
                            value = monto,
                            onValueChange = { monto = it },
                            label = { Text("Monto") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.padding(16.dp),
                            singleLine = true,
                            placeholder = { Text("Ingrese el monto") }
                        )

                        OutlinedTextField(
                            value = fecha,
                            onValueChange = { },
                            label = { Text("Fecha") },
                            readOnly = true,
                            trailingIcon = {
                                IconButton(onClick = { showDialog = true }) {
                                    Icon(Icons.Default.DateRange, contentDescription = "Seleccionar fecha")
                                }
                            },
                            modifier = Modifier.padding(16.dp)
                        )

                        if (showDialog) {
                            DatePickerDialog(
                                onDismissRequest = { showDialog = false },
                                confirmButton = {
                                    TextButton(onClick = {
                                        val selectedDate = datePickerState.selectedDateMillis
                                        if (selectedDate != null) {
                                            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                            fecha = formatter.format(Date(selectedDate))
                                        }
                                        showDialog = false
                                    }) { Text("Aceptar") }
                                }
                            ) {
                                DatePicker(state = datePickerState)
                            }
                        }

                        OutlinedTextField(
                            value = categoria,
                            onValueChange = { categoria = it }, // crear categoria mas adelante
                            label = { Text("Categoría") },
                            modifier = Modifier.padding(16.dp),
                            singleLine = true,
                            placeholder = { Text("Ingrese la categoría") }
                        )

                        OutlinedTextField(
                            value = descripcion,
                            onValueChange = { descripcion = it },
                            label = { Text("Descripción") },
                            modifier = Modifier.padding(16.dp),
                            singleLine = true,
                            placeholder = { Text("Descripción (opcional)") }
                        )

                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Blue,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.padding(16.dp),
                                onClick = {
                                    val usuarioObj = Usuario(id = 1, nombre = usuario, email = "", password = "")
                                    val tipoFinal = selectedOptionText
                                    val montoFinal = monto
                                    val fechaFinal = fecha
                                    val categoriaObj = Categoria(1, "Vicio")
                                    val descripcionFinal = descripcion

                                    // VALIDACIONES
                                    if (montoFinal.toIntOrNull() == null) {
                                        scope.launch {
                                            snackbarHostState.showSnackbar("El monto es obligatorio")
                                        }
                                    } else {
                                        val transaccion = Transaccion(
                                            null,
                                            usuarioObj,
                                            tipoFinal,
                                            montoFinal.toInt(),
                                            fechaFinal,
                                            categoriaObj,
                                            descripcionFinal)

                                        viewModel.validacionTransaccion(transaccion)
                                        val validacion = viewModel.validacionTransaccion(transaccion)
                                        if (validacion != null) {
                                            scope.launch {
                                                snackbarHostState.showSnackbar(validacion)
                                            }
                                        } else {
                                            viewModel.crearTransaccion(transaccion)
                                        }
                                    }
                                }
                            ) {
                                Text("Crear transacción")
                            }

                            Button(
                                modifier = Modifier.padding(16.dp),
                                onClick = { navController.navigate("transacciones") }
                            ) {
                                Text("Ir a mis transacciones")
                            }
                        }
                    }
                }
            }
            LaunchedEffect(isOk) {
                isOk?.let {
                    snackbarHostState.showSnackbar(it)
                    navController.navigate("transacciones")
                }
            }
        }
    }
}