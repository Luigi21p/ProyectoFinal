package com.example.proyectofinal.data.model

data class NoteModel(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val category: String = "",
    val imageUrl: String = "",      // Solo una vez
    val drawingUrl: String = "",    // Para dibujos
    val reminderTime: Long = 0L,    // Para recordatorios
    val createdAt: Long = System.currentTimeMillis()
)