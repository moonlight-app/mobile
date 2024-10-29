package ru.moonlight.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.moonlight.ui.R

internal val golosFont = FontFamily(
    Font(R.font.golos_text_regular, FontWeight.Normal),
    Font(R.font.golos_text_medium, FontWeight.Medium),
    Font(R.font.golos_text_semi_bold, FontWeight.SemiBold),
    Font(R.font.golos_text_bold, FontWeight.Bold),
    Font(R.font.golos_text_extra_bold, FontWeight.ExtraBold),
    Font(R.font.golos_text_black, FontWeight.Black),
)

val titleTextStyle = TextStyle(
    fontFamily = golosFont,
    fontWeight = FontWeight.SemiBold,
    fontSize = 30.sp,
    lineHeight = 20.sp,
)

val secondTitleTextStyle = TextStyle(
    fontFamily = golosFont,
    fontWeight = FontWeight.SemiBold,
    fontSize = 30.sp,
    lineHeight = 36.sp,
)

val textFieldTextStyle = TextStyle(
    fontFamily = golosFont,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 20.sp,
)

val buttonTextStyle = TextStyle(
    fontFamily = golosFont,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 20.sp,
)

val smallButtonTextStyle = TextStyle(
    fontFamily = golosFont,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 20.sp,
)

val descriptionTextStyle = TextStyle(
    fontFamily = golosFont,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    lineHeight = 20.sp,
)