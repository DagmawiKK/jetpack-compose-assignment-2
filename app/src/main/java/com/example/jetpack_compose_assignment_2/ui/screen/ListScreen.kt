package com.example.jetpack_compose_assignment_2.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetpack_compose_assignment_2.ui.viewmodels.ListScreenViewModel
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navController: NavController,
    listScreenViewModel: ListScreenViewModel = hiltViewModel(),
    onNavigateToDetailScreen: (Int) -> Unit = {}
) {
    val state by listScreenViewModel.state.collectAsState()
    val cardColor = MaterialTheme.colorScheme.surfaceVariant
    val patternColor = cardColor.copy(alpha = 0.5f)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo List") }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val step = 48.dp.toPx()
                for (x in 0..(size.width / step).toInt() + 1) {
                    for (y in 0..(size.height / step).toInt() + 1) {
                        drawCircle(
                            color = patternColor,
                            radius = 16.dp.toPx(),
                            center = androidx.compose.ui.geometry.Offset(
                                x * step + if (y % 2 == 0) 0f else step / 2,
                                y * step
                            )
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = state.isLoading,
                enter = fadeIn(),
                exit = fadeOut(),
                modifier = Modifier.align(Alignment.Center)
            ) {
                CircularProgressIndicator()
            }
            if (state.error != null) {
                Text(
                    text = state.error ?: "Unknown error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            if (!state.isLoading && state.error == null) {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.todoList) { todo ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = todo.title,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = if (todo.completed) "Completed" else "Pending",
                                    color = if (todo.completed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Button(
                                        onClick = { onNavigateToDetailScreen(todo.id) }
                                    ) {
                                        Text("View Details")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}