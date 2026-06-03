package com.example.proyectofinal.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.ui.login.LoginScreen
import com.example.proyectofinal.ui.login.RegisterScreen
import com.example.proyectofinal.ui.screens.DashboardScreen
import com.example.proyectofinal.ui.screens.SettingsScreen
import com.example.proyectofinal.ui.screens.RedactarNotaScreen
import com.example.proyectofinal.ui.screens.ExportScreen
import com.example.proyectofinal.ui.screens.PhotoAnnotationScreen
import com.example.proyectofinal.ui.screens.PhotoNoteScreen
import com.example.proyectofinal.ui.home.HomeScreen
import com.example.proyectofinal.ui.note.NoteScreen
import com.example.proyectofinal.viewmodel.NoteViewModel
import com.example.proyectofinal.data.model.NoteModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    // Instanciamos el ViewModel de notas aquí para compartirlo entre HomeScreen y NoteScreen
    val noteViewModel: NoteViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen.route
    ) {
        // ---- PANTALLAS DE AUTENTICACIÓN & SISTEMA BASE ----
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(AppScreens.RegisterScreen.route) {
            RegisterScreen(navController)
        }
        composable(AppScreens.DashboardScreen.route) {
            DashboardScreen(navController)
        }
        composable(AppScreens.RedactarNotaScreen.route) {
            RedactarNotaScreen(navController)
        }
        composable(AppScreens.SettingsScreen.route) {
            SettingsScreen(navController)
        }
        composable(AppScreens.ExportScreen.route) {
            ExportScreen(onBack = { navController.popBackStack() })
        }
        composable(AppScreens.PhotoNoteScreen.route) {
            PhotoNoteScreen(onBackClick = { navController.popBackStack() })
        }
        composable(AppScreens.PhotoAnnotationScreen.route) {
            PhotoAnnotationScreen(
                onBackClick = { navController.popBackStack() },
                onConfirmClick = { navController.popBackStack() }
            )
        }

        // ---- PANTALLAS DE TU MÓDULO (GESTIÓN DE NOTAS) ----
        composable("home") {
            HomeScreen(
                onNoteClick = { note ->
                    noteViewModel.selectNote(note)
                    navController.navigate("note")
                },
                onAddNote = {
                    noteViewModel.selectNote(NoteModel())
                    navController.navigate("note")
                },
                viewModel = noteViewModel
            )
        }
        composable("note") {
            NoteScreen(
                onBack = {
                    navController.popBackStack()
                },
                viewModel = noteViewModel
            )
        }
    }
}