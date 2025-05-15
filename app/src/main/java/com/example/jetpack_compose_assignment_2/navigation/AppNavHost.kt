package com.example.jetpack_compose_assignment_2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.jetpack_compose_assignment_2.ui.screen.DetailsScreen
import com.example.jetpack_compose_assignment_2.ui.screen.ListScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.compose.runtime.LaunchedEffect
import com.example.jetpack_compose_assignment_2.ui.state.DetailScreenEvent
import com.example.jetpack_compose_assignment_2.ui.viewmodels.DetailScreenViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "todo/listScreen"
    ) {
        composable("todo/listScreen") {
            ListScreen(
                navController = navController,
                onNavigateToDetailScreen = { todoId ->
                    navController.navigate("todo/detailScreen/$todoId")
                },
            )
        }

        composable(
            route = "todo/detailScreen/{todoId}",
            arguments = listOf(navArgument("todoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getInt("todoId") ?: 0
            val viewModel: DetailScreenViewModel = hiltViewModel()

            LaunchedEffect(todoId) {
                viewModel.handleEvent(DetailScreenEvent.OnIdChange(todoId))
                viewModel.handleEvent(DetailScreenEvent.FetchTodo)
            }

            DetailsScreen(
                detailScreenViewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}