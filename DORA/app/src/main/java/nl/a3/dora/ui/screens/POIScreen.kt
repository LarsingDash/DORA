package nl.a3.dora.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.navigation.NavController
import nl.a3.dora.MainActivity
import nl.a3.dora.model.POI
import nl.a3.dora.ui.component.POIItem

@Composable
fun POIScreen(
    poiID: Int?,
    navController: NavController,
    currentPage: MutableState<String>
) {
    val nullableList by remember {
        mutableStateOf(MainActivity.selectedRoute?.routeList)
    }
    val list: List<POI> = nullableList ?: listOf()
    var openedPOI: POI? by remember { mutableStateOf(null) }
    var preOpenedPoiID by remember { mutableStateOf(poiID) }

    LazyColumn {
        items(list.size) { index ->
            val poi = list[index]
            var foldout = false
            if (openedPOI != null && openedPOI == poi) foldout = true
            if (preOpenedPoiID != null && poi.poiID != null && preOpenedPoiID == poi.poiID ) foldout = true
            POIItem(
                poi = poi,
                isFoldedOut = foldout,
                onFoldClick = {
                    openedPOI = if (foldout) null else poi
                    preOpenedPoiID = null
                },
                navController = navController,
                currentPage = currentPage
            )
        }
    }
}