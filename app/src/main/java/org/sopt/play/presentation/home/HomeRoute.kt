package org.sopt.play.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.sopt.play.data.dummy.BannerDummyData
import org.sopt.play.data.dummy.ProductDummyData

@Composable
fun HomeRoute(
    registeredUserName: String,
    paddingValues: PaddingValues,
) {
    var favorites by remember {
        mutableStateOf(setOf(ProductDummyData.products.first().id))
    }

    HomeScreen(
        userName = registeredUserName,
        paddingValues = paddingValues,
        banners = BannerDummyData.banners,
        products = ProductDummyData.products,
        favorites = favorites,
        onFavoriteClick = { product ->
            favorites = if (product.id in favorites) favorites - product.id
            else favorites + product.id
        },
        onSearchClick = {},
    )
}
