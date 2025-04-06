package com.duchastel.simon.encoreapp.screens.todolistpreview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.duchastel.simon.encoreapp.screens.todolist.TodoItem
import com.duchastel.simon.encoreapp.ui.components.SectionHeader
import com.duchastel.simon.encoreapp.ui.components.TodoRow
import com.duchastel.simon.encoreapp.utils.Async
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.ui.Ui
import dagger.hilt.components.SingletonComponent

@CircuitInject(TodoListPreviewScreen::class, SingletonComponent::class)
class TodoListPreviewUi : Ui<TodoListPreviewScreen.State> {

    @Composable
    override fun Content(state: TodoListPreviewScreen.State, modifier: Modifier) {
        Column(
            modifier = modifier
                .heightIn(max = 300.dp)
                .fillMaxWidth(),
        ) {
            SectionHeader(title = "Todo List")

            when (val todoItems = state.todoItems) {
                is Async.Success -> {
                    todoItems().forEach { todoItem ->
                        TodoRow(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            todoItem = todoItem,
                            onToggleComplete = { isCompleted ->
                                state.emitEvent(
                                    TodoListPreviewScreen.Event.TodoItemCheckedChanged(
                                        id = todoItem.id,
                                        isCompleted = isCompleted,
                                    )
                                )
                            },
                            maxLines = 1,
                        )
                    }
                }
                is Async.Uninitialized, is Async.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp)
                    )
                }
                is Async.Fail -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

// Previews

@Preview(name = "Todo List Preview - Loading")
@Composable
internal fun TodoListPreviewLoadingPreview() {
    Surface {
        TodoListPreviewUi().Content(
            state = TodoListPreviewScreen.State(
                todoItems = Async.Loading(),
                emitEvent = {},
            ),
            modifier = Modifier
        )
    }
}

@Preview(name = "Todo List Preview - Success")
@Composable
internal fun TodoListPreviewSuccessPreview() {
    Surface {
        TodoListPreviewUi().Content(
            state = TodoListPreviewScreen.State(
                todoItems = Async.Success(
                    listOf(
                        TodoItem(
                            id = "1",
                            text = "Buy groceries with a very very long text that should be truncated",
                            isCompleted = false
                        ),
                        TodoItem(
                            id = "2",
                            text = "Finish project documentation with all the details",
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