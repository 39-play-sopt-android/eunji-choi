package org.sopt.play.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.common.extension.noRippleClickable
import org.sopt.play.core.designsystem.component.model.PlayButtonType
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

@Composable
fun PlayButton(
    text: String,
    type: PlayButtonType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val enabled = if(type == PlayButtonType.DISABLED) false else true

    Text(
        text = text,
        color = type.textColor(),
        style = PlaySoptTheme.typography.sb14,
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = type.backgroundColor(),
                shape = CircleShape
            )
            .noRippleClickable(
                enabled = enabled,
                onClick = onClick,
            )
            .padding(all = 16.dp),
    )
}

@Preview(showBackground = true)
@Composable
private fun PlayButtonPreview() {
    PlaySoptTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PlayButton(
                text = "로그인",
                type = PlayButtonType.PRIMARY_ACTIVE,
                onClick = {},
            )

            PlayButton(
                text = "로그인",
                type = PlayButtonType.SECONDARY_ACTIVE,
                onClick = {},
            )

            PlayButton(
                text = "로그인",
                type = PlayButtonType.DISABLED,
                onClick = {},
            )
        }
    }
}
