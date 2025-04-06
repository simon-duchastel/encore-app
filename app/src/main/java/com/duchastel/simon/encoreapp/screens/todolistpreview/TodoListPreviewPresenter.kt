package com.duchastel.simon.encoreapp.screens.todolistpreview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.duchastel.simon.encoreapp.data.TodoItemRepository
import com.duchastel.simon.encoreapp.utils.Async
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@CircuitInject(TodoListPreviewScreen::class, SingletonComponent::class)
class TodoListPreviewPresenter @Inject constructor(
    private val todoItemRepository: TodoItemRepository
) : Presenter<TodoListPreviewScreen.State> {
    @Composable
    override fun present(): TodoListPreviewScreen.State {
        val todoItems by todoItemRepository.getTodoItems().collectAsState(initial = null)

        return TodoListPreviewScreen.State(
            todoItems = todoItems?.let { Async.Success(it) } ?: Async.Loading(),
            emitEvent = { event ->
                when (event) {
                    is TodoListPreviewScreen.Event.TodoItemCheckedChanged -> {
                        todoItemRepository.updateTodoItem(event.id, event.isCompleted)
                    }
                }
            }
        )
    }
}