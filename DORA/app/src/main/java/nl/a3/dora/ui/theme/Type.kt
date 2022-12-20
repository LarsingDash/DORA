package nl.a3.dora.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import nl.a3.dora.R
import nl.a3.dora.R.font.*

// Set of Material typography styles to start with
val Montserrat = FontFamily(
    Font(montserrat_black),
    Font(montserrat_blackitalic),
    Font(montserrat_bold),
    Font(montserrat_bolditalic),
    Font(montserrat_extrabold),
    Font(montserrat_extrabolditalic),
    Font(montserrat_extralight),
    Font(montserrat_extralightitalic),
    Font(montserrat_italic),
    Font(montserrat_light),
    Font(montserrat_lightitalic),
    Font(montserrat_medium, FontWeight.Medium),
    Font(montserrat_mediumitalic),
    Font(montserrat_regular, FontWeight.Normal),
    Font(montserrat_semibold),
    Font(montserrat_semibolditalic),
    Font(montserrat_thin, FontWeight.Thin),
    Font(montserrat_thinitalic),
)

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp
    ),
    body1 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)