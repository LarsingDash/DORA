package nl.a3.dora.ui

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.a3.dora.R
import nl.a3.dora.model.POI
import nl.a3.dora.ui.screens.HelpScreen
import nl.a3.dora.ui.screens.HomeScreen
import nl.a3.dora.ui.screens.MapScreen
import nl.a3.dora.ui.screens.POIScreen
import nl.a3.dora.viewmodel.PoiViewModel
import nl.a3.dora.viewmodel.RouteViewModel
import org.osmdroid.util.GeoPoint
import nl.a3.dora.ui.theme.*

enum class Pages(val title: String) {
    Home(title = "HOME"),
    Map(title = "MAP"),
    POI(title = "POI"),
    Help(title = "HELP"),
}

private lateinit var currentPage: MutableState<String>

@Composable
fun DORA(
    poiViewModel: PoiViewModel,
    routeViewModel: RouteViewModel
) {
    val navController: NavHostController = rememberNavController()

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
            startDestination = Pages.Home.title,
            modifier = Modifier.padding(paddingValues)
        ) {
            //Home
            composable(route = Pages.Home.title) {
                HomeScreen(routeViewModel)
            }

            //Map
            composable(route = Pages.Map.title) {
                MapScreen()
            }

            //POI
            composable(route = Pages.POI.title) {
                POIScreen(poiViewModel)
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
        modifier = Modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        navBarColor1,
                        navBarColor2,
                    )
                )
            ),
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
    ) {
        Row {
            //Home
            BottomNavigationItem(
                label = {
                    Text(
                        text = Pages.Home.title,
                        style = MaterialTheme.typography.body1,
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = Pages.Home.title,
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 3.dp),
                    )
                },
                selected = currentPage.value == Pages.Home.title,
                onClick = homeButtonUnit,
                selectedContentColor = iconSelected,
                unselectedContentColor = iconUnselected,
            )

            //Map
            BottomNavigationItem(
                label = {
                    Text(
                        text = Pages.Map.title,
                        style = MaterialTheme.typography.body1,
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = Pages.Map.title,
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 3.dp),
                        )
                },
                selected = currentPage.value == Pages.Map.title,
                onClick = mapButtonUnit,
                selectedContentColor = iconSelected,
                unselectedContentColor = iconUnselected,
            )

            //POI
            BottomNavigationItem(
                label = {
                    Text(
                        text = Pages.POI.title,
                        style = MaterialTheme.typography.body1,
                    )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = Pages.POI.title,
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 3.dp),
                        )
                },
                selected = currentPage.value == Pages.POI.title,
                onClick = poiButtonUnit,
                selectedContentColor = iconSelected,
                unselectedContentColor = iconUnselected,
            )

            //INFO
            BottomNavigationItem(
                label = {
                    Text(
                        text = Pages.Help.title,
                        style = MaterialTheme.typography.body1,
                        )
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = Pages.Help.title,
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 3.dp),
                        )
                },
                selected = currentPage.value == Pages.Help.title,
                onClick = helpButtonUnit,
                selectedContentColor = iconSelected,
                unselectedContentColor = iconUnselected,
            )
        }
    }
}