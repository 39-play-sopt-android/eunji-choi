package org.sopt.play.presentation.maintab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.sopt.play.data.dummy.ProductDummyData
import org.sopt.play.presentation.home.HomeRoute
import org.sopt.play.presentation.like.LikeRoute
import org.sopt.play.presentation.maintab.component.PlayMainTabBar
import org.sopt.play.presentation.mypage.MyPageRoute

@Composable
fun MainTabRoute(
    registeredUserName: String,
    paddingValues: PaddingValues,
) {
    val tabs = MainTab.entries
    val pagerState = rememberPagerState { tabs.size }
    val scope = rememberCoroutineScope()

    var favorites by rememberSaveable {
        mutableStateOf(setOf(ProductDummyData.products.first().id))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding()),
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
        ) { page ->
            when (tabs[page]) {
                MainTab.HOME -> HomeRoute(
                    registeredUserName = registeredUserName,
                    favorites = favorites,
                    onFavoriteClick = { product ->
                        favorites = if (product.id in favorites) favorites - product.id
                        else favorites + product.id
                    },
                    paddingValues = PaddingValues(),
                )
                MainTab.LIKE -> LikeRoute(PaddingValues())
                MainTab.MYPAGE -> MyPageRoute(PaddingValues())
            }
        }
        PlayMainTabBar(
            currentTab = tabs[pagerState.targetPage],
            onTabClick = { tab ->
                scope.launch {
                    pagerState.animateScrollToPage(tab.ordinal)
                }
            }
        )
    }
}
