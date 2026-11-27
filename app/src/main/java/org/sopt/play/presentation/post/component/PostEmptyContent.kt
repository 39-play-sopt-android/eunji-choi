package org.sopt.play.presentation.post.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

@Composable
fun PostEmptyContent(
    @StringRes messageRes: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(messageRes),
            style = PlaySoptTheme.typography.m18,
            color = PlaySoptTheme.colors.gray3,
            textAlign = TextAlign.Center,
        )
    }
}
