package nl.a3.dora.presentation.ui.screens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import nl.a3.dora.presentation.ui.component.DialogBox

@Composable
fun HelpScreen() {
    val displayDisplay = remember {
        mutableStateOf(0)
    }

    Button(onClick = {
        displayDisplay.value = 1
    }) {
        Text(text = "help")
    }

    if (displayDisplay.value == 1) {
        DialogBox(
            showDialog = displayDisplay,
            titleText = "title",
            description = "description",
            buttons = mapOf(
                "close" to {},
            )
        )
    }
}