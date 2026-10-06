package org.sopt.play.core.designsystem.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val Black = Color(0xFF121212)
private val Gray1 = Color(0xFFF7F7F7)
private val Gray2 = Color(0xFFD1D5D6)
private val Gray3 = Color(0xFFB2BABD)
private val Gray5 = Color(0xFF505559)
private val Gray6 = Color(0xFF23272A)
private val Red = Color(0xFFFF4D4D)
private val White = Color(0xFFFFFFFF)

@Immutable
data class PlaySoptColors(
    val black: Color,
    val gray1: Color,
    val gray2: Color,
    val gray3: Color,
    val gray5: Color,
    val gray6: Color,
    val red: Color,
    val white: Color,
)

val defaultPlaySoptColors = PlaySoptColors(
    black = Black,
    gray1 = Gray1,
    gray2 = Gray2,
    gray3 = Gray3,
    gray5 = Gray5,
    gray6 = Gray6,
    red = Red,
    white = White,
)

val LocalPlaySoptColors = staticCompositionLocalOf { defaultPlaySoptColors }

@Preview(showBackground = true)
@Composable
private fun PlaySoptColorsPreview() {
    PlaySoptTheme {
        Column {
            listOf(
                PlaySoptTheme.colors.black,
                PlaySoptTheme.colors.gray1,
                PlaySoptTheme.colors.gray2,
                PlaySoptTheme.colors.gray3,
                PlaySoptTheme.colors.gray5,
                PlaySoptTheme.colors.gray6,
                PlaySoptTheme.colors.red,
                PlaySoptTheme.colors.white,
            ).chunked(4).forEach { rowColors ->
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    rowColors.forEach { c ->
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(c)
                                .padding(end = 8.dp),
                        )
                    }
                }
            }
        }
    }
}