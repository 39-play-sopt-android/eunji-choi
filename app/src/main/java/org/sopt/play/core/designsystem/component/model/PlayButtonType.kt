package org.sopt.play.core.designsystem.component.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

enum class PlayButtonType(
    val textColor: @Composable () -> Color,
    val backgroundColor: @Composable () -> Color,
) {
    PRIMARY_ACTIVE(
        textColor = { PlaySoptTheme.colors.gray1 },
        backgroundColor = { PlaySoptTheme.colors.black },
    ),
    SECONDARY_ACTIVE(
        textColor = { PlaySoptTheme.colors.gray5 },
        backgroundColor = { PlaySoptTheme.colors.gray1 },
    ),
    DISABLED(
        textColor = { PlaySoptTheme.colors.gray3 },
        backgroundColor = { PlaySoptTheme.colors.gray1 },
    )
}
