package com.duchastel.simon.encoreapp.screens.todolist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.duchastel.simon.encoreapp.ui.components.SectionHeader
import com.duchastel.simon.encoreapp.ui.components.TodoRow
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

            items(state.todoItems) { todoItem ->
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
    }
}