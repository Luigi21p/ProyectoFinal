package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectofinal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoAnnotationScreen(
    onBackClick: () -> Unit = {},
    onConfirmClick: () -> Unit = {}
) {

    var selectedTool by remember { mutableStateOf("Dibujar") }

    var selectedColor by remember { mutableStateOf(Color.Red) }

    val colorsList = listOf(Color.Red, Color.Yellow, Color.Green, Color.Blue, Color.White, Color.Black)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Anotar imagen", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_back),
                            contentDescription = "Atrás"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Acción borrar todo */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_delete),
                            contentDescription = "Borrar todo"
                        )
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
                .background(Color.Black)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF212121))
                    .padding(8.dp)
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

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
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {

                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF1C1C1C))
                ) {

                }

                Text(
                    text = "Área de edición de imagen",
                    color = Color.White.copy(alpha = 0.4f),
                    fontSize = 14.sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = onBackClick,
                    modifier = Modifier.weight(1.0f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                ) {
                    Text("CANCELAR")
                }
                Button(
                    onClick = onConfirmClick,
                    modifier = Modifier.weight(1.0f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Text("GUARDAR ANOTACIÓN")
                }
            }
        }
    }
}


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
    }
}
