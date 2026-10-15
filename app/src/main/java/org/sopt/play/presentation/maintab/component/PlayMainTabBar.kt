package org.sopt.play.presentation.maintab.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.common.extension.noRippleClickable
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.maintab.MainTab

@Composable
fun PlayMainTabBar(
    currentTab: MainTab,
    onTabClick: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = PlaySoptTheme.colors.gray2,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = PlaySoptTheme.colors.white)
                .navigationBarsPadding(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MainTab.entries.forEach { tab ->
                PlayMainTabBarItem(
                    tab = tab,
                    isSelected = tab == currentTab,
                    onClick = { onTabClick(tab) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun PlayMainTabBarItem(
    tab: MainTab,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentColor = if (isSelected) PlaySoptTheme.colors.black else PlaySoptTheme.colors.gray5
    val icon = if (isSelected) tab.selectedIcon else tab.unselectedIcon

    Column(
        modifier = modifier
            .noRippleClickable(onClick = onClick)
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(24.dp),
        )

        Text(
            text = stringResource(tab.label),
            color = contentColor,
            style = PlaySoptTheme.typography.sb14,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayMainTabBarPreview() {
    PlaySoptTheme {
        Column(
            modifier = Modifier
                .background(color = PlaySoptTheme.colors.black)
                .padding(all = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            PlayMainTabBar(
                currentTab = MainTab.HOME,
                onTabClick = {},
            )

            PlayMainTabBar(
                currentTab = MainTab.LIKE,
                onTabClick = {},
            )

            PlayMainTabBar(
                currentTab = MainTab.MYPAGE,
                onTabClick = {},
            )
        }
    }
}
