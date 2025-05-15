package com.example.jetpack_compose_assignment_2.di

import com.example.jetpack_compose_assignment_2.data.api.ApiService
import com.example.jetpack_compose_assignment_2.data.repositories.ToDoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideTodoRepository(apiService: ApiService): ToDoRepository {
        return ToDoRepository(apiService)
    }
}