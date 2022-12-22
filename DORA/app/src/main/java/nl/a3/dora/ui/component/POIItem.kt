package nl.a3.dora.ui.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nl.a3.dora.R
import nl.a3.dora.model.POI
import nl.a3.dora.ui.Pages

@Composable
fun POIItem(
    poi: POI,
    isFoldedOut: Boolean,
    modifier: Modifier = Modifier,
    onFoldClick: () -> Unit,
    navController: NavController,
    currentPage: MutableState<String>
) {
    val context = LocalContext.current

    //Background Card used in both Route and POI Item
    ListItemCard(
        headerText = context.getString(
            context.resources.getIdentifier(
                poi.poiName,
                "string",
                context.packageName
            )
        ),
        isFoldedOut = isFoldedOut,
        onFoldClick = onFoldClick,
        DescriptionComp = {
            POIDescriptionItem(
                poi = poi,
                context = context,
                navController = navController,
                currentPage = currentPage
            )
        },
        modifier = modifier
    )
}

@Composable
private fun POIDescriptionItem(
    poi: POI,
    modifier: Modifier = Modifier,
    context: Context,
    navController: NavController,
    currentPage: MutableState<String>
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(Pages.Map.title + "/1/${poi.poiID}")
                currentPage.value = Pages.Map.title
            }
    ) {
        //Info and thumbnail
        Row {
            //description header and text
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.header_info),
                    style = MaterialTheme.typography.h1
                )
                //todo add descriptionText
                Text(
                    text = context.getString(
                        context.resources.getIdentifier(
                            poi.poiDescription,
                            "string",
                            context.packageName
                        )
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                //poi number
                Text(text = stringResource(R.string.poi_number) + " " + (poi.poiID?.plus(1)))
                //hasVisited
                Text(
                    text =
                    if (poi.isVisited) {
                        stringResource(R.string.has_visited_text)
                    } else {
                        stringResource(R.string.not_yet_visited_text)
                    }
                )
            }
            //thumbnail
            Image(
                modifier = Modifier
                    .size(128.dp)
                    .clip(RoundedCornerShape(25)),
                contentScale = ContentScale.Crop,
                painter = painterResource(
                    id = context.resources.getIdentifier(
                        poi.thumbnailName,
                        "drawable",
                        context.packageName
                    )
                ),
                contentDescription = context.getString(
                    context.resources.getIdentifier(
                        poi.poiDescription,
                        "string",
                        context.packageName
                    )
                )
            )
        }
        //ags logo
        Image(
            modifier = Modifier
                .size(64.dp),
            painter = painterResource(id = R.drawable.ags_logo),
            contentDescription = stringResource(R.string.ags_logo_desc)
        )
    }
}


//NON ESSENTIAL/IGNORE BELOW
//preview POIItem with one POI
//@Preview
//@Composable
//private fun PreviewPOIItem() {
//    POIItem(
//        poi = POI(
//            0,
//            R.string.truth,
//            true,
//            R.drawable.tower_of_destinity,
//            R.string.truth,
//            GeoPoint(51.5856, 4.7925)
//        ),
//        isFoldedOut = true,
//        onFoldClick = {}
//    )
//}
