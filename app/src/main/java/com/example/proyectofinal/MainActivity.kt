package com.example.proyectofinal

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.proyectofinal.navigation.AppNavigation
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme
import com.example.proyectofinal.utils.LanguageHelper
import com.example.proyectofinal.utils.LanguageState
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LanguageState.currentLanguage.value = LanguageHelper.getSavedLanguage(this)

        enableEdgeToEdge()
        setContent {
            val lang by LanguageState.currentLanguage
            val localizedContext = LanguageHelper.getLocalizedContext(this, lang)

            CompositionLocalProvider(LocalContext provides localizedContext) {
                ProyectoFinalTheme {
                    AppNavigation()
                }
            }
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