package nl.a3.dora.ui.screens

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import nl.a3.dora.MainActivity
import nl.a3.dora.model.Route
import nl.a3.dora.ui.Pages
import nl.a3.dora.ui.component.DialogBox
import nl.a3.dora.ui.component.RouteItem
import nl.a3.dora.viewmodel.PoiViewModel
import nl.a3.dora.viewmodel.RouteViewModel

private var routeAwaitingReset: Route? = null

@Composable
fun HomeScreen(
    routeViewModel: RouteViewModel,
    poiViewModel: PoiViewModel,
    navController: NavHostController,
    currentPage: MutableState<String>
) {
    val routeStateList = routeViewModel.typeListFlow.collectAsState(initial = listOf())
    var openedRoute: Route? by remember { mutableStateOf(null) }
    val showDialog = remember { mutableStateOf(0) }

    LazyColumn {

        items(routeStateList.value.size) { index ->
            val route = routeStateList.value[index]
            var foldout = false
            if (openedRoute != null && openedRoute == route) {
                foldout = true
            }
            RouteItem(
                route = route,
                isFoldedOut = foldout,
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
                    navController.navigate(Pages.Map.title + "/1")
                },
                onResetRouteClick = {
                    showDialog.value = 1
                    routeAwaitingReset = route
                }
            )
        }
    }

    //DialogBox
    if (showDialog.value == 1) {
        DialogBox(
            showDialog = showDialog,
            titleText = "cirgen",
            description = "sinjor",
            buttons = mapOf(
                "No" to Pair(true) {},
                "Yes" to Pair(
                    false
                ) {
                    Log.d("DEBUG ENTRY", "Resetting ROUTE")
                    resetRoute(poiViewModel)
                }
            )
        )
    }
}

private fun resetRoute(poiViewModel: PoiViewModel) {
    Log.d("POI data", "${routeAwaitingReset?.routeList}")
    routeAwaitingReset?.routeList?.forEach {
        val poi = it.copy(isVisited = false)
        Log.d("POI data", "$poi")
        poiViewModel.updateType(poi)
    }
    routeAwaitingReset = null
}