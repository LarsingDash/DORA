package nl.a3.dora.ui.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.a3.dora.R
import nl.a3.dora.model.Route

@Composable
fun RouteItem(
    route: Route,
    isFoldedOut: Boolean,
    onFoldClick: () -> Unit,
    onSelectRouteClick: () -> Unit,
    onResetRouteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    //Background Card used in both Route and POI Item
    ListItemCard(
        headerText = context.getString(context.resources.getIdentifier(route.routeName, "string", context.packageName)),
        isFoldedOut = isFoldedOut,
        onFoldClick = onFoldClick,
        DescriptionComp = {
            RouteDescriptionItem(
                route = route,
                context = context,
                onSelectRouteClick = onSelectRouteClick,
                onResetRouteClick = onResetRouteClick
            )
        },
        modifier = modifier
    )
}

@Composable
private fun RouteDescriptionItem(
    route: Route,
    context: Context,
    onSelectRouteClick: () -> Unit,
    onResetRouteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        //description and picture
        Row {
            //description header and text
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.header_info),
                    style = MaterialTheme.typography.h1
                )

                Text(text = context.getString(context.resources.getIdentifier(route.routeDescription, "string", context.packageName)))
            }
            //thumbnail
            Image(
                modifier = Modifier
                    .size(128.dp)
                    .clip(RoundedCornerShape(25)),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = context.resources.getIdentifier(route.thumbnailUri, "drawable", context.packageName)),
                contentDescription = stringResource(R.string.route_thumb_desc)
            )
        }
        //distance, poi, and logo
        Row {
            //distance and POI amount
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(R.string.discription_distance) + " " + route.routeLength + "km")
                Text(text = stringResource(R.string.discription_poi_amount) + " " + route.routeList.size)
            }
            //ags logo
            Image(
                modifier = Modifier
                    .size(64.dp),
                painter = painterResource(id = R.drawable.ags_logo),
                contentDescription = stringResource(R.string.ags_logo_desc)
            )
        }
        //reset route and select route button. spacers to get buttons at the center
        Row(horizontalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.weight(1f))
            //selectButton
            Button(onClick = onSelectRouteClick) {
                Text(text = stringResource(R.string.select_button_text))
            }
            Spacer(modifier = Modifier.weight(1f))
            //resetButton
            Button(onClick = onResetRouteClick) {
                Text(text = stringResource(R.string.reset_button_text))
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}


//NON ESSENTIAL/IGNORE BELOW
//preview RouteItem with one route from DummyRepoRoute
//@Preview
//@Composable
//private fun PreviewRouteItem() {
//    RouteItem(
//        route = Route(
//            routeID = 0,
//            routeName = R.string.truth,
//            listOf(
//                POI(poiID= 0, poiName=   R.string.poi_test, isVisited= true, thumbnailUri= R.drawable.tower_of_destinity, poiDescription = R.string.truth, poiLocation= GeoPoint(51.5856, 4.7925)),
//                POI(poiID= 1, poiName=R.string.poi_test, isVisited=false, thumbnailUri= R.drawable.breda_bieb, poiDescription = R.string.truth, poiLocation= GeoPoint(51.58778, 4.78080)),
//                POI(poiID=2, poiName=R.string.poi_test, isVisited=false,  thumbnailUri= R.drawable.breda_stadhuis_nieuw, poiDescription = R.string.truth, poiLocation= GeoPoint(51.59461, 4.77896)),
//                POI(poiID=3, poiName=R.string.poi_test, isVisited=true, thumbnailUri= R.drawable.bocht_of_cingel, poiDescription = R.string.truth, poiLocation= GeoPoint(51.5864, 4.7902)), //Geolocation made up
//            ),
//            thumbnailUri = R.drawable.tower_of_destinity,
//            routeDescription = R.string.truth,
//            routeLength = 5f
//        ),
//        isFoldedOut = true,
//        {},
//        {},
//        {}
//    )
//}