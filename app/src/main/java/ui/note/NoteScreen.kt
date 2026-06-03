package com.example.proyectofinal.ui.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectofinal.data.model.NoteModel
import com.example.proyectofinal.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    noteId: String? = null,
    onBack: () -> Unit,
    viewModel: NoteViewModel = viewModel()
) {
    val selectedNote by viewModel.selectedNote.observeAsState()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }

    // Si hay una nota seleccionada, cargar sus datos
    LaunchedEffect(selectedNote) {
        selectedNote?.let {
            if (it.id.isNotBlank()) {
                title = it.title
                content = it.content
                category = it.category
                isEditing = true
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isEditing) "Editar Nota" else "Nueva Nota",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (title.isNotBlank()) {
                            if (isEditing && selectedNote != null) {
                                viewModel.updateNote(
                                    selectedNote!!.copy(
                                        title = title,
                                        content = content,
                                        category = category
                                    )
                                )
                            } else {
                                viewModel.addNote(
                                    title = title,
                                    content = content,
                                    category = category
                                )
                            }
                            onBack()
                        }
                    }) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Guardar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF5F5F5))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Campo título
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // Campo categoría
            // Campo categoría
            var expanded by remember { mutableStateOf(false) }
            val categorias = listOf("Personal", "Universidad", "Trabajo", "Ideas", "Otro")

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categorias.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                category = opcion
                                expanded = false
                            }
                        )
                    }
                }
            }

            // Campo contenido
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Contenido") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                shape = RoundedCornerShape(12.dp),
                maxLines = 20
            )

            // Texto de ayuda
            if (title.isBlank()) {
                Text(
                    text = "El título es obligatorio",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
    }
}