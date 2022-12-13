package nl.a3.dora.presentation.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.a3.dora.presentation.ui.screens.HelpScreen
import nl.a3.dora.presentation.ui.screens.HomeScreen
import nl.a3.dora.presentation.ui.screens.MapScreen
import nl.a3.dora.presentation.ui.screens.POIScreen
import nl.a3.dora.viewmodel.PoiViewModel

enum class Pages(val title: String) {
    Home(title = "home"),
    Map(title = "map"),
    POI(title = "poi"),
    Help(title = "help"),
}

@Composable
fun DORA(
    poiViewModel: PoiViewModel
) {
    val navController: NavHostController = rememberNavController()
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
                HomeScreen(poiViewModel)
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
        Row(modifier = Modifier.fillMaxWidth()) {
            //Home
            NavButton(
                buttonUnit = homeButtonUnit,
                buttonName = Pages.Home.title,
                modifier = Modifier.weight(0.25f),
            )

            //map
            NavButton(
                buttonUnit = mapButtonUnit,
                buttonName = Pages.Map.title,
                modifier = Modifier.weight(0.25f),
            )

            //poi
            NavButton(
                buttonUnit = poiButtonUnit,
                buttonName = Pages.POI.title,
                modifier = Modifier.weight(0.25f),
            )

            //help
            NavButton(
                buttonUnit = helpButtonUnit,
                buttonName = Pages.Help.title,
                modifier = Modifier.weight(0.25f),
            )
        }
    }
}

@Composable
fun NavButton(
    modifier: Modifier,
    buttonUnit: () -> Unit,
    buttonName: String,
) {
    Button(
        onClick = buttonUnit,
        modifier = modifier.fillMaxHeight(),
        colors = ButtonDefaults.buttonColors(Color.Red),
        shape = MaterialTheme.shapes.large,
    ) {
        Text(
            text = buttonName,
            color = Color.White
        )
    }
}