package nl.a3.dora.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import nl.a3.dora.R

@Composable
fun ListItemHeader(
    text: String,
    isFoldedOut: Boolean,
    onFoldClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (modifier = Modifier.clickable { onFoldClick.invoke() }) {
        Text(text = text)
        Spacer(modifier = Modifier.weight(1f))
        if (isFoldedOut) {
            Icon(imageVector = Icons.Filled.ExpandLess, contentDescription = stringResource(R.string.close_route_item))
        } else {
            Icon(imageVector = Icons.Filled.ExpandMore, contentDescription = stringResource(R.string.open_route_item))
        }
    }
}