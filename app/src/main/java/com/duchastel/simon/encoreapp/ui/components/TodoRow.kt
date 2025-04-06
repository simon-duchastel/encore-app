package com.duchastel.simon.encoreapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.duchastel.simon.encoreapp.screens.todolist.TodoItem
import com.duchastel.simon.encoreapp.utils.runIf

/**
 * A reusable row component for displaying todo items
 *
 * @param todoItem The todo item data to display
 * @param onToggleComplete Callback when the todo completion status should be toggled
 * @param modifier Optional modifier for customizing the layout
 */
@Composable
fun TodoRow(
    todoItem: TodoItem,
    onToggleComplete: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Row(
        modifier = modifier
            .graphicsLayer {
                scaleX = if (isPressed) 0.98f else 1f
                scaleY = if (isPressed) 0.98f else 1f
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onToggleComplete(!todoItem.isCompleted) },
            ),
            verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = todoItem.isCompleted,
            onCheckedChange = null,
        )

        Text(
            text = todoItem.text,
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f),
            style = MaterialTheme.typography.bodyLarge
                .runIf(todoItem.isCompleted) {
                    copy(textDecoration = TextDecoration.LineThrough)
                }
        )
    }
}
