package nl.a3.dora.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

private lateinit var currentPage : MutableState<String>

@Composable
fun DORA(
    navController: NavHostController = rememberNavController()
) {
    currentPage = remember {
        mutableStateOf(Pages.Home.title)
    }

    Scaffold(
        bottomBar = {
            BottomBar(
                homeButtonUnit = {
                    navController.navigate(Pages.Home.title)
                    currentPage.value = Pages.Home.title
                },
                mapButtonUnit = {
                    navController.navigate(Pages.Map.title)
                    currentPage.value = Pages.Map.title
                },
                poiButtonUnit = {
                    navController.navigate(Pages.POI.title)
                    currentPage.value = Pages.POI.title
                },
                helpButtonUnit = {
                    navController.navigate(Pages.Help.title)
                    currentPage.value = Pages.Help.title
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
            BottomNavigationItem(
                label = {
                    Text(
                        text = Pages.Home.title,
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = Pages.Home.title,
                    )
                },
                selected = currentPage.value == Pages.Home.title,
                onClick = homeButtonUnit,
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Black,
            )

            //Map
            BottomNavigationItem(
                label = {
                    Text(
                        text = Pages.Map.title,
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = Pages.Map.title,
                    )
                },
                selected = currentPage.value == Pages.Map.title,
                onClick = mapButtonUnit,
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Black,
            )

            //POI
            BottomNavigationItem(
                label = {
                    Text(
                        text = Pages.POI.title,
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = Pages.POI.title,
                    )
                },
                selected = currentPage.value == Pages.POI.title,
                onClick = poiButtonUnit,
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Black,
            )

            //POI
            BottomNavigationItem(
                label = {
                    Text(
                        text = Pages.Help.title,
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = Pages.Help.title,
                    )
                },
                selected = currentPage.value == Pages.Help.title,
                onClick = helpButtonUnit,
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Black,
            )
        }
    }
}