package org.sopt.play.core.designsystem.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import org.sopt.play.R

@Immutable
data class PlaySoptTypography(
    val b28: TextStyle,
    val m18: TextStyle,
    val sb16: TextStyle,
    val m14: TextStyle,
    val sb14: TextStyle,
)

private val playSoptFontFamily = FontFamily(
    Font(R.font.pretendard_bold, weight = FontWeight.Bold),
    Font(R.font.pretendard_semibold, weight = FontWeight.SemiBold),
    Font(R.font.pretendard_medium, weight = FontWeight.Medium),
)


val defaultPlaySoptTypography = PlaySoptTypography(
    b28 = TextStyle(
        fontFamily = playSoptFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 1.2.em,
        letterSpacing = (-0.01).em
    ),

    m18 = TextStyle(
        fontFamily = playSoptFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 1.2.em,
        letterSpacing = (-0.01).em
    ),

    sb16 = TextStyle(
        fontFamily = playSoptFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 1.2.em,
        letterSpacing = (-0.01).em
    ),

    m14 = TextStyle(
        fontFamily = playSoptFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 1.2.em,
        letterSpacing = (-0.01).em
    ),

    sb14 = TextStyle(
        fontFamily = playSoptFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 1.2.em,
        letterSpacing = (-0.01).em
    ),
)

val LocalPlaySoptTypography = staticCompositionLocalOf { defaultPlaySoptTypography }

@Preview(showBackground = true)
@Composable
private fun PlaySoptTypographyPreview() {
    PlaySoptTheme {
        Column(modifier = Modifier.padding(8.dp)) {
            Text("b28", style = PlaySoptTheme.typography.b28)
            Text("m18", style = PlaySoptTheme.typography.m18)
            Text("sb16", style = PlaySoptTheme.typography.sb16)
            Text("m14", style = PlaySoptTheme.typography.m14)
            Text("sb14", style = PlaySoptTheme.typography.sb14)
        }
    }
}