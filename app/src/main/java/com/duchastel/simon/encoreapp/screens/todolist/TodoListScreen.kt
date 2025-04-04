package com.duchastel.simon.encoreapp.screens.todolist

import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import kotlinx.parcelize.Parcelize

@Parcelize
data object TodoListScreen: Screen {
    data object State: CircuitUiState
}