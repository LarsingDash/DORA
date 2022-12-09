package nl.a3.dora.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.res.painterResource
import nl.a3.dora.R
import nl.a3.dora.ui.component.DialogBox
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun HelpScreen() {
    val displayDisplay = remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = {
            displayDisplay.value = 1
            println(displayDisplay.value)
        }) {
            Text(text = "Help")
        }

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "AGS Logo"
        )

        Button(onClick = {
            displayDisplay.value = 2
            println(displayDisplay.value)
        }) {
            Text(text = "About")
        }
    }

    if (displayDisplay.value == 1) {
        DialogBox(
            showDialog = displayDisplay,
            titleText = "How does the app work?",
            description = "description",
            buttons = mapOf(
                "close" to {},
            )
        )
    }

    if (displayDisplay.value == 2) {
        DialogBox(
            showDialog = displayDisplay,
            titleText = "About the app",
            description = "About",
            buttons = mapOf(
                "close" to {},
            )
        )
    }
}