package com.example.proyectofinal.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.ui.home.HomeScreen
import com.example.proyectofinal.ui.note.NoteScreen
import com.example.proyectofinal.viewmodel.NoteViewModel
import com.example.proyectofinal.data.model.NoteModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val noteViewModel: NoteViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
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