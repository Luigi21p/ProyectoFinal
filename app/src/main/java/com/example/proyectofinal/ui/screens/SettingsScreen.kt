package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofinal.R
import com.example.proyectofinal.utils.LanguageHelper
import com.example.proyectofinal.utils.LanguageState

val Purple = Color(0xFF6200EE)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {

    val context = LocalContext.current

    var selectedLanguage by remember {
        mutableStateOf(LanguageHelper.getSavedLanguage(context))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.settings_title), fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(R.string.settings_language),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
            )
            Text(
                text = stringResource(R.string.settings_language_desc),
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        selectedLanguage = "es"
                        LanguageHelper.saveLanguage(context, "es")
                        LanguageState.currentLanguage.value = "es"
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedLanguage == "es") Purple else Color.LightGray,
                        contentColor = if (selectedLanguage == "es") Color.White else Color.DarkGray
                    )
                ) {
                    Text("🇨🇴 Español", fontWeight = FontWeight.Medium)
                }

                Button(
                    onClick = {
                        selectedLanguage = "en"
                        LanguageHelper.saveLanguage(context, "en")
                        LanguageState.currentLanguage.value = "en"
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedLanguage == "en") Purple else Color.LightGray,
                        contentColor = if (selectedLanguage == "en") Color.White else Color.DarkGray
                    )
                ) {
                    Text("🇺🇸 English", fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}