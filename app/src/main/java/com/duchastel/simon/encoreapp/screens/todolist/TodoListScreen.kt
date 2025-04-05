package com.duchastel.simon.encoreapp.screens.todolist

import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object TodoListScreen: Screen {
    data class State(
        val todoItems: List<TodoItem> = emptyList(),
        val emitEvent: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data class TodoItemCheckedChanged(val id: String, val isCompleted: Boolean) : Event
    }
}

data class TodoItem(
    val id: String,
    val text: String,
    val isCompleted: Boolean
)