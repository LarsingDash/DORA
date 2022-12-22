package nl.a3.dora.ui.screens

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import nl.a3.dora.MainActivity
import nl.a3.dora.R
import nl.a3.dora.model.POI
import nl.a3.dora.model.Route
import nl.a3.dora.ui.Pages
import nl.a3.dora.ui.component.DialogBox
import nl.a3.dora.ui.component.RouteItem
import nl.a3.dora.viewmodel.PoiViewModel
import nl.a3.dora.viewmodel.RouteViewModel
import org.osmdroid.util.GeoPoint

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
                    openedRoute = if (foldout) null else route
                },
                onSelectRouteClick = {
                    MainActivity.selectedRoute = route
                    MainActivity.lastUserLocation = GeoPoint(0.0,0.0)
                    navController.navigate(Pages.Map.title + "/1/-1")
                    currentPage.value = Pages.Map.title
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
            titleText = stringResource(R.string.reset_route),
            description = stringResource(R.string.are_you_sure_text),
            buttons = mapOf(
                stringResource(R.string.no) to Pair(true) {},
                stringResource(R.string.yes) to Pair(
                    false
                ) {
                    Log.d("DEBUG ENTRY", "Resetting ROUTE")
                    resetRoute(routeViewModel)
                }
            )
        )
    }
}

private fun resetRoute(routeViewModel: RouteViewModel) {
    val newRouteList = mutableListOf<POI>()
    routeAwaitingReset?.routeList?.forEach {
        val newPOI = it.copy(isVisited = false)
        newRouteList.add(newPOI)
    }

    val route = routeAwaitingReset?.copy(routeList = newRouteList)
    if (route != null) {
        routeViewModel.updateType(route)
        MainActivity.selectedRoute = route
    }

    Log.d("POI data", "${route?.routeList}")

    routeAwaitingReset = null
}