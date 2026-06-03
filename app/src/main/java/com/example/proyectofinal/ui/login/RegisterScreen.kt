package com.example.proyectofinal.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectofinal.R
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

    val errorEmpty = stringResource(R.string.register_error_empty)
    val errorMatch = stringResource(R.string.register_error_match)
    val errorShort = stringResource(R.string.register_error_short)

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            navController.navigate("home") {
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
            text = stringResource(R.string.register_title),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6200EE)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(stringResource(R.string.register_email)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.register_password)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(stringResource(R.string.register_confirm_password)) },
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
                        email.isBlank() || password.isBlank() -> errorLocal = errorEmpty
                        password != confirmPassword           -> errorLocal = errorMatch
                        password.length < 6                  -> errorLocal = errorShort
                        else -> {
                            errorLocal = ""
                            viewModel.register(email, password)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text(stringResource(R.string.register_btn), color = Color.White)
            }
        }

        val mensajeError = errorLocal.ifEmpty {
            (authState as? AuthState.Error)?.message ?: ""
        }
        if (mensajeError.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(mensajeError, color = Color.Red, fontSize = 13.sp)
        }

        TextButton(onClick = { navController.popBackStack() }) {
            Text(stringResource(R.string.register_already_account))
        }
    }
}