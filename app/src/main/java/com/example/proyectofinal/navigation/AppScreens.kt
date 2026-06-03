package com.example.proyectofinal.navigation

sealed class AppScreens(val route: String) {
    object LoginScreen : AppScreens("login_screen")
    object RegisterScreen : AppScreens("register_screen")
    object DashboardScreen : AppScreens("dashboard_screen")
    object RedactarNotaScreen : AppScreens("redactar_nota_screen")
    object SettingsScreen : AppScreens("settings_screen")
    object ExportScreen : AppScreens("export_screen")
    object PhotoNoteScreen : AppScreens("photo_note_screen")
    object PhotoAnnotationScreen : AppScreens("photo_annotation_screen")
    // Los integrantes 2, 3 y 4 agregan sus rutas aquí abajo
}