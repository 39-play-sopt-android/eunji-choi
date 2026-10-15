package org.sopt.play.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import org.sopt.play.data.dummy.BannerDummyData
import org.sopt.play.data.dummy.ProductDummyData
import org.sopt.play.presentation.home.model.ProductModel

@Composable
fun HomeRoute(
    registeredUserName: String,
    favorites: Set<String>,
    onFavoriteClick: (ProductModel) -> Unit,
    paddingValues: PaddingValues,
) {
    HomeScreen(
        userName = registeredUserName,
        paddingValues = paddingValues,
        banners = BannerDummyData.banners,
        products = ProductDummyData.products,
        favorites = favorites,
        onFavoriteClick = onFavoriteClick,
        onSearchClick = {},
    )
}
