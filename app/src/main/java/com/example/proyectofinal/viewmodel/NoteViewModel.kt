package com.example.proyectofinal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofinal.data.model.NoteModel
import com.example.proyectofinal.data.repository.NoteRepository
import com.google.firebase.firestore.ListenerRegistration
import java.util.UUID

class NoteViewModel : ViewModel() {

    private val repository = NoteRepository()

    private val _notes = MutableLiveData<List<NoteModel>>(emptyList())
    val notes: LiveData<List<NoteModel>> = _notes

    private var allNotes: List<NoteModel> = emptyList()

    private val _selectedNote = MutableLiveData<NoteModel?>()
    val selectedNote: LiveData<NoteModel?> = _selectedNote

    private var listener: ListenerRegistration? = null

    init {
        startListening()
    }

    private fun startListening() {
        listener = repository.listenToNotes { notes ->
            allNotes = notes
            _notes.value = notes
        }
    }

    fun loadNotes() {
        _notes.value = allNotes
    }

    fun addNote(
        title: String,
        content: String,
        category: String,
        imageUrl: String = "",
        drawingUrl: String = "",
        reminderTime: Long = 0L
    ) {
        val note = NoteModel(
            id = UUID.randomUUID().toString(),
            title = title,
            content = content,
            category = category,
            imageUrl = imageUrl,
            drawingUrl = drawingUrl,
            reminderTime = reminderTime,
            createdAt = System.currentTimeMillis()
        )
        repository.addNote(note)
    }

    fun addNoteComplete(note: NoteModel) {
        repository.addNote(note)
    }

    fun updateNote(note: NoteModel) {
        repository.updateNote(note)
    }

    fun deleteNote(noteId: String) {
        repository.deleteNote(noteId)
    }

    fun selectNote(note: NoteModel) {
        _selectedNote.value = note
    }

    fun searchNotes(query: String) {
        _notes.value = repository.searchNotes(query, allNotes)
    }

    fun filterByCategory(category: String) {
        _notes.value = repository.getNotesByCategory(category, allNotes)
    }

    // ✅ Sin import — override directo de ViewModel
    override fun onCleared() {
        super.onCleared()
        listener?.remove()
    }
}