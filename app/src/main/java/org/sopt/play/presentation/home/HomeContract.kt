package org.sopt.play.presentation.home

import org.sopt.play.domain.model.BannerModel
import org.sopt.play.domain.model.ProductModel

data class HomeState(
    val userName: String = "",
    val banners: List<BannerModel> = emptyList(),
    val products: List<ProductModel> = emptyList(),
    val favoriteIds: Set<String> = emptySet(),
)

sealed interface HomeIntent {
    data class FavoriteClicked(val id: String) : HomeIntent
    data object SearchClicked : HomeIntent
}
