package com.example.proyectofinal.data.repository

import com.example.proyectofinal.data.model.NoteModel

class NoteRepository {

    private val notes = mutableListOf<NoteModel>()

    fun getAllNotes(): List<NoteModel> {
        return notes.toList()
    }

    fun addNote(note: NoteModel) {
        notes.add(note)
    }

    fun updateNote(updatedNote: NoteModel) {
        val index = notes.indexOfFirst { it.id == updatedNote.id }
        if (index != -1) {
            notes[index] = updatedNote
        }
    }

    fun deleteNote(noteId: String) {
        notes.removeIf { it.id == noteId }
    }

    fun getNoteById(noteId: String): NoteModel? {
        return notes.find { it.id == noteId }
    }

    fun searchNotes(query: String): List<NoteModel> {
        return notes.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.content.contains(query, ignoreCase = true)
        }.toList()
    }

    fun getNotesByCategory(category: String): List<NoteModel> {
        return notes.filter { it.category == category }.toList()
    }
}