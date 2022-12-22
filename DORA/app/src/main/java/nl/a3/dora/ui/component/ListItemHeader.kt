package nl.a3.dora.ui.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.a3.dora.R

@Composable
fun ListItemHeader(
    text: String,
    isFoldedOut: Boolean,
    onFoldClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.LightGray
) {
    val animatedColor by animateColorAsState(
        targetValue = if (isFoldedOut) Color.Blue else color //todo change selectedcolor to a nicer one
    )
    Surface(
        modifier = Modifier.clickable { onFoldClick.invoke() },
    color = animatedColor) {
        Row (modifier = modifier.padding(8.dp)) {
            Text(
                text = text,
                style = MaterialTheme.typography.h1,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            if (isFoldedOut) {
                Icon(imageVector = Icons.Filled.ExpandLess, contentDescription = stringResource(R.string.close_route_item))
            } else {
                Icon(imageVector = Icons.Filled.ExpandMore, contentDescription = stringResource(R.string.open_route_item))
            }
        }
    }
}