package com.example.proyectofinal.data.repository
import com.example.proyectofinal.data.model.NoteModel
import com.google.firebase.auth.FirebaseAuth                          // ✅
import com.google.firebase.firestore.FirebaseFirestore                // ✅
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
class NoteRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // Colección del usuario actual
    private fun userNotesCollection() =
        db.collection("users")
            .document(auth.currentUser?.uid ?: "anonymous")
            .collection("notes")

    // ✅ Escuchar notas en tiempo real
    fun listenToNotes(onUpdate: (List<NoteModel>) -> Unit): ListenerRegistration {
        return userNotesCollection()
            .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) return@addSnapshotListener
                val notes = snapshot.documents.mapNotNull { it.toObject(NoteModel::class.java) }
                onUpdate(notes)
            }
    }

    // ✅ Agregar nota
    fun addNote(note: NoteModel) {
        userNotesCollection()
            .document(note.id)
            .set(note)
    }

    // ✅ Actualizar nota
    fun updateNote(note: NoteModel) {
        userNotesCollection()
            .document(note.id)
            .set(note)
    }

    // ✅ Eliminar nota
    fun deleteNote(noteId: String) {
        userNotesCollection()
            .document(noteId)
            .delete()
    }

    // ✅ Buscar (filtro local sobre lo que ya cargó el listener)
    fun searchNotes(query: String, allNotes: List<NoteModel>): List<NoteModel> {
        return allNotes.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.content.contains(query, ignoreCase = true)
        }
    }

    // ✅ Filtrar por categoría (filtro local)
    fun getNotesByCategory(category: String, allNotes: List<NoteModel>): List<NoteModel> {
        return allNotes.filter { it.category == category }
    }
}