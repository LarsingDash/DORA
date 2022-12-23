package nl.a3.dora.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.a3.dora.R
import nl.a3.dora.ui.theme.*

@Composable
fun ListItemHeader(
    text: String,
    isFoldedOut: Boolean,
    onFoldClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val brush = if (isFoldedOut)
        Brush.horizontalGradient(
            colors = listOf(
                selectedColor1,
                selectedColor2,
            )
        ) else Brush.horizontalGradient(
        colors = listOf(
            unSelectedColor1,
            unSelectedColor2,
        )
    )

    Box(modifier = Modifier
        .clickable { onFoldClick.invoke() }
        .background(
            brush = brush
        ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = modifier
                .padding(8.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.9f),
                text = text,
                style = MaterialTheme.typography.h1.copy(
                    shadow = Shadow(
                        color = Color(15, 15, 15),
                        offset = Offset(x = 2f, y = 4f),
                        blurRadius = 0.3f
                    )
                ),
                color = Color.White,
                fontWeight = FontWeight.Black,
                fontSize = 28.sp,
                textAlign = TextAlign.Center
            )

            if (isFoldedOut) {
                Icon(
                    imageVector = Icons.Filled.ExpandLess,
                    contentDescription = stringResource(R.string.close_route_item),
                    tint = Color.White
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.ExpandMore,
                    contentDescription = stringResource(R.string.open_route_item),
                    tint = Color.White

                )
            }
        }
    }
}