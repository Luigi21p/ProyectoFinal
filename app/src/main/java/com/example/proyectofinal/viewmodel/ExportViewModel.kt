package com.example.proyectofinal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.data.model.NoteModel
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class ExportState {
    object Idle    : ExportState()
    object Loading : ExportState()
    data class Success(val json: String) : ExportState()
    data class Error(val message: String) : ExportState()
}

class ExportViewModel : ViewModel() {

    private val _exportState = MutableStateFlow<ExportState>(ExportState.Idle)
    val exportState: StateFlow<ExportState> = _exportState

    fun exportNotes(notes: List<NoteModel>) {
        viewModelScope.launch {
            _exportState.value = ExportState.Loading
            try {
                val json = withContext(Dispatchers.IO) {
                    GsonBuilder()
                        .setPrettyPrinting()
                        .create()
                        .toJson(notes)
                }
                _exportState.value = ExportState.Success(json)
            } catch (e: Exception) {
                _exportState.value = ExportState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun resetState() {
        _exportState.value = ExportState.Idle
    }
}