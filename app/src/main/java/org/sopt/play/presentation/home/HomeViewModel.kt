package org.sopt.play.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.play.di.RepositoryModule
import org.sopt.play.domain.repository.AuthRepository
import org.sopt.play.domain.repository.FavoriteRepository
import org.sopt.play.presentation.home.dummy.BannerDummyData
import org.sopt.play.presentation.home.dummy.ProductDummyData

class HomeViewModel(
    private val authRepository: AuthRepository = RepositoryModule.authRepository,
    private val favoriteRepository: FavoriteRepository = RepositoryModule.favoriteRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(
        HomeState(
            userName = authRepository.getUserName(),
            banners = BannerDummyData.banners,
            products = ProductDummyData.products,
        )
    )
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadFavorites()
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.FavoriteClicked -> toggleFavorite(intent.id)
            HomeIntent.SearchClicked -> Unit
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            favoriteRepository.getFavorites().collect { result ->
                result.onSuccess { favorites ->
                    _state.update { state ->
                        state.copy(favorites = favorites.map { it.id }.toSet())
                    }
                }
            }
        }
    }

    private fun toggleFavorite(id: String) {
        val product = _state.value.products.find { it.id == id } ?: return

        viewModelScope.launch {
            if (id in _state.value.favorites) favoriteRepository.delete(id)
            else favoriteRepository.insert(product)
        }
    }
}
