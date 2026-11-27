package org.sopt.play.presentation.home

import org.sopt.play.presentation.home.model.BannerUiModel
import org.sopt.play.presentation.home.model.ProductUiModel

data class HomeState(
    val userName: String = "",
    val banners: List<BannerUiModel> = emptyList(),
    val products: List<ProductUiModel> = emptyList(),
    val favoriteIds: Set<String> = emptySet(),
)

sealed interface HomeIntent {
    data class FavoriteClicked(val id: String) : HomeIntent
}
