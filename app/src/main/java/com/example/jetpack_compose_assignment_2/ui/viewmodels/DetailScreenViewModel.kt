package com.example.jetpack_compose_assignment_2.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpack_compose_assignment_2.data.repositories.ToDoRepository
import com.example.jetpack_compose_assignment_2.ui.state.DetailScreenEvent
import com.example.jetpack_compose_assignment_2.ui.state.DetailScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
) : ViewModel() {
    private val _state = MutableStateFlow(DetailScreenState())
    val state: StateFlow<DetailScreenState> = _state

    init {
        handleEvent(DetailScreenEvent.FetchTodo)
    }

    fun handleEvent(event: DetailScreenEvent) {
        when (event) {
            is DetailScreenEvent.OnIdChange -> {
                _state.value = _state.value.copy(id = event.id)
            }
            is DetailScreenEvent.OnTitleChange -> {
                _state.value = _state.value.copy(title = event.title)
            }
            is DetailScreenEvent.OnCompletedChange -> {
                _state.value = _state.value.copy(completed = event.completed)
            }
            DetailScreenEvent.FetchTodo -> {
                fetchTodo()
            }
        }
    }

    private fun fetchTodo() {
        val todoId = state.value.id
        if (todoId == 0) {
            _state.value = _state.value.copy(error = "Invalid Todo ID")
            return
        }
        _state.value = _state.value.copy(isLoading = true, error = null)

        viewModelScope.launch {
            try {
                val todo = toDoRepository.getTodoById(todoId)
                _state.value = _state.value.copy(
                    title = todo.title,
                    completed = todo.completed,
                    isLoading = false,
                    error = null,
                    isSuccess = true
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "An unknown error occurred"
                )
            }
        }
    }
}