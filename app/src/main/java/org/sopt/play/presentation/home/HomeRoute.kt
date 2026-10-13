package org.sopt.play.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.sopt.play.data.dummy.ProductDummyData

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
) {
    var favorites by remember { mutableStateOf(setOf<String>())}

    HomeScreen(
        user = "김솝트",
        paddingValues = PaddingValues(),
        products = ProductDummyData.products,
        favorites = setOf(ProductDummyData.products.first().id),
        onFavoriteClick = {},
        onSearchClick = {},
    )
}
