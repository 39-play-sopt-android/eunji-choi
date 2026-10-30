package org.sopt.play.presentation.home

import org.sopt.play.domain.model.ProductModel
import org.sopt.play.presentation.home.model.BannerModel

data class HomeState(
    val userName: String = "",
    val banners: List<BannerModel> = emptyList(),
    val products: List<ProductModel> = emptyList(),
    val favorites: Set<String> = emptySet(),
)

sealed interface HomeIntent {
    data class FavoriteClicked(val id: String) : HomeIntent
    data object SearchClicked : HomeIntent
}
