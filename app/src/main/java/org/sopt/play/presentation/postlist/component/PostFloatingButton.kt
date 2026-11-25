package org.sopt.play.presentation.postlist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.core.common.extension.noRippleClickable
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

@Composable
fun PostFloatingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Icon(
        painter = painterResource(R.drawable.ic_plus),
        contentDescription = null,
        tint = PlaySoptTheme.colors.white,
        modifier = modifier
            .dropShadow(
                shape = CircleShape,
                shadow = Shadow(
                    radius = 12.dp,
                    spread = 0.dp,
                    color = Color.Black.copy(alpha = 0.15f),
                    offset = DpOffset(0.dp, 4.dp)
                ),
            )
            .clip(CircleShape)
            .background(color = PlaySoptTheme.colors.red)
            .padding(16.dp)
            .noRippleClickable(onClick = onClick)
            .size(28.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun PostFloatingButtonPreview() {
    PlaySoptTheme {
        Box(modifier = Modifier.padding(24.dp)) {
            PostFloatingButton(onClick = {})
        }
    }
}
