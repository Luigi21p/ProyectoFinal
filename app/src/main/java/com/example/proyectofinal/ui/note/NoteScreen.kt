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
import androidx.compose.ui.platform.LocalContext // 💡 Importación necesaria
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectofinal.R
import com.example.proyectofinal.viewmodel.NoteViewModel
import com.example.proyectofinal.utils.NotificationHelper // 💡 Importación necesaria

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    noteId: String? = null,
    onBack: () -> Unit,
    viewModel: NoteViewModel = viewModel()
) {
    val context = LocalContext.current // 💡 Capturamos el contexto para la notificación
    val selectedNote by viewModel.selectedNote.observeAsState()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }

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

    val categoriasMap = listOf(
        stringResource(R.string.tab_personal)   to "Personal",
        stringResource(R.string.tab_university) to "Universidad",
        stringResource(R.string.tab_work)       to "Trabajo",
        stringResource(R.string.tab_ideas)      to "Ideas",
        stringResource(R.string.tab_other)      to "Otro"
    )

    val categoryLabel = categoriasMap
        .firstOrNull { it.second == category }?.first ?: category

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isEditing) stringResource(R.string.note_edit)
                        else           stringResource(R.string.note_new),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = null,
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
                                // 💡 Notificación al actualizar nota
                                NotificationHelper.sendNoteNotification(
                                    context = context,
                                    title = "Nota Actualizada",
                                    content = "Se guardaron los cambios en '$title'"
                                )
                            } else {
                                viewModel.addNote(
                                    title = title,
                                    content = content,
                                    category = category
                                )
                                // 💡 Notificación al crear nota nueva
                                NotificationHelper.sendNoteNotification(
                                    context = context,
                                    title = "Nota Creada",
                                    content = "Se añadió la nota '$title' exitosamente"
                                )
                            }
                            onBack()
                        }
                    }) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
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
                label = { Text(stringResource(R.string.note_title)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            // Campo categoría
            var expanded by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = categoryLabel,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.note_category)) },
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
                    categoriasMap.forEach { (label, valor) ->
                        DropdownMenuItem(
                            text = { Text(label) },
                            onClick = {
                                category = valor
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
                label = { Text(stringResource(R.string.note_content)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                shape = RoundedCornerShape(12.dp),
                maxLines = 20
            )

            if (title.isBlank()) {
                Text(
                    text = stringResource(R.string.note_title_required),
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
    }
}