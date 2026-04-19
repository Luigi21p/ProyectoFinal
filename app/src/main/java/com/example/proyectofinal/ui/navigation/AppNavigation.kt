package com.example.proyectofinal.ui.navigation

import RegisterScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinal.ui.screens.LoginScreen
import com.example.proyectofinal.ui.screens.DashboardScreen
import com.example.proyectofinal.ui.screens.SettingsScreen
import com.example.proyectofinal.ui.screens.RedactarNotaScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.LoginScreen.route) {
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
    }
}