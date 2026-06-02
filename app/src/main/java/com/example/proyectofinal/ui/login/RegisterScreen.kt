package com.example.proyectofinal.ui.login   // 👈 Faltaba el package

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectofinal.navigation.AppScreens
import com.example.proyectofinal.viewmodel.AuthState
import com.example.proyectofinal.viewmodel.LoginViewModel

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorLocal by remember { mutableStateOf("") }

    val authState by viewModel.authState.collectAsState()

    // Navega al Dashboard cuando el registro es exitoso
    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            navController.navigate(AppScreens.DashboardScreen.route) {
                popUpTo(AppScreens.LoginScreen.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Crear Cuenta",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6200EE)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (authState is AuthState.Loading) {
            CircularProgressIndicator(color = Color(0xFF6200EE))
        } else {
            Button(
                onClick = {
                    when {
                        email.isBlank() || password.isBlank() -> {
                            errorLocal = "Completa todos los campos"
                        }
                        password != confirmPassword -> {
                            errorLocal = "Las contraseñas no coinciden"
                        }
                        password.length < 6 -> {
                            errorLocal = "La contraseña debe tener al menos 6 caracteres"
                        }
                        else -> {
                            errorLocal = ""
                            viewModel.register(email, password)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text("Registrarse", color = Color.White)
            }
        }

        // Muestra error local (validación) o error de Firebase
        val mensajeError = errorLocal.ifEmpty {
            (authState as? AuthState.Error)?.message ?: ""
        }
        if (mensajeError.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(mensajeError, color = Color.Red, fontSize = 13.sp)
        }

        TextButton(onClick = { navController.popBackStack() }) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}