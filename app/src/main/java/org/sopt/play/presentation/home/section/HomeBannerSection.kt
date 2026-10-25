package org.sopt.play.presentation.home.section

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.component.asyncimage.PlayAsyncImage
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.home.dummy.BannerDummyData
import org.sopt.play.presentation.home.model.BannerModel

@Composable
fun HomeBannerSection(
    banners: List<BannerModel>,
    modifier: Modifier = Modifier,
) {
    if (banners.isEmpty()) return

    val infiniteCount = Int.MAX_VALUE
    val contentsSize = banners.size
    val startIndex = infiniteCount / 2 / contentsSize * contentsSize

    val state = rememberLazyListState(initialFirstVisibleItemIndex = startIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = state)

    LazyRow(
        state = state,
        flingBehavior = flingBehavior,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(infiniteCount) { index ->
            val banner = banners[index % contentsSize]

            PlayAsyncImage(
                imageUrl = banner.imageUrl,
                modifier = Modifier
                    .fillParentMaxWidth()
                    .aspectRatio(328f / 195f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeBannerSectionPreview() {
    PlaySoptTheme {
        HomeBannerSection(
            banners = BannerDummyData.banners,
        )
    }
}
