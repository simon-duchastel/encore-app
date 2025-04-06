package com.duchastel.simon.encoreapp.data

import com.duchastel.simon.encoreapp.screens.todolist.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoItemRepository @Inject constructor() {
    private val initialTodoItems = listOf(
        TodoItem(
            id = "1",
            text = """
                Learn Compose and read lots of blogs and docs so that I understand 
                everything I need to know to build an app in compose.
            """.trimIndent(),
            isCompleted = true
        ),
        TodoItem(
            id = "2",
            text = "Build an Encore app",
            isCompleted = false
        ),
        TodoItem(
            id = "3",
            text = "Publish to Play Store",
            isCompleted = false
        )
    )

    private val _todoItems = MutableStateFlow(initialTodoItems)

    fun getTodoItems(): Flow<List<TodoItem>> {
        return _todoItems.asStateFlow()
    }

    fun updateTodoItem(id: String, isCompleted: Boolean) {
        val updatedItems = _todoItems.value.map { item ->
            if (item.id == id) {
                item.copy(isCompleted = isCompleted)
            } else {
                item
            }
        }
        _todoItems.value = updatedItems
    }
}