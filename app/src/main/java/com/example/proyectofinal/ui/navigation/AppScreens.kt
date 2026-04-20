package com.example.proyectofinal.ui.navigation

sealed class AppScreens(val route: String) {
    object LoginScreen: AppScreens("login_screen")
    object RegisterScreen: AppScreens("register_screen")
    object DashboardScreen: AppScreens("dashboard_screen")
    object RedactarNotaScreen: AppScreens("redactar_nota_screen")
    object SettingsScreen: AppScreens("settings_screen")

    object ExportScreen: AppScreens("export_screen")
}