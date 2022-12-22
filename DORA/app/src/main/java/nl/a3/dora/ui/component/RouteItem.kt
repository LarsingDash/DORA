package nl.a3.dora.ui.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
        modifier = Modifier,
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