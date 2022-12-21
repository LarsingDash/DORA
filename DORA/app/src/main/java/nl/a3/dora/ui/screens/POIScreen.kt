package nl.a3.dora.ui.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import nl.a3.dora.MainActivity
import nl.a3.dora.model.POI
import nl.a3.dora.ui.component.POIItem

@Composable
fun POIScreen(
    poiID: Int?,
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
            )
        }
    }















//    Text(text = "$poiID")
//    val poiListState = poiViewModel.typeListFlow.collectAsState(initial = listOf())

    /*LazyColumn {
        items(list.size) { index ->
            val poi = list[index]
            Text(
                text = poi.name,
                modifier = Modifier.padding(5.dp),
                lineHeight = 70.sp
            )
            Image(
                painter = painterResource(id = poi.thumbnailUri),
                contentDescription = "Cool tower"
            )
            if(poi.poiID == poiID) {
                Text(
                    text = "Je moeder ofzo: $poiID",
                    fontStyle = FontStyle.Italic,
                    lineHeight = 70.sp
                )
            }
        }
    }*/
}