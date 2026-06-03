package com.example.proyectofinal.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DrawingViewModel : ViewModel() {

    private val _savedDrawing = MutableLiveData<Bitmap?>()
    val savedDrawing: LiveData<Bitmap?> = _savedDrawing

    fun saveDrawing(bitmap: Bitmap) {
        _savedDrawing.value = bitmap
    }

    fun clearDrawing() {
        _savedDrawing.value = null
    }
}