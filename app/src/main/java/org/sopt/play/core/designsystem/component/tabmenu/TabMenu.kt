package org.sopt.play.core.designsystem.component.tabmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.core.common.extension.noRippleClickable
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

@Composable
fun TabMenu(
    menu: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val contentColor = if (isSelected) PlaySoptTheme.colors.black
    else PlaySoptTheme.colors.gray3

    Column(
        modifier = modifier
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .background(
                        color = PlaySoptTheme.colors.black,
                        shape = CircleShape
                    ),
            )

            Spacer(modifier = Modifier.height(4.dp))
        }
        else {
            Spacer(modifier = Modifier.height(8.dp))
        }

        Text(
            text = menu,
            style = PlaySoptTheme.typography.m18,
            color = contentColor
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TabMenuPreview() {
    PlaySoptTheme {
        Row(
            modifier = Modifier
                .padding(all = 20.dp)
        ) {
            TabMenu(
                menu = stringResource(R.string.home_menu_home),
                isSelected = true,
                onClick = {}
            )

            TabMenu(
                menu = stringResource(R.string.home_menu_brand),
                isSelected = false,
                onClick = {}
            )
        }
    }
}
