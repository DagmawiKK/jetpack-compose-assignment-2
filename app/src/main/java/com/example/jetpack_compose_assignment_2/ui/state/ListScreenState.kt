package com.example.jetpack_compose_assignment_2.ui.state

import com.example.jetpack_compose_assignment_2.data.models.Todo

data class ListScreenState(
    val todoList: List<Todo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

sealed class ListScreenEvent {
    object getTodos : ListScreenEvent()
    data class OnTodoClick(val todo: Todo) : ListScreenEvent()
    data class NavigateToDetailsScreen(val id: Int) : ListScreenEvent()
}