package com.example.proyectofinal

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.proyectofinal.navigation.AppNavigation
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme
import com.example.proyectofinal.utils.LanguageHelper
import com.example.proyectofinal.utils.LanguageState
import com.example.proyectofinal.utils.NotificationHelper
import com.example.proyectofinal.viewmodel.ImageViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import java.util.Locale

class MainActivity : ComponentActivity() {


    private val imageViewModel: ImageViewModel by viewModels()

    //  Launcher para recibir el resultado de ImagePicker
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        ImageDecoder.decodeBitmap(
                            ImageDecoder.createSource(contentResolver, uri)
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        android.provider.MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    }
                    imageViewModel.setSelectedImage(bitmap)
                }
            }
        }

    private val requestNotificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { _ -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LanguageState.currentLanguage.value = LanguageHelper.getSavedLanguage(this)
        NotificationHelper.createNotificationChannel(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestNotificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        enableEdgeToEdge()
        setContent {
            val lang by LanguageState.currentLanguage
            val localizedContext = LanguageHelper.getLocalizedContext(this, lang)

            CompositionLocalProvider(LocalContext provides localizedContext) {
                ProyectoFinalTheme {
                    AppNavigation(
                        onLaunchCamera = { launchCamera() },       // pasados a navegación
                        onLaunchGallery = { launchGallery() }
                    )
                }
            }
        }
    }

    //  Abre solo la cámara
    fun launchCamera() {
        ImagePicker.with(this)
            .cameraOnly()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                imagePickerLauncher.launch(intent)
            }
    }

    //  Abre solo la galería
    fun launchGallery() {
        ImagePicker.with(this)
            .galleryOnly()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                imagePickerLauncher.launch(intent)
            }
    }

    override fun attachBaseContext(newBase: Context) {
        val lang = newBase.getSharedPreferences("settings", Context.MODE_PRIVATE)
            .getString("language", "es") ?: "es"
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration(newBase.resources.configuration)
        config.setLocale(locale)
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }
}