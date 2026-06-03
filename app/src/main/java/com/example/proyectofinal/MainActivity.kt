package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.proyectofinal.navigation.AppNavigation
import androidx.activity.enableEdgeToEdge
import com.example.proyectofinal.navigation.AppNavigation
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _root_ide_package_.com.example.proyectofinal.ui.theme.ProyectoFinalTheme {
                AppNavigation()
            }
        }
    }
}