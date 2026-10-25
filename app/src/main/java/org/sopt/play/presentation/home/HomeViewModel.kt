package org.sopt.play.presentation.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.sopt.play.presentation.home.dummy.BannerDummyData
import org.sopt.play.presentation.home.dummy.ProductDummyData

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(
        HomeState(
            banners = BannerDummyData.banners,
            products = ProductDummyData.products,
            favorites = setOf(ProductDummyData.products.first().id),
        )
    )
    val state: StateFlow<HomeState> = _state.asStateFlow()

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.FavoriteClicked -> toggleFavorite(intent.id)
            HomeIntent.SearchClicked -> Unit
        }
    }

    private fun toggleFavorite(id: String) {
        _state.update { state ->
            val favorites = if (id in state.favorites) state.favorites - id
            else state.favorites + id
            state.copy(favorites = favorites)
        }
    }
}
