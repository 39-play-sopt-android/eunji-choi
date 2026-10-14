package org.sopt.play.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.core.common.extension.noRippleClickable
import org.sopt.play.core.designsystem.component.asyncimage.PlayAsyncImage
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.home.model.ProductModel

@Composable
fun HomeProductCard(
    item: ProductModel,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val favoriteIcon = if (isFavorite) R.drawable.ic_favorite_filled_24
    else R.drawable.ic_favorite_unfilled_24

    val favoriteColor = if (isFavorite) PlaySoptTheme.colors.red
    else PlaySoptTheme.colors.white

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Box {
            PlayAsyncImage(
                imageUrl = item.imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(4f / 5f),
            )

            Icon(
                painter = painterResource(id = favoriteIcon),
                contentDescription = null,
                tint = favoriteColor,
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .noRippleClickable(onClick = onFavoriteClick)
                    .padding(all = 8.dp)
                    .size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = item.storeName,
                style = PlaySoptTheme.typography.m14,
                color = PlaySoptTheme.colors.black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Text(
                text = item.caption,
                style = PlaySoptTheme.typography.r14,
                color = PlaySoptTheme.colors.black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Row(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(R.string.home_item_discount_rate, item.discountRate),
                    style = PlaySoptTheme.typography.m14,
                    color = PlaySoptTheme.colors.red,
                )

                Text(
                    text = "%,d".format(item.price),
                    style = PlaySoptTheme.typography.m14,
                    color = PlaySoptTheme.colors.black,
                )
            }
        }


        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun HomeProductCardPreview() {
    PlaySoptTheme {
        val dummyItem = ProductModel(
            imageUrl = "https://raw.githubusercontent.com/chldmswll/play-sopt-assets/main/img_home_autumn.png",
            storeName = "슬로우앤드",
            caption = "[\uD83C\uDF41가을/3기장선택!] made. 모먼트 투커버 올데이슬랙스 (+숏/베이직/롱ver) - 4 size",
            discountRate = 50,
            price = 26820
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HomeProductCard(
                item = dummyItem,
                isFavorite = true,
                modifier = Modifier
                    .weight(1f),
                onFavoriteClick = {}
            )

            HomeProductCard(
                item = dummyItem,
                isFavorite = false,
                modifier = Modifier
                    .weight(1f),
                onFavoriteClick = {}
            )
        }
    }
}
