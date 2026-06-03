package com.example.proyectofinal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofinal.data.model.NoteModel
import com.example.proyectofinal.data.repository.NoteRepository
import java.util.UUID

class NoteViewModel : ViewModel() {

    private val repository = NoteRepository()

    private val _notes = MutableLiveData<List<NoteModel>>()
    val notes: LiveData<List<NoteModel>> = _notes

    private val _selectedNote = MutableLiveData<NoteModel?>()
    val selectedNote: LiveData<NoteModel?> = _selectedNote

    init {
        loadNotes()
    }

    fun loadNotes() {
        _notes.value = repository.getAllNotes()
    }

    fun addNote(title: String, content: String, category: String, imageUrl: String = "") {
        val note = NoteModel(
            id = UUID.randomUUID().toString(),
            title = title,
            content = content,
            category = category,
            imageUrl = imageUrl
        )
        repository.addNote(note)
        loadNotes()
    }

    fun updateNote(note: NoteModel) {
        repository.updateNote(note)
        loadNotes()
    }

    fun deleteNote(noteId: String) {
        repository.deleteNote(noteId)
        loadNotes()
    }

    fun selectNote(note: NoteModel) {
        _selectedNote.value = note
    }

    fun searchNotes(query: String) {
        _notes.value = repository.searchNotes(query)
    }

    fun filterByCategory(category: String) {
        _notes.value = repository.getNotesByCategory(category)
    }
}