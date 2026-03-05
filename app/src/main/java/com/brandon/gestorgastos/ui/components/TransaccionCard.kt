package com.brandon.gestorgastos.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.brandon.gestorgastos.model.TipoTransaccion
import com.brandon.gestorgastos.model.Transaccion
import com.brandon.gestorgastos.ui.theme.GastoRojo
import com.brandon.gestorgastos.ui.theme.IngresoVerde
import com.brandon.gestorgastos.viewmodel.TransaccionViewModel

@Composable
fun TransaccionCard (
    transaccion: Transaccion,
    snackbarHostState: SnackbarHostState,
    viewModel: TransaccionViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var showEliminarTransaccion by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Descripción
            Text(
                text = transaccion.descripcion ?: "Sin descripción",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Monto
            Text(
                text = "$${transaccion.monto}",
                style = MaterialTheme.typography.titleLarge,
                color = if (transaccion.tipo == TipoTransaccion.GASTO) GastoRojo else IngresoVerde,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Categoría (si existe)
            transaccion.categoria?.let {
                Text(
                    text = it.nombre,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Fecha
            Text(
                text = transaccion.fecha,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Box {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Más opciones")
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Eliminar") },
                    onClick = {
                        expanded = false
                        showEliminarTransaccion = true
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = Color.Red
                    )
                )

                DropdownMenuItem(
                    text = { Text("Editar") },
                    onClick = {
                        expanded = false
                        // AGREGAR FUNCION DE EDITAR
                    }
                )
            }

            if (showEliminarTransaccion) {
                AlertDialog(
                    onDismissRequest = { showEliminarTransaccion = false },
                    title = { Text("Menú de confirmación") },
                    text = { Text("¿Estás seguro que desea eliminar esta transacción?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                transaccion.id?.let { id ->
                                    viewModel.eliminarTransaccionPorId(id, usuarioId) // SOLUCIONAR AL TERMINAR LOGIN
                                }
                                showEliminarTransaccion = false
                            }
                        ) {
                            Text("Confirmar")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showEliminarTransaccion = false }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}