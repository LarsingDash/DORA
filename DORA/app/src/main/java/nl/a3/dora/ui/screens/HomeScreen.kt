package nl.a3.dora.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import nl.a3.dora.MainActivity
import nl.a3.dora.model.Route
import nl.a3.dora.ui.Pages
import nl.a3.dora.ui.component.RouteItem
import nl.a3.dora.viewmodel.RouteViewModel


@Composable
fun HomeScreen(
    routeViewModel: RouteViewModel,
    navController: NavHostController,
    currentPage: MutableState<String>
) {
    val routeStateList = routeViewModel.typeListFlow.collectAsState(initial = listOf())
    var openedRoute: Route? by remember { mutableStateOf(null) }

    LazyColumn {

        items(routeStateList.value.size) { index ->
            val route = routeStateList.value[index]
            var foldout = false
            if (openedRoute != null && openedRoute == route) {
                foldout = true
            }
            RouteItem(
                route = route,
                isFoldedOut = foldout, //todo = foldvariable
                onFoldClick = {
                    if (foldout) {
                        openedRoute = null
                    } else {
                        openedRoute = route
                    }
                },
                onSelectRouteClick = {
                    MainActivity.selectedRoute = route
                    currentPage.value = Pages.Map.title
                    navController.navigate(Pages.Map.title)
                },
                onResetRouteClick = {/*todo = deselectroute via dialogbox*/ }
            )
        }
    }


    //TestCode DialogBox en button
    /*
    val showDialog = remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            showDialog.value = 1
        }) {
            Text(text = "Popup Testbutton")
        }
    }
    */

    /*
    if (showDialog.value == 1) {
        DialogBox(
            showDialog,
            "TestTitle",
            "Test\nDescription\nDescription\nDescription\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "Description\n" +
                    "last",
            mapOf(
//                "No" to { println("no") },
//                "Maybe" to { println("maybe") },
                "Yes" to Pair(false, { println("yes") })
            )
        )
    }
    */
}