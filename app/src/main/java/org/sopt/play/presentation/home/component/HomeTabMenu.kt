package org.sopt.play.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.core.common.extension.noRippleClickable
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

@Composable
fun HomeTabMenu(
    menu: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val textColor = if (isSelected) PlaySoptTheme.colors.black
    else PlaySoptTheme.colors.gray3

    val dotColor = if (isSelected) PlaySoptTheme.colors.black
    else Color.Transparent

    Column(
        modifier = modifier
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(4.dp)
                .background(
                    color = dotColor,
                    shape = CircleShape
                ),
        )

        Text(
            text = menu,
            style = PlaySoptTheme.typography.m18,
            color = textColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeTabMenuPreview() {
    PlaySoptTheme {
        Row(
            modifier = Modifier
                .padding(all = 20.dp)
        ) {
            HomeTabMenu(
                menu = stringResource(R.string.home_menu_home),
                isSelected = true,
                onClick = {}
            )

            HomeTabMenu(
                menu = stringResource(R.string.home_menu_brand),
                isSelected = false,
                onClick = {}
            )
        }
    }
}
