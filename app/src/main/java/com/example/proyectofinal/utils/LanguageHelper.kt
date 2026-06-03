package com.example.proyectofinal.utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LanguageHelper {

    fun getSavedLanguage(context: Context): String {
        return context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            .getString("language", "es") ?: "es"
    }

    fun saveLanguage(context: Context, langCode: String) {
        context.getSharedPreferences("settings", Context.MODE_PRIVATE)
            .edit().putString("language", langCode).commit()
    }

    fun getLocalizedContext(context: Context, langCode: String): Context {
        val locale = Locale(langCode)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}