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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import nl.a3.dora.R
import nl.a3.dora.ui.component.DialogBox

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
            painter = painterResource(id = R.drawable.ags_logo),
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
            titleText = stringResource(id = R.string.help_button_name),
            description = stringResource(id = R.string.how_does_app_work_description),
            buttons = mapOf(
                "close" to Pair(false) {},
            )
        )
    }

    if (displayDisplay.value == 2) {
        DialogBox(
            showDialog = displayDisplay,
            titleText = stringResource(id = R.string.about_button_name),
            description = stringResource(id = R.string.about_the_app_description),
            buttons = mapOf(
                "close" to Pair(false) {},
            )
        )
    }
}