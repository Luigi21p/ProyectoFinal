package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.background
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
import androidx.navigation.NavController
import com.example.proyectofinal.navigation.AppScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedactarNotaScreen(navController: NavController) { // ESTA ES LA FUNCIÓN QUE TE FALTABA
    var titulo by remember { mutableStateOf("") }
    var contenido by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf("Trabajo") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Nueva Nota", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF6200EE))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Campo de Título
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Selector de Categoría (Usando tu función de abajo)
            Text("Selecciona una categoría:", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            SelectorCategoria { categoria ->
                categoriaSeleccionada = categoria
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Contenido
            OutlinedTextField(
                value = contenido,
                onValueChange = { contenido = it },
                label = { Text("Contenido de la nota") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // Para que ocupe el espacio sobrante
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de Exportar
            Button(
                onClick = {
                    navController.navigate(AppScreens.ExportScreen.route)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF018786))
            ) {
                Text("Exportar Nota", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Botón de Guardar
            Button(
                onClick = {
                    // Aquí iría la lógica para guardar
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text("Guardar Nota", color = Color.White)
            }
        }
    }
}

@Composable
fun SelectorCategoria(alSeleccionar: (String) -> Unit) {
    val categorias = listOf("Trabajo", "Personal", "Ideas", "Compras")
    var expandido by remember { mutableStateOf(false) }
    var seleccionado by remember { mutableStateOf(categorias[0]) }

    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        OutlinedButton(
            onClick = { expandido = true },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Categoría: $seleccionado")
        }

        DropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            categorias.forEach { nombre ->
                DropdownMenuItem(
                    text = { Text(nombre) },
                    onClick = {
                        seleccionado = nombre
                        expandido = false
                        alSeleccionar(nombre)
                    }
                )
            }
        }
    }
}