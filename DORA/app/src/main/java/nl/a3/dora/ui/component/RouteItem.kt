package nl.a3.dora.ui.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.a3.dora.R
import nl.a3.dora.model.Route
import nl.a3.dora.ui.theme.navBarColor1
import nl.a3.dora.ui.theme.navBarColor2
import nl.a3.dora.ui.theme.selectedColor1
import nl.a3.dora.ui.theme.selectedColor2

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
                Text(text = stringResource(R.string.discription_distance) + " " + route.routeLength + " km")
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
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(10.dp, 10.dp, 5.dp, 0.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(23.dp, 23.dp, 23.dp, 23.dp))
                    .fillMaxSize()
                    .clickable { onSelectRouteClick.invoke() }
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                selectedColor1,
                                selectedColor2,
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.select_button_text),
                    style = MaterialTheme.typography.h1,
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp
                )
            }

            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(5.dp, 10.dp, 10.dp, 0.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(23.dp, 23.dp, 23.dp, 23.dp))
                    .fillMaxSize()
                    .clickable { onResetRouteClick.invoke() }
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                selectedColor2,
                                selectedColor1,
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.reset_button_text),
                    style = MaterialTheme.typography.h1,
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp
                )
            }
        }
    }
}