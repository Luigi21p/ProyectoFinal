package com.example.proyectofinal.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.ui.login.LoginScreen
import com.example.proyectofinal.ui.login.RegisterScreen          // ✅ con paquete
import com.example.proyectofinal.ui.screens.DashboardScreen
import com.example.proyectofinal.ui.screens.SettingsScreen
import com.example.proyectofinal.ui.screens.RedactarNotaScreen
import com.example.proyectofinal.ui.screens.ExportScreen
import com.example.proyectofinal.ui.screens.PhotoAnnotationScreen
import com.example.proyectofinal.ui.screens.PhotoNoteScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = _root_ide_package_.com.example.proyectofinal.navigation.AppScreens.LoginScreen.route
    ) {
        composable(_root_ide_package_.com.example.proyectofinal.navigation.AppScreens.LoginScreen.route) {
            _root_ide_package_.com.example.proyectofinal.ui.login.LoginScreen(navController)
        }
        composable(_root_ide_package_.com.example.proyectofinal.navigation.AppScreens.RegisterScreen.route) {
            _root_ide_package_.com.example.proyectofinal.ui.login.RegisterScreen(navController)
        }
        composable(_root_ide_package_.com.example.proyectofinal.navigation.AppScreens.DashboardScreen.route) {
            _root_ide_package_.com.example.proyectofinal.ui.screens.DashboardScreen(navController)
        }
        composable(_root_ide_package_.com.example.proyectofinal.navigation.AppScreens.RedactarNotaScreen.route) {
            _root_ide_package_.com.example.proyectofinal.ui.screens.RedactarNotaScreen(navController)
        }
        composable(_root_ide_package_.com.example.proyectofinal.navigation.AppScreens.SettingsScreen.route) {
            _root_ide_package_.com.example.proyectofinal.ui.screens.SettingsScreen(navController)
        }
        composable(_root_ide_package_.com.example.proyectofinal.navigation.AppScreens.ExportScreen.route) {
            _root_ide_package_.com.example.proyectofinal.ui.screens.ExportScreen(onBack = { navController.popBackStack() })
        }
        composable(_root_ide_package_.com.example.proyectofinal.navigation.AppScreens.PhotoNoteScreen.route) {
            _root_ide_package_.com.example.proyectofinal.ui.screens.PhotoNoteScreen(onBackClick = { navController.popBackStack() })
        }
        composable(_root_ide_package_.com.example.proyectofinal.navigation.AppScreens.PhotoAnnotationScreen.route) {
            _root_ide_package_.com.example.proyectofinal.ui.screens.PhotoAnnotationScreen(
                onBackClick = { navController.popBackStack() },
                onConfirmClick = { navController.popBackStack() }
            )
        }
    }
}