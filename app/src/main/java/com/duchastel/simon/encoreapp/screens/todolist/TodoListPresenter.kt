package com.duchastel.simon.encoreapp.screens.todolist

import androidx.compose.runtime.Composable
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@CircuitInject(TodoListScreen::class, SingletonComponent::class)
class TodoListPresenter @Inject constructor() : Presenter<TodoListScreen.State> {
    @Composable
    override fun present(): TodoListScreen.State {
        return TodoListScreen.State
    }
}