package com.example.proyectofinal.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.proyectofinal.R



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoNoteScreen(onBackClick: () -> Unit = {}) {
    // Estados para los inputs de texto
    var title by remember { mutableStateOf("") }
    var noteContent by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva nota con imagen") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back), // Usamos el vector que ya tienes en drawable
                            contentDescription = "Atrás"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE), // El morado
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        // Contenedor con Scroll (Reemplaza al ScrollView + LinearLayout vertical)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Campo de Título
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                placeholder = { Text("Escribe el título...") },
                modifier = Modifier.fillMaxWidth()
            )

            // Cuadro de la Imagen (Sección central)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFFF5F5F5)), // Gris claro
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_image), // Asegúrate de usar el id correcto
                        contentDescription = "Sin imagen seleccionada",
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Sin imagen seleccionada", color = Color.Gray)
                }
            }

            // Botones de acción de la imagen (Fila Horizontal)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { /* Acción Tomar Foto */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Tomar foto")
                }
                Button(
                    onClick = { /* Acción Subir Imagen */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Subir Imagen")
                }
            }

            // Campo de la Nota
            OutlinedTextField(
                value = noteContent,
                onValueChange = { noteContent = it },
                label = { Text("Nota") },
                placeholder = { Text("Escribe tu nota aquí...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            // Espacio inferior para el Spinner/Categorías que tiene abajo
            Text("Categoría", style = MaterialTheme.typography.labelLarge)

            // Aquí se puede agregar un ExposedDropdownMenuBox más adelante para el Spinner.
        }
    }
}