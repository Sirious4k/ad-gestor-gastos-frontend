package com.brandon.gestorgastos.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.brandon.gestorgastos.model.TipoTransaccion
import com.brandon.gestorgastos.model.Transaccion
import com.brandon.gestorgastos.ui.theme.GastoRojo
import com.brandon.gestorgastos.ui.theme.IngresoVerde

@Composable
fun TransaccionCard (
    transaccion: Transaccion,
    modifier: Modifier = Modifier
) {
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
    }
}