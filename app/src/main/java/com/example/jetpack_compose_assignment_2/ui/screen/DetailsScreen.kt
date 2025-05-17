package com.example.jetpack_compose_assignment_2.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.jetpack_compose_assignment_2.ui.viewmodels.DetailScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    detailScreenViewModel: DetailScreenViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val state by detailScreenViewModel.state.collectAsState()
    val cardColor = MaterialTheme.colorScheme.surfaceVariant
    val patternColor = cardColor.copy(alpha = 0.5f)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo Details") }
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
                Card(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(24.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = cardColor
                    ),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                        ) {
                            drawLine(
                                color = Color.Gray,
                                start = androidx.compose.ui.geometry.Offset(0f, 0f),
                                end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                                strokeWidth = 2f,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                            )
                        }
                        Text(
                            text = "User ID: ${state.userId}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "ID: ${state.id}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Title ${state.title}",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = if (state.completed) "Status: Completed" else "Status: Pending",
                            color = if (state.completed) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                        ) {
                            drawLine(
                                color = Color.Gray,
                                start = androidx.compose.ui.geometry.Offset(0f, 0f),
                                end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                                strokeWidth = 2f,
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(onClick = onBack) {
                                Text("Return")
                            }
                        }
                    }
                }
            }
        }
    }
}