package org.sopt.play.core.designsystem.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.core.common.extension.noRippleClickable
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

@Composable
fun Chip(
    category: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val textColor = if (isSelected) PlaySoptTheme.colors.white
    else PlaySoptTheme.colors.gray5

    val backgroundColor = if (isSelected) PlaySoptTheme.colors.gray5
    else PlaySoptTheme.colors.white

    val borderColor = if (isSelected) PlaySoptTheme.colors.gray5
    else PlaySoptTheme.colors.gray2

    val shape = RoundedCornerShape(6.dp)

    Text(
        text = category,
        style = PlaySoptTheme.typography.m14,
        color = textColor,
        modifier = modifier
            .noRippleClickable(onClick = onClick)
            .background(
                color = backgroundColor,
                shape = shape
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun ChipPreview() {
    PlaySoptTheme {
        Row(
            modifier = Modifier
                .padding(all = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Chip(
                category = stringResource(R.string.home_category_whole),
                isSelected = true,
                onClick = {},
            )

            Chip(
                category = stringResource(R.string.home_category_shoes),
                isSelected = false,
                onClick = {},
            )
        }
    }
}
