package com.duchastel.simon.encoreapp.screens.todolist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Surface
import com.duchastel.simon.encoreapp.ui.components.SectionHeader
import com.duchastel.simon.encoreapp.ui.components.TodoRow
import com.duchastel.simon.encoreapp.utils.Async
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dagger.hilt.components.SingletonComponent

@CircuitInject(TodoListScreen::class, SingletonComponent::class)
class TodoListUi: Ui<TodoListScreen.State> {

    /**
     * Composable function that displays the content of the Todo List screen
     *
     * @param state The current state of the TodoListScreen
     * @param modifier Optional Modifier for customizing layout behavior
     */
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content(state: TodoListScreen.State, modifier: Modifier) {
        LazyColumn(
            modifier = modifier
        ) {
            stickyHeader {
                SectionHeader(title = "Todo List")
            }

            when (val todoItems = state.todoItems) {
                is Async.Success -> {
                    items(todoItems()) { todoItem ->
                        TodoRow(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            todoItem = todoItem,
                            onToggleComplete = { isCompleted ->
                                state.emitEvent(
                                    TodoListScreen.Event.TodoItemCheckedChanged(
                                        id = todoItem.id,
                                        isCompleted = isCompleted,
                                    )
                                )
                            }
                        )
                    }
                }
                is Async.Uninitialized, is Async.Loading -> {
                    item {
                        CircularProgressIndicator()
                    }
                }
                is Async.Fail -> {
                    item {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

// Previews

@Preview(name = "Todo List - Loading")
@Composable
internal fun TodoListLoadingPreview() {
    Surface {
        TodoListUi().Content(
            state = TodoListScreen.State(
                todoItems = Async.Loading(),
                emitEvent = {}
            ),
            modifier = Modifier
        )
    }
}

@Preview(name = "Todo List - Success")
@Composable
internal fun TodoListSuccessPreview() {
    Surface {
        TodoListUi().Content(
            state = TodoListScreen.State(
                todoItems = Async.Success(
                    listOf(
                        TodoItem(
                            id = "1",
                            text = "Buy groceries",
                            isCompleted = false
                        ),
                        TodoItem(
                            id = "2",
                            text = "Finish project",
                            isCompleted = true
                        ),
                        TodoItem(
                            id = "3",
                            text = "Exercise",
                            isCompleted = false
                        )
                    )
                ),
                emitEvent = {}
            ),
            modifier = Modifier
        )
    }
}

@Preview(name = "Todo List - Error")
@Composable
internal fun TodoListErrorPreview() {
    Surface {
        TodoListUi().Content(
            state = TodoListScreen.State(
                todoItems = Async.Fail(Exception("Failed to load todo items")),
                emitEvent = {}
            ),
            modifier = Modifier
        )
    }
}