package nl.a3.dora.ui.screens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import nl.a3.dora.ui.component.DialogBox

@Composable
fun HelpScreen() {
    val displayDisplay = remember {
        mutableStateOf(false)
    }

    Button(onClick = {
        displayDisplay.value = true
    }) {
        Text(text = "help")
    }

    if (displayDisplay.value) {
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