package com.example.proyectofinal.ui.screens

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectofinal.R
import com.example.proyectofinal.utils.NotificationHelper
import com.example.proyectofinal.viewmodel.NoteViewModel
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingScreen(
    onBackClick: () -> Unit = {},
    noteViewModel: NoteViewModel = viewModel()
) {
    val context = LocalContext.current

    // Estado del dibujo
    val paths = remember { mutableStateListOf<DrawingPath>() }
    var currentPath by remember { mutableStateOf<DrawingPath?>(null) }

    // Herramientas
    var selectedColor by remember { mutableStateOf(Color.Black) }
    var selectedStrokeWidth by remember { mutableStateOf(8f) }
    var selectedTool by remember { mutableStateOf("lapiz") }

    // Estado para el título de la nota
    var title by remember { mutableStateOf("") }

    // Opciones de colores
    val colors = listOf(
        Color.Black, Color.Red, Color.Blue, Color.Green,
        Color.Yellow, Color.Magenta, Color.Cyan, Color.Gray
    )

    // Opciones de grosores
    val strokes = listOf(4f, 8f, 12f, 16f, 20f)

    // Función para guardar el dibujo como imagen
    fun saveDrawingAsImage(): String {
        val bitmap = captureDrawing(paths, size = androidx.compose.ui.geometry.Size(800f, 1200f))
        val fileName = "drawing_${System.currentTimeMillis()}.png"
        val file = File(context.filesDir, fileName)
        FileOutputStream(file).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        }
        return file.absolutePath
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dibujo Libre", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {
                        if (title.isBlank()) {
                            Toast.makeText(context, "Escribe un título para el dibujo", Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }
                        if (paths.isEmpty()) {
                            Toast.makeText(context, "Dibuja algo primero", Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }

                        val imagePath = saveDrawingAsImage()

                        noteViewModel.addNote(
                            title = title,
                            content = "Dibujo libre",
                            category = "Personal",
                            drawingUrl = imagePath      // ✅ era imageUrl, ahora drawingUrl
                        )

                        NotificationHelper.sendNoteNotification(
                            context = context,
                            title = "Dibujo Guardado",
                            content = "Se guardó '$title' exitosamente"
                        )

                        Toast.makeText(context, "Dibujo guardado", Toast.LENGTH_LONG).show()
                        onBackClick()
                    }) {
                        Icon(Icons.Filled.Save, "Guardar")
                    }
                },
                actions = {
                    // Botón limpiar todo
                    IconButton(onClick = { paths.clear() }) {
                        Icon(painterResource(R.drawable.ic_delete), "Limpiar")
                    }
                    // Botón guardar - USANDO EL MISMO MÉTODO QUE NoteScreen
                    IconButton(onClick = {
                        if (title.isBlank()) {
                            Toast.makeText(context, "Escribe un título para el dibujo", Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }

                        if (paths.isEmpty()) {
                            Toast.makeText(context, "Dibuja algo primero", Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }

                        // Guardar el dibujo como imagen
                        val imagePath = saveDrawingAsImage()

                        // Guardar la nota usando el mismo método que NoteScreen
                        noteViewModel.addNote(
                            title = title,
                            content = "Dibujo libre creado con la app",
                            category = "Personal",
                            imageUrl = imagePath
                        )

                        // Notificación
                        NotificationHelper.sendNoteNotification(
                            context = context,
                            title = "Dibujo Guardado",
                            content = "Se guardó tu dibujo '$title' exitosamente"
                        )

                        Toast.makeText(context, "Dibujo guardado como nota", Toast.LENGTH_LONG).show()
                        onBackClick()
                    }) {
                        Icon(Icons.Filled.Save, "Guardar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            // Campo para el título del dibujo
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título del dibujo *") },
                    placeholder = { Text("Ej: Mi dibujo creativo") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    singleLine = true
                )
            }

            // Barra de herramientas superior
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF212121))
                    .padding(8.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterChip(
                    selected = selectedTool == "lapiz",
                    onClick = { selectedTool = "lapiz" },
                    label = { Text("✏️ Lápiz", fontSize = 12.sp) },
                    modifier = Modifier.height(36.dp)
                )

                FilterChip(
                    selected = selectedTool == "pincel",
                    onClick = { selectedTool = "pincel" },
                    label = { Text("🖌️ Pincel", fontSize = 12.sp) },
                    modifier = Modifier.height(36.dp)
                )

                FilterChip(
                    selected = selectedTool == "borrador",
                    onClick = { selectedTool = "borrador" },
                    label = { Text("🧽 Borrador", fontSize = 12.sp) },
                    modifier = Modifier.height(36.dp)
                )

                Divider(
                    modifier = Modifier
                        .height(30.dp)
                        .width(1.dp),
                    color = Color.Gray
                )

                Text("Grosor:", color = Color.White, fontSize = 11.sp)

                strokes.forEach { stroke ->
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .background(
                                color = if (selectedStrokeWidth == stroke) Color(0xFF6200EE) else Color.Transparent,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(stroke.dp)
                                .background(Color.White, CircleShape)
                        )
                    }
                    IconButton(onClick = { selectedStrokeWidth = stroke }) {
                        Box(modifier = Modifier.fillMaxSize())
                    }
                }
            }

            // Barra de colores
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2D2D2D))
                    .padding(8.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                colors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(color, CircleShape)
                            .border(
                                width = if (selectedColor == color) 3.dp else 0.dp,
                                color = Color.White,
                                shape = CircleShape
                            )
                    ) {
                        IconButton(
                            onClick = { selectedColor = color },
                            modifier = Modifier.fillMaxSize()
                        ) {}
                    }
                }
            }

            // Área de dibujo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .pointerInput(selectedTool) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    val drawColor = when (selectedTool) {
                                        "borrador" -> Color.White
                                        else -> selectedColor
                                    }
                                    val strokeW = when (selectedTool) {
                                        "pincel" -> selectedStrokeWidth * 1.5f
                                        "borrador" -> selectedStrokeWidth * 2f
                                        else -> selectedStrokeWidth
                                    }
                                    currentPath = DrawingPath(
                                        color = drawColor,
                                        strokeWidth = strokeW,
                                        points = mutableListOf(offset)
                                    )
                                },
                                onDrag = { change, _ ->
                                    currentPath?.points?.add(change.position)
                                    change.consume()
                                },
                                onDragEnd = {
                                    currentPath?.let {
                                        if (it.points.size > 1) paths.add(it)
                                    }
                                    currentPath = null
                                }
                            )
                        }
                ) {
                    paths.forEach { path ->
                        drawDrawingPath(path)
                    }
                    currentPath?.let {
                        drawDrawingPath(it)
                    }
                }

                if (paths.isEmpty()) {
                    Text(
                        text = "🎨 Dibuja con tu dedo\nUsa lápiz, pincel o borrador",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }

            // Barra inferior de información
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF212121))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Herramienta: ${when(selectedTool) {
                        "lapiz" -> "Lápiz"
                        "pincel" -> "Pincel"
                        "borrador" -> "Borrador"
                        else -> "Lápiz"
                    }} | Grosor: ${selectedStrokeWidth.toInt()}px",
                    color = Color.White,
                    fontSize = 11.sp
                )
            }
        }
    }
}

