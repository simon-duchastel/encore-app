package com.duchastel.simon.encoreapp.screens.todolist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.duchastel.simon.encoreapp.data.TodoItemRepository
import com.duchastel.simon.encoreapp.utils.Async
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@CircuitInject(TodoListScreen::class, SingletonComponent::class)
class TodoListPresenter @Inject constructor(
    private val todoItemRepository: TodoItemRepository
) : Presenter<TodoListScreen.State> {
    @Composable
    override fun present(): TodoListScreen.State {
        val todoItems by todoItemRepository.getTodoItems().collectAsState(initial = null)

        return TodoListScreen.State(
            todoItems = todoItems?.let { Async.Success(it) } ?: Async.Loading(),
            emitEvent = { event ->
                when (event) {
                    is TodoListScreen.Event.TodoItemCheckedChanged -> {
                        todoItemRepository.updateTodoItem(event.id, event.isCompleted)
                    }
                }
            }
        )
    }
}