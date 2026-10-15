package org.sopt.play.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.sopt.play.presentation.home.HomeRoute
import org.sopt.play.presentation.like.LikeRoute
import org.sopt.play.presentation.main.component.PlayMainTabBar
import org.sopt.play.presentation.main.navigation.MainTab
import org.sopt.play.presentation.mypage.MyPageRoute

@Composable
fun MainTabRoute(
    registeredUserName: String,
    paddingValues: PaddingValues,
) {
    val tabs = MainTab.entries
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { page ->
            when (tabs[page]) {
                MainTab.HOME -> HomeRoute(registeredUserName, PaddingValues())
                MainTab.LIKE -> LikeRoute(PaddingValues())
                MainTab.MYPAGE -> MyPageRoute(PaddingValues())
            }
        }
        PlayMainTabBar(
            currentTab = tabs[pagerState.currentPage],
            onTabClick = { tab ->
                scope.launch {
                    pagerState.animateScrollToPage(tab.ordinal)
                }
            }
        )
    }
}
