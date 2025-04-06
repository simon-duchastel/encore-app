package com.duchastel.simon.encoreapp.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.duchastel.simon.encoreapp.screens.todolist.TodoListScreen
import com.duchastel.simon.encoreapp.screens.todolistpreview.TodoListPreviewScreen
import com.duchastel.simon.encoreapp.ui.components.SectionHeader
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.foundation.CircuitContent
import com.slack.circuit.runtime.ui.Ui
import dagger.hilt.components.SingletonComponent

@CircuitInject(HomeScreen::class, SingletonComponent::class)
class HomeScreenUi : Ui<HomeScreen.State> {

    @Composable
    override fun Content(state: HomeScreen.State, modifier: Modifier) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            item {
                SectionHeader(title = "Home")
            }
            item {
                CircuitContent(TodoListPreviewScreen, modifier = Modifier)
            }
            item {
                CircuitContent(TodoListPreviewScreen, modifier = Modifier)
            }
            item {
                CircuitContent(TodoListPreviewScreen, modifier = Modifier)
            }
            item {
                CircuitContent(TodoListPreviewScreen, modifier = Modifier)
            }
            item {
                CircuitContent(TodoListPreviewScreen, modifier = Modifier)
            }
            item {
                CircuitContent(TodoListPreviewScreen, modifier = Modifier)
            }
        }
    }
}