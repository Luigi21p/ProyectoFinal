package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectofinal.R
import com.example.proyectofinal.viewmodel.ImageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoAnnotationScreen(
    onLaunchCamera: () -> Unit = {},
    onLaunchGallery: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onConfirmClick: () -> Unit = {}
) {
<<<<<<< HEAD
    val imageViewModel: ImageViewModel = viewModel()
    val originalImage by imageViewModel.selectedImage.observeAsState()
    val paths = remember { mutableStateListOf<DrawPath>() }
    var currentPath by remember { mutableStateOf<DrawPath?>(null) }
=======

    var selectedTool by remember { mutableStateOf("Dibujar") }

>>>>>>> origin/master
    var selectedColor by remember { mutableStateOf(Color.Red) }

    val colors = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow, Color.Black)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Anotar imagen", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(painterResource(R.drawable.ic_arrow_back), "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = { paths.clear() }) {
                        Icon(painterResource(R.drawable.ic_delete), "Borrar")
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
                .background(Color.Black)
        ) {
<<<<<<< HEAD
            // Barra de colores
=======

>>>>>>> origin/master
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF212121))
                    .padding(8.dp)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
<<<<<<< HEAD
                colors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(color, CircleShape)
                            .border(                              // ✅ import ya agregado arriba
                                width = if (selectedColor == color) 3.dp else 0.dp,
                                color = Color.White,
                                shape = CircleShape
=======

                ToolButton(label = "Texto", isSelected = selectedTool == "Texto") { selectedTool = "Texto" }

                ToolButton(label = "Dibujar", isSelected = selectedTool == "Dibujar") { selectedTool = "Dibujar" }

                ToolButton(label = "Efecto", isSelected = selectedTool == "Efecto") { selectedTool = "Efecto" }

                ToolButton(label = "Resaltar", isSelected = selectedTool == "Resaltar") { selectedTool = "Resaltar" }

                VerticalDivider(modifier = Modifier.height(30.dp), color = Color.Gray)

                colorsList.forEach { color ->
                    IconButton(
                        onClick = { selectedColor = color },
                        modifier = Modifier
                            .size(24.dp)
                            .background(color, shape = CircleShape)
                            .padding(2.dp)
                    ) {
                        if (selectedColor == color) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Gray.copy(alpha = 0.6f), shape = CircleShape)
>>>>>>> origin/master
                            )
                    ) {
                        IconButton(
                            onClick = { selectedColor = color },
                            modifier = Modifier.fillMaxSize()
                        ) {}
                    }
                }
            }

<<<<<<< HEAD
            // Área de dibujo
            Box(modifier = Modifier.weight(1f)) {
=======
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {

>>>>>>> origin/master
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.DarkGray)
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    currentPath = DrawPath(selectedColor, mutableListOf(offset))
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
<<<<<<< HEAD
                    // drawImage con dstOffset e dstSize correctos
                    originalImage?.let { bitmap ->
                        drawImage(
                            image = bitmap.asImageBitmap(),
                            dstOffset = IntOffset.Zero,
                            dstSize = IntSize(size.width.toInt(), size.height.toInt())
                        )
                    }
=======

                }
>>>>>>> origin/master

                    paths.forEach { path ->
                        drawPathWithPoints(path.points, path.color)
                    }

                    currentPath?.let {
                        drawPathWithPoints(it.points, it.color)
                    }
                }
            }

<<<<<<< HEAD
            // Botones
=======
>>>>>>> origin/master
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(onClick = onBackClick, modifier = Modifier.weight(1f)) {
                    Text("CANCELAR")
                }
                Button(
                    onClick = onConfirmClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Text("GUARDAR")
                }
            }
        }
    }
}

<<<<<<< HEAD
private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawPathWithPoints(
    points: List<Offset>,
    color: Color
) {
    if (points.size < 2) return
    val path = Path()
    path.moveTo(points.first().x, points.first().y)
    for (i in 1 until points.size) {
        path.lineTo(points[i].x, points[i].y)
=======

@Composable
fun ToolButton(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF6200EE) else Color.DarkGray
        ),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
        modifier = Modifier.height(36.dp)
    ) {
        Text(text = label, fontSize = 12.sp, color = Color.White)
>>>>>>> origin/master
    }
    drawPath(path = path, color = color, style = Stroke(width = 12f))
}

data class DrawPath(
    val color: Color,
    val points: MutableList<Offset>
)