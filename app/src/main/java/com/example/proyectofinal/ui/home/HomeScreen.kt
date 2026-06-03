package com.example.proyectofinal.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.proyectofinal.R
import com.example.proyectofinal.data.model.NoteModel
import com.example.proyectofinal.navigation.AppScreens
import com.example.proyectofinal.viewmodel.NoteViewModel
import androidx.compose.material.icons.filled.Edit

val Purple = Color(0xFF6200EE)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    onNoteClick: (NoteModel) -> Unit,
    onAddNote: () -> Unit,
    viewModel: NoteViewModel = viewModel()
) {
    val notes by viewModel.notes.observeAsState(emptyList())
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todas") }

    //  Control del FAB expandido
    var fabExpanded by remember { mutableStateOf(false) }

    val categoriasMap = listOf(
        R.string.tab_all        to "Todas",
        R.string.tab_personal   to "Personal",
        R.string.tab_university to "Universidad",
        R.string.tab_work       to "Trabajo",
        R.string.tab_ideas      to "Ideas",
        R.string.tab_other      to "Otro"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.my_notes),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        try {
                            com.google.firebase.auth.FirebaseAuth.getInstance().signOut()
                            navController.navigate(AppScreens.LoginScreen.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        } catch (e: Exception) {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Cerrar Sesión",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    // ✅ Botón directo a PhotoNoteScreen en el TopBar
                    IconButton(onClick = {
                        navController.navigate(AppScreens.PhotoNoteScreen.route)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_image),
                            contentDescription = "Nota con imagen",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate(AppScreens.SettingsScreen.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Configuración",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple
                )
            )
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Mini FABs que aparecen al expandir
                if (fabExpanded) {
                    // Opción: nota con foto
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF212121),
                            tonalElevation = 4.dp
                        ) {
                            Text(
                                text = "Nota con imagen",
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                fontSize = 13.sp
                            )
                        }
                        SmallFloatingActionButton(
                            onClick = {
                                fabExpanded = false
                                navController.navigate(AppScreens.PhotoNoteScreen.route)
                            },
                            containerColor = Purple
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_image),
                                contentDescription = "Nota con imagen",
                                tint = Color.White
                            )
                        }
                    }

                    // Opción: nota de texto
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFF212121),
                            tonalElevation = 4.dp
                        ) {
                            Text(
                                text = "Nota de texto",
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                fontSize = 13.sp
                            )
                        }
                        SmallFloatingActionButton(
                            onClick = {
                                fabExpanded = false
                                onAddNote()
                            },
                            containerColor = Purple
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Nueva nota",
                                tint = Color.White
                            )
                        }
                    }
                }

                //  FAB principal — abre/cierra el menú
                FloatingActionButton(
                    onClick = { fabExpanded = !fabExpanded },
                    containerColor = Purple
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar",
                        tint = Color.White
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFF212121),
                        tonalElevation = 4.dp
                    ) {
                        Text(
                            text = "Dibujo libre",
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 13.sp
                        )
                    }
                    SmallFloatingActionButton(
                        onClick = {
                            fabExpanded = false
                            navController.navigate("drawing")  //  Navegar a pantalla de dibujo
                        },
                        containerColor = Purple
                    ) {
                        Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Dibujo libre",
                        tint = Color.White
                    )

                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF5F5F5))
        ) {
            ScrollableTabRow(
                selectedTabIndex = categoriasMap.map { it.second }
                    .indexOf(selectedCategory).coerceAtLeast(0),
                containerColor = Color.White,
                contentColor = Purple
            ) {
                categoriasMap.forEach { (stringRes, categoriaId) ->
                    Tab(
                        selected = selectedCategory == categoriaId,
                        onClick = {
                            selectedCategory = categoriaId
                            if (categoriaId == "Todas") viewModel.loadNotes()
                            else viewModel.filterByCategory(categoriaId)
                        },
                        text = { Text(stringResource(id = stringRes)) }
                    )
                }
            }

            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    if (it.isEmpty()) viewModel.loadNotes()
                    else viewModel.searchNotes(it)
                },
                placeholder = { Text(stringResource(id = R.string.search_notes)) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            if (notes.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(id = R.string.no_notes),
                        color = Color.Gray,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(notes) { note ->
                        NoteCard(
                            note = note,
                            onClick = { onNoteClick(note) },
                            onDelete = { viewModel.deleteNote(note.id) }
                        )
                    }
                }
            }
        }
    }
}

// NoteCard sin cambios
@Composable
fun NoteCard(
    note: NoteModel,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = note.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF212121)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = note.content,
                    fontSize = 13.sp,
                    color = Color.Gray,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(8.dp))

                val categoryText = when (note.category.trim()) {
                    "Personal"    -> stringResource(id = R.string.tab_personal)
                    "Universidad" -> stringResource(id = R.string.tab_university)
                    "Trabajo"     -> stringResource(id = R.string.tab_work)
                    "Ideas"       -> stringResource(id = R.string.tab_ideas)
                    "Otro"        -> stringResource(id = R.string.tab_other)
                    else          -> note.category
                }

                Text(
                    text = categoryText,
                    fontSize = 11.sp,
                    color = Color.White,
                    modifier = Modifier
                        .background(Purple, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
            }
        }
    }
}