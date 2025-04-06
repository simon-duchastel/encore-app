package com.duchastel.simon.encoreapp.screens.todolist

import com.duchastel.simon.encoreapp.data.TodoItemRepository
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

class TodoListPresenterTest {

    private val todoItemRepository: TodoItemRepository = mockk(relaxed = true)

    private lateinit var presenter: TodoListPresenter

    @Before
    fun setup() {
        presenter = TodoListPresenter(todoItemRepository)
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
            assertTrue(awaitItem().todoItems is Async.Loading)
            val loadedState = awaitItem()
            assertTrue(loadedState.todoItems is Async.Success)
            assertEquals(todoItems, (loadedState.todoItems as Async.Success)())
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
                TodoListScreen.Event.TodoItemCheckedChanged(id = "1", isCompleted = true)
            )
            verify { todoItemRepository.updateTodoItem("1", true) }
        }
    }
}