package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportScreen(
    noteTitle: String = "Mi Nota",
    onBack: () -> Unit = {}
) {
    var selectedFormat by remember { mutableStateOf<String?>(null) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Exportar Nota") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título de la nota
            Text(
                text = "Nota: \"$noteTitle\"",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Divider()

            Text(
                text = "Selecciona el formato de exportación:",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón Markdown
            ExportFormatButton(
                label = "Markdown (.md)",
                description = "Ideal para documentación y GitHub",
                icon = "📝",
                color = Color(0xFF6200EE),
                isSelected = selectedFormat == "Markdown",
                onClick = {
                    selectedFormat = "Markdown"
                    showConfirmDialog = true
                }
            )

            // Botón PDF
            ExportFormatButton(
                label = "PDF (.pdf)",
                description = "Para compartir e imprimir",
                icon = "📄",
                color = Color(0xFFB00020),
                isSelected = selectedFormat == "PDF",
                onClick = {
                    selectedFormat = "PDF"
                    showConfirmDialog = true
                }
            )

            // Botón JSON
            ExportFormatButton(
                label = "Ticket de Tarea (.json)",
                description = "Formato estructurado para integración",
                icon = "🔧",
                color = Color(0xFF018786),
                isSelected = selectedFormat == "JSON",
                onClick = {
                    selectedFormat = "JSON"
                    showConfirmDialog = true
                }
            )
        }

        // Dialog de confirmación (solo visual por ahora)
        if (showConfirmDialog && selectedFormat != null) {
            AlertDialog(
                onDismissRequest = { showConfirmDialog = false },
                title = { Text("Exportar como $selectedFormat") },
                text = { Text("La exportación en $selectedFormat estará disponible próximamente.") },
                confirmButton = {
                    TextButton(onClick = { showConfirmDialog = false }) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showConfirmDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun ExportFormatButton(
    label: String,
    description: String,
    icon: String,
    color: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) color else Color.LightGray
    val containerColor = if (isSelected) color.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = androidx.compose.foundation.BorderStroke(2.dp, borderColor),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = icon, fontSize = 32.sp)

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = color
                )
                Text(
                    text = description,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}