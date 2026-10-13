package org.sopt.play.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.component.productcard.ProductCard
import org.sopt.play.core.designsystem.component.productcard.model.ProductModel
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.data.dummy.ProductDummyData

@Composable
fun ProductGrid(
    products: List<ProductModel>,
    favorites: Set<String>,
    onFavoriteClick: (ProductModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        products.chunked(2).forEach { rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                rowItems.forEach { product ->
                    ProductCard(
                        item = product,
                        isFavorite = product.id in favorites,
                        onFavoriteClick = { onFavoriteClick(product) },
                        modifier = Modifier.weight(1f),
                    )
                }
                if (rowItems.size == 1) Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProductGridPreview() {
    PlaySoptTheme {
        ProductGrid(
            products = ProductDummyData.products,
            favorites = setOf(ProductDummyData.products.first().id),
            onFavoriteClick = {},
        )
    }
}
