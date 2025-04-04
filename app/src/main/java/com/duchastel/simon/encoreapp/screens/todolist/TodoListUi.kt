package com.duchastel.simon.encoreapp.screens.todolist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dagger.hilt.components.SingletonComponent

@CircuitInject(TodoListScreen::class, SingletonComponent::class)
class TodoListUi: Ui<TodoListScreen.State> {
    @Composable
    override fun Content(state: TodoListScreen.State, modifier: Modifier) {
        Text(state.name)
    }
}