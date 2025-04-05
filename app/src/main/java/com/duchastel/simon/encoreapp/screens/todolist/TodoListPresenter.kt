package com.duchastel.simon.encoreapp.screens.todolist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@CircuitInject(TodoListScreen::class, SingletonComponent::class)
class TodoListPresenter @Inject constructor() : Presenter<TodoListScreen.State> {
    @Composable
    override fun present(): TodoListScreen.State {
        var todoItems by remember {
            mutableStateOf(
                listOf(
                    TodoItem(
                        id = "1",
                        text = "Learn Compose",
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
            )
        }

        return TodoListScreen.State(
            todoItems = todoItems,
            emitEvent = { event ->
                when (event) {
                    is TodoListScreen.Event.TodoItemCheckedChanged -> {
                        todoItems = todoItems.map { item ->
                            if (item.id == event.id) {
                                item.copy(isCompleted = event.isCompleted)
                            } else {
                                item
                            }
                        }
                    }
                }
            }
        )
    }
}