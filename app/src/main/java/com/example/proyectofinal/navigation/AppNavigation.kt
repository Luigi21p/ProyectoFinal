package com.example.proyectofinal.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.ui.login.LoginScreen
import com.example.proyectofinal.ui.login.RegisterScreen
import com.example.proyectofinal.ui.screens.SettingsScreen
import com.example.proyectofinal.ui.screens.ExportScreen
import com.example.proyectofinal.ui.screens.PhotoAnnotationScreen
import com.example.proyectofinal.ui.screens.PhotoNoteScreen
import com.example.proyectofinal.ui.screens.DrawingScreen
import com.example.proyectofinal.ui.home.HomeScreen
import com.example.proyectofinal.ui.note.NoteScreen
import com.example.proyectofinal.viewmodel.NoteViewModel
import com.example.proyectofinal.data.model.NoteModel

@Composable
fun AppNavigation(
    onLaunchCamera: () -> Unit = {},
    onLaunchGallery: () -> Unit = {}
) {
    val navController = rememberNavController()
    val noteViewModel: NoteViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppScreens.LoginScreen.route
    ) {
        composable(AppScreens.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(AppScreens.RegisterScreen.route) {
            RegisterScreen(navController)
        }
        composable(AppScreens.SettingsScreen.route) {
            SettingsScreen(navController)
        }
        composable(AppScreens.ExportScreen.route) {
            ExportScreen(
                onBack = { navController.popBackStack() },
                noteViewModel = noteViewModel
            )
        }

        // PhotoNoteScreen
        composable(AppScreens.PhotoNoteScreen.route) {
            PhotoNoteScreen(
                onBackClick = { navController.popBackStack() },
                onLaunchCamera = onLaunchCamera,
                onLaunchGallery = onLaunchGallery,
                onAnnotateClick = { navController.navigate(AppScreens.PhotoAnnotationScreen.route) }
            )
        }

        // PhotoAnnotationScreen
        composable(AppScreens.PhotoAnnotationScreen.route) {
            PhotoAnnotationScreen(
                onBackClick = { navController.popBackStack() },
                onConfirmClick = { navController.popBackStack() }
            )
        }

        // ✅ DrawingScreen CORREGIDO (sin onSaveClick)
        composable("drawing") {
            DrawingScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("home") {
            HomeScreen(
                navController = navController,
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
                onBack = { navController.popBackStack() },
                viewModel = noteViewModel
            )
        }
    }
}