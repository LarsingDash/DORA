package nl.a3.dora.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.a3.dora.ui.screens.HelpScreen
import nl.a3.dora.ui.screens.HomeScreen
import nl.a3.dora.ui.screens.MapScreen
import nl.a3.dora.ui.screens.POIScreen

enum class Pages(val title: String) {
    Home(title = "home"),
    Map(title = "map"),
    POI(title = "poi"),
    Help(title = "help"),
}

@Composable
fun DORA(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            BottomBar(
                homeButtonUnit = {
                    navController.navigate(Pages.Home.title)
                },
                mapButtonUnit = {
                    navController.navigate(Pages.Map.title)
                },
                poiButtonUnit = {
                    navController.navigate(Pages.POI.title)
                },
                helpButtonUnit = {
                    navController.navigate(Pages.Help.title)
                })
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            //Home
            composable(route = Pages.Home.title) {
                HomeScreen()
            }

            //Map
            composable(route = Pages.Map.title) {
                MapScreen()
            }

            //POI
            composable(route = Pages.POI.title) {
                POIScreen()
            }

            //Help
            composable(route = Pages.Help.title) {
                HelpScreen()
            }
        }
    }
}

@Composable
fun BottomBar(
    homeButtonUnit: () -> Unit,
    mapButtonUnit: () -> Unit,
    poiButtonUnit: () -> Unit,
    helpButtonUnit: () -> Unit,
) {
    BottomAppBar(
        modifier = Modifier,
        backgroundColor = Color.LightGray
    ) {
        Row {
            //Home
            NavButton(
                buttonUnit = homeButtonUnit,
                buttonName = Pages.Home.title,
            )

            //map
            NavButton(
                buttonUnit = mapButtonUnit,
                buttonName = Pages.Map.title,
            )

            //poi
            NavButton(
                buttonUnit = poiButtonUnit,
                buttonName = Pages.POI.title,
            )

            //help
            NavButton(
                buttonUnit = helpButtonUnit,
                buttonName = Pages.Help.title,
            )
        }
    }
}

@Composable
fun NavButton(
    buttonUnit: () -> Unit,
    buttonName: String,
) {
    Button(
        onClick = buttonUnit,
        modifier = Modifier
            .padding(10.dp, 0.dp),
        colors = ButtonDefaults.buttonColors(Color.Red)
    ) {
        Text(
            text = buttonName,
            color = Color.White
        )
    }
}