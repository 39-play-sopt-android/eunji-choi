package org.sopt.play.presentation.home.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.home.component.HomeProductCard
import org.sopt.play.presentation.home.dummy.ProductDummyData
import org.sopt.play.presentation.home.model.ProductModel

@Composable
fun HomeProductRow(
    rowItems: List<ProductModel>,
    favorites: Set<String>,
    onFavoriteClick: (ProductModel) -> Unit,
    modifier: Modifier = Modifier,
    columnCount: Int = 2,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        rowItems.forEach { product ->
            HomeProductCard(
                item = product,
                isFavorite = product.id in favorites,
                onFavoriteClick = { onFavoriteClick(product) },
                modifier = Modifier.weight(1f),
            )
        }
        repeat(columnCount - rowItems.size) {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeProductRowPreview() {
    PlaySoptTheme {
        HomeProductRow(
            rowItems = ProductDummyData.products.take(2),
            favorites = setOf(ProductDummyData.products.first().id),
            onFavoriteClick = {},
        )
    }
}
