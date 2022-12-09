package nl.a3.dora.ui.screens

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import nl.a3.dora.ui.component.DialogBox

@Composable
fun HomeScreen() {
    val showDialog = remember {
        mutableStateOf(false)
    }

    Button(onClick = {
        showDialog.value = true
    }) {
        Text(text = "Dialog je moeder")
    }

    if (showDialog.value) {
        DialogBox(
            showDialog,
            "TestTitle",
            "Juuuuuuuuuuuuuuuuuuuuuuuuu kanker moeder",
            mapOf(
                "No" to { println("no") },
                "Maybe" to { println("maybe") },
                "Yes" to { println("yes") }
            )
        )
    }
}