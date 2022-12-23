package nl.a3.dora.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import nl.a3.dora.R
import nl.a3.dora.ui.component.DialogBox
import nl.a3.dora.ui.theme.unSelectedColor1
import nl.a3.dora.ui.theme.unSelectedColor2

@Composable
fun HelpScreen() {
    val displayDisplay = remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(240, 240, 240)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.ags_logo),
            contentDescription = "AGS Logo",
        )

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(10.dp, 0.dp, 5.dp, 0.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(8.dp, 23.dp, 8.dp, 8.dp))
                    .fillMaxSize()
                    .clickable { displayDisplay.value = 1 }
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                unSelectedColor1,
                                unSelectedColor2,
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Help",
                    style = MaterialTheme.typography.h1,
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 28.sp
                )
            }

            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .padding(5.dp, 0.dp, 10.dp, 0.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(23.dp, 8.dp, 8.dp, 8.dp))
                    .fillMaxSize()
                    .clickable { displayDisplay.value = 2 }
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                unSelectedColor2,
                                unSelectedColor1,
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "About",
                    style = MaterialTheme.typography.h1,
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    fontSize = 28.sp
                )
            }
        }
    }

    if (displayDisplay.value == 1) {
        DialogBox(
            showDialog = displayDisplay,
            titleText = stringResource(id = R.string.help_button_name),
            description = stringResource(id = R.string.how_does_app_work_description),
            buttons = mapOf(
                "close" to Pair(true) {},
            )
        )
    }

    if (displayDisplay.value == 2) {
        DialogBox(
            showDialog = displayDisplay,
            titleText = stringResource(id = R.string.about_button_name),
            description = stringResource(id = R.string.about_the_app_description),
            buttons = mapOf(
                "close" to Pair(true) {},
            )
        )
    }
}