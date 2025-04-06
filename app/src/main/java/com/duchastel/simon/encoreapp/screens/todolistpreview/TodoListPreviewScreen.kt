package com.duchastel.simon.encoreapp.screens.todolistpreview

import androidx.compose.runtime.Immutable
import com.duchastel.simon.encoreapp.screens.todolist.TodoItem
import com.duchastel.simon.encoreapp.utils.Async
import com.slack.circuit.runtime.CircuitUiEvent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object TodoListPreviewScreen : Screen {
    @Immutable
    data class State(
        val todoItems: Async<List<TodoItem>>,
        val emitEvent: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Event : CircuitUiEvent {
        data class TodoItemCheckedChanged(
            val id: String,
            val isCompleted: Boolean,
        ) : Event
    }
}