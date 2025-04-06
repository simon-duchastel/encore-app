package com.duchastel.simon.encoreapp.screens.todolistpreview

import com.duchastel.simon.encoreapp.data.TodoItemRepository
import com.duchastel.simon.encoreapp.screens.todolist.TodoItem
import com.duchastel.simon.encoreapp.utils.Async
import com.slack.circuit.test.test
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TodoListPreviewPresenterTest {

    private val todoItemRepository: TodoItemRepository = mockk(relaxed = true)

    private lateinit var presenter: TodoListPreviewPresenter

    @Before
    fun setup() {
        presenter = TodoListPreviewPresenter(todoItemRepository)
    }

    @Test
    fun `todo items starts uninitialized`() = runTest {
        every { todoItemRepository.getTodoItems() } returns flowOf()

        presenter.test {
            val state = awaitItem()
            assertTrue(state.todoItems is Async.Loading)
        }
    }

    @Test
    fun stateShowsTodoItemsWhenDataIsAvailable() = runTest {
        val todoItems = listOf(
            TodoItem("1", "Test Task 1", false),
            TodoItem("2", "Test Task 2", true)
        )
        every { todoItemRepository.getTodoItems() } returns flowOf(todoItems)

        presenter.test {
            val state = awaitItem()
            assertTrue(state.todoItems is Async.Success)
            assertEquals(todoItems, (state.todoItems as Async.Success)())
        }
    }

    @Test
    fun `on todo item check changed, update item in repository`() = runTest {
        val todoItems = listOf(
            TodoItem("1", "Test Task 1", false),
            TodoItem("2", "Test Task 2", true)
        )
        every { todoItemRepository.getTodoItems() } returns flowOf(todoItems)

        presenter.test {
            awaitItem() // initial state from load
            val state = awaitItem() // repository update
            state.emitEvent(
                TodoListPreviewScreen.Event.TodoItemCheckedChanged(id = "1", isCompleted = true)
            )
            verify { todoItemRepository.updateTodoItem("1", true) }
        }
    }
}