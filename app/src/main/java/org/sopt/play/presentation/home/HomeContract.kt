package org.sopt.play.presentation.home

import org.sopt.play.presentation.home.model.BannerModel
import org.sopt.play.presentation.home.model.ProductModel

data class HomeState(
    val banners: List<BannerModel> = emptyList(),
    val products: List<ProductModel> = emptyList(),
    val favorites: Set<String> = emptySet(),
)

sealed interface HomeIntent {
    data class FavoriteClicked(val id: String) : HomeIntent
    data object SearchClicked : HomeIntent
}
