package com.example.proyectofinal.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectofinal.R
import com.example.proyectofinal.viewmodel.ExportState
import com.example.proyectofinal.viewmodel.ExportViewModel
import com.example.proyectofinal.viewmodel.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExportScreen(
    onBack: () -> Unit,
    noteViewModel: NoteViewModel = viewModel(),
    exportViewModel: ExportViewModel = viewModel()
) {
    val context = LocalContext.current
    val notes by noteViewModel.notes.observeAsState(initial = emptyList())
    val exportState by exportViewModel.exportState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(exportState) {
        when (exportState) {
            is ExportState.Success -> {
                val json = (exportState as ExportState.Success).json
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "application/json"
                    putExtra(Intent.EXTRA_TEXT, json)
                    putExtra(Intent.EXTRA_SUBJECT, "respaldo_notas.json")
                }
                val chooser = Intent.createChooser(intent, "Guardar o compartir notas").apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(chooser)
            }
            is ExportState.Error -> {
                snackbarHostState.showSnackbar(
                    (exportState as ExportState.Error).message
                )
            }
            else -> Unit
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.export_title),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.export_json_desc),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            when (exportState) {
                is ExportState.Idle -> {
                    Button(
                        onClick = { exportViewModel.exportNotes(notes) },
                        enabled = notes.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF6200EE)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.export_as, "JSON (${notes.size})"))
                    }
                    if (notes.isEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.no_notes),
                            color = Color.Gray,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                is ExportState.Loading -> {
                    CircularProgressIndicator(color = Color(0xFF6200EE))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(stringResource(R.string.export_select_format))
                }

                is ExportState.Success -> {
                    Text(
                        text = "✅ ${stringResource(R.string.export_json_desc)}",
                        color = Color(0xFF388E3C),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    OutlinedButton(
                        onClick = { exportViewModel.resetState() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.export_as, "JSON"))
                    }
                }

                is ExportState.Error -> {
                    Text(
                        text = "❌ ${stringResource(R.string.export_title)}",
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(
                        onClick = { exportViewModel.resetState() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.export_accept))
                    }
                }
            }
        }
    }
}