// Función para dibujar un trazo
private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawDrawingPath(path: DrawingPath) {
    if (path.points.size < 2) return

    val composePath = androidx.compose.ui.graphics.Path()
    composePath.moveTo(path.points.first().x, path.points.first().y)

    for (i in 1 until path.points.size) {
        composePath.lineTo(path.points[i].x, path.points[i].y)
    }

    drawPath(
        path = composePath,
        color = path.color,
        style = Stroke(width = path.strokeWidth)
    )
}

// Función para capturar el dibujo como Bitmap
fun captureDrawing(paths: List<DrawingPath>, size: androidx.compose.ui.geometry.Size): Bitmap {
    val bitmap = Bitmap.createBitmap(size.width.toInt(), size.height.toInt(), Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    canvas.drawColor(android.graphics.Color.WHITE)

    val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
    }

    paths.forEach { path ->
        paint.color = path.color.toArgb()
        paint.strokeWidth = path.strokeWidth

        if (path.points.size >= 2) {
            val androidPath = Path()
            androidPath.moveTo(path.points.first().x, path.points.first().y)
            for (i in 1 until path.points.size) {
                androidPath.lineTo(path.points[i].x, path.points[i].y)
            }
            canvas.drawPath(androidPath, paint)
        }
    }

    return bitmap
}

// Data class para guardar trazos
data class DrawingPath(
    val color: Color,
    val strokeWidth: Float,
    val points: MutableList<Offset>
)

// Extensión para convertir Color de Compose a Color de Android
fun Color.toArgb(): Int {
    return android.graphics.Color.argb(
        (alpha * 255).toInt(),
        (red * 255).toInt(),
        (green * 255).toInt(),
        (blue * 255).toInt()
    )
}