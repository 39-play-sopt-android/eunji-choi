package org.sopt.play.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object PlaySoptTheme {
    val colors: PlaySoptColors
        @Composable
        @ReadOnlyComposable
        get() = LocalPlaySoptColors.current
    val typography: PlaySoptTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalPlaySoptTypography.current
}

@Composable
fun ProvidePlaySoptColorsAndTypography(
    colors: PlaySoptColors,
    typography: PlaySoptTypography,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalPlaySoptColors provides colors,
        LocalPlaySoptTypography provides typography,
        content = content,
    )
}

@Composable
fun PlaySoptTheme(
    content: @Composable () -> Unit,
) {
    ProvidePlaySoptColorsAndTypography(
        colors = defaultPlaySoptColors,
        typography = defaultPlaySoptTypography,
    ) {
        MaterialTheme(
            content = content,
        )
    }
}