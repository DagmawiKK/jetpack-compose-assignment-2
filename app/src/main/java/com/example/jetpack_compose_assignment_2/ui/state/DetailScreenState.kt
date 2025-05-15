package com.example.jetpack_compose_assignment_2.ui.state

data class DetailScreenState(
    val id: Int = 0,
    val title: String = "",
    val completed: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

sealed class DetailScreenEvent {
    data class OnIdChange(val id: Int) : DetailScreenEvent()
    data class OnTitleChange(val title: String) : DetailScreenEvent()
    data class OnCompletedChange(val completed: Boolean) : DetailScreenEvent()
    object FetchTodo : DetailScreenEvent()
}

