package nl.a3.dora.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.a3.dora.R
import nl.a3.dora.model.POI
import nl.a3.dora.model.Route
import org.osmdroid.util.GeoPoint

@Composable
fun RouteItem(
    route: Route,
    isFoldedOut: Boolean,
    onFoldClick: () -> Unit,
    onSelectRouteClick: () -> Unit,
    onResetRouteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column (modifier = Modifier.padding(8.dp)) {
            ListItemHeader(text = route.routeName,isFoldedOut = isFoldedOut, onFoldClick = onFoldClick)
            if (isFoldedOut) {
                RouteDiscriptionItem(
                    route = route,
                    onSelectRouteClick = onSelectRouteClick,
                    onResetRouteClick = onResetRouteClick
                )
            }
        }
    }
}

@Composable
private fun RouteDiscriptionItem(
    route: Route,
    onSelectRouteClick: () -> Unit,
    onResetRouteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        //discription and picture
        Row() {
            //discription header and text
            Column (modifier = Modifier.weight(1f)) {
                Text(text = stringResource(R.string.route_info))
                Text(text = route.routeContent)
            }
            //thumbnail
            Image(
                modifier = Modifier.size(128.dp).clip(RoundedCornerShape(25)),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = route.thumbnailUri),
                contentDescription = null
            )
        }
        //distance, poi, and logo
        Row() {
            //distance and POI amount
            Column {
                Text(text = stringResource(R.string.discription_distance) + " " + route.routeLength)
                Text(text = stringResource(R.string.discription_poi_amount) + " " + route.routeList.size)
            }
            //todo add logo
        }
        //reset route and select route button
        Row(horizontalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = onSelectRouteClick) {
                Text(text = stringResource(R.string.select_button_text))
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = onResetRouteClick) {
                Text(text = stringResource(R.string.reset_button_text))
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}


//preview RouteItem with one route from DummyRepoRoute
@Preview
@Composable
private fun PreviewRouteItem() {
    RouteItem(
        route = Route(
            routeID = 0,
            routeName = "Route 1",
            listOf(
                POI(0, "Poi 0", true, R.drawable.tower_of_destinity, GeoPoint(51.5856, 4.7925)),
                POI(1, "Poi 1",false, R.drawable.breda_bieb, GeoPoint(51.58778, 4.78080)),
                POI(2, "Poi 2",false, R.drawable.breda_stadhuis_nieuw, GeoPoint(51.59461, 4.77896)),
                POI(3, "Poi 3", true, R.drawable.bocht_of_cingel, GeoPoint(51.5864, 4.7902)), //Geolocation made up
            ),
            thumbnailUri = R.drawable.tower_of_destinity,
            routeLength = 5f,
            routeContent = "This route is used for test data purposes"
        ),
        isFoldedOut = true,
        {},
        {},
        {}
    )
}