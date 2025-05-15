package com.example.jetpack_compose_assignment_2.data.repositories

import com.example.jetpack_compose_assignment_2.data.api.ApiService
import com.example.jetpack_compose_assignment_2.data.models.Todo
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ToDoRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getTodos(): List<Todo> {
        return try {
            apiService.getTodos()
        } catch (e: Exception) {
            throw Exception("Failed to fetch user list: ${e.message}")
        }
    }
    suspend fun getTodoById(id: Int): Todo {
        return try {
            apiService.getTodoById(id)
        } catch (e: Exception) {
            throw Exception("Failed to fetch user by ID: ${e.message}")
        }
    }
}
