package com.example.proyectofinal.data.model

data class NoteModel(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val category: String = "",
    val imageUrl: String = "",
    val createdAt: Long = System.currentTimeMillis()
)