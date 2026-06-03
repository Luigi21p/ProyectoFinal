package com.example.proyectofinal.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectofinal.R
import com.example.proyectofinal.viewmodel.ImageViewModel
import com.example.proyectofinal.viewmodel.NoteViewModel
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoNoteScreen(
    onBackClick: () -> Unit = {},
    onLaunchCamera: () -> Unit = {},
    onLaunchGallery: () -> Unit = {},
    onAnnotateClick: () -> Unit = {},
    noteViewModel: NoteViewModel = viewModel()
) {
    var title by remember { mutableStateOf("") }
    var noteContent by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Personal") }

    val imageViewModel: ImageViewModel = viewModel()
    val selectedImage by imageViewModel.selectedImage.observeAsState()
    val context = LocalContext.current

    // Función para guardar la imagen en almacenamiento
    fun saveImageToStorage(bitmap: Bitmap): String {
        val fileName = "note_image_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)
        FileOutputStream(file).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)
        }
        return file.absolutePath
    }

    // Función para guardar la nota usando el método correcto del ViewModel
    fun saveNote() {
        if (title.isBlank()) {
            android.widget.Toast.makeText(context, "El título es obligatorio", android.widget.Toast.LENGTH_SHORT).show()
            return
        }
        if (selectedImage == null) {
            android.widget.Toast.makeText(context, "Selecciona una imagen primero", android.widget.Toast.LENGTH_SHORT).show()
            return
        }

        val imagePath = saveImageToStorage(selectedImage!!)

        // ✅ Usando el método que existe en NoteViewModel
        noteViewModel.addNote(
            title = title,
            content = noteContent,
            category = category,
            imageUrl = imagePath
        )

        android.widget.Toast.makeText(context, "Nota guardada con imagen", android.widget.Toast.LENGTH_SHORT).show()
        onBackClick()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva nota con imagen") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(painterResource(R.drawable.ic_arrow_back), "Atrás")
                    }
                },
                actions = {
                    Button(
                        onClick = { saveNote() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                    ) {
                        Text("Guardar", color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título *") },
                modifier = Modifier.fillMaxWidth()
            )

            // Selector de categoría
            Text("Categoría", style = MaterialTheme.typography.labelLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Personal", "Universidad", "Trabajo", "Ideas").forEach { cat ->
                    FilterChip(
                        selected = category == cat,
                        onClick = { category = cat },
                        label = { Text(cat) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Vista previa de la imagen
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                if (selectedImage != null) {
                    Image(
                        bitmap = selectedImage!!.asImageBitmap(),
                        contentDescription = "Imagen seleccionada",
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painterResource(R.drawable.ic_image),
                            contentDescription = "Sin imagen",
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )
                        Text("Sin imagen seleccionada", color = Color.Gray)
                    }
                }
            }

<<<<<<< HEAD
            // Botones cámara y galería
=======
>>>>>>> origin/master
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onLaunchCamera,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Text("📷 Tomar foto")
                }
                Button(
                    onClick = onLaunchGallery,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Text("🖼️ Subir imagen")
                }
            }

<<<<<<< HEAD
            // Contenido de la nota
=======

>>>>>>> origin/master
            OutlinedTextField(
                value = noteContent,
                onValueChange = { noteContent = it },
                label = { Text("Nota") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

<<<<<<< HEAD
            // Botón anotar
            Button(
                onClick = onAnnotateClick,
                modifier = Modifier.fillMaxWidth(),
                enabled = selectedImage != null,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text("✏️ ANOTAR IMAGEN")
            }
=======

            Text("Categoría", style = MaterialTheme.typography.labelLarge)


>>>>>>> origin/master
        }
    }
}