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
            titleText = "How does the app work?",
            description = "On the bottom of the app there are four buttons. Each of these buttons " +
                    "lead to different pages. Home is the homescreen, here you can find all available " +
                    "routes to walk and how many points of interest there are on that route. On this " +
                    "page you have the ability to click on a route to get two options. The first is " +
                    "to select the route, so you can start or resume walking it and the second is to " +
                    "reset the route. The reset route option is only useful if you haven't walked the " +
                    "route before, if pressed a popup appears to confirm if you want to reset the route. " +
                    "The button labeled Map shows the directions and route of the selected route. It " +
                    "shows the POI's through a sort of arrow icon on the map. This icon can be pressed " +
                    "and if you do press it, a popup will appear. In the popup it reveals the number " +
                    "of the POI and the distance to the selected POI. It also shows a blue marked text " +
                    "that says 'POI info' and if pressed, it will take you to the POI details page with " +
                    "the details of the selected POI. When you reach a POI another popup will appear, " +
                    "congratulating you on reaching the next POI. In this popup you will have two choices, " +
                    "the first is to go to the POI details page to learn more about the POI. The second " +
                    "choice is to continue the route you are walking to the next POI. The button labeled " +
                    "POI gives an overview of all points of interest. In this screen you will have an " +
                    "overview of all the POI's on your selected route. Without touching anything in this " +
                    "screen you can see what the distance is to all POI's. If you click on a POI a " +
                    "dropdown information box will appear with more information on the selected POI. In " +
                    "this box there is general information about the POI, there is an image of the POI " +
                    "aswell as hyperlinks to video's about the POI. Finally there is a rating in stars " +
                    "about the POI.",
            buttons = mapOf(
                "close" to Pair(false) {},
            )
        )
    }

    if (displayDisplay.value == 2) {
        DialogBox(
            showDialog = displayDisplay,
            titleText = "About the app",
            description = "Deze app is gericht op mensen die de stad Breda zouden willen ontdekken." +
                    "De app is ontwikkeld voor AGS op het eerdere systeem van VVV-Breda. De makers van" +
                    "de app zijn Owen Verhoeven, Lars Villevoye, Dogukan Akyazi, Tom Martens, Max" +
                    "van gils en Martijn van der Linden.",
            buttons = mapOf(
                "close" to Pair(false) {},
            )
        )
    }
}