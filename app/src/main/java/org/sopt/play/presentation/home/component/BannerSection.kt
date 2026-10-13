package org.sopt.play.presentation.home.component

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.data.dummy.BannerDummyData
import org.sopt.play.presentation.home.model.BannerModel

@Composable
fun BannerSection(
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
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(infiniteCount) { index ->
            val banner = banners[index % contentsSize]

            AsyncImage(
                model = banner.bannerUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(PlaySoptTheme.colors.gray2),
                error = ColorPainter(PlaySoptTheme.colors.gray2),
                modifier = Modifier
                    .fillParentMaxWidth()
                    .aspectRatio(328f / 195f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BannerSectionPreview() {
    PlaySoptTheme {
        BannerSection(
            banners = BannerDummyData.banners,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
