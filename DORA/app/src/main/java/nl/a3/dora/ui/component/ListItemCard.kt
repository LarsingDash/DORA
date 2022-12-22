package nl.a3.dora.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ListItemCard(
    headerText: String,
    isFoldedOut: Boolean,
    onFoldClick: () -> Unit,
    DescriptionComp: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp, 23.dp, 8.dp, 8.dp))
    ) {
        //column holding header and info, with animation
        Column(
            Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .background(Color.White)
        ) {
            ListItemHeader(
                text = headerText,
                isFoldedOut = isFoldedOut,
                onFoldClick = onFoldClick,
                modifier = Modifier.height(130.dp)
            )
            if (isFoldedOut) {
                DescriptionComp.invoke()
            }
        }
    }
}