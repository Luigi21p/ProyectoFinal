package com.example.proyectofinal.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ImageViewModel : ViewModel() {

    // Guarda la imagen seleccionada
    private val _selectedImage = MutableLiveData<Bitmap?>()
    val selectedImage: LiveData<Bitmap?> = _selectedImage

    fun setSelectedImage(bitmap: Bitmap?) {
        _selectedImage.value = bitmap
    }
}