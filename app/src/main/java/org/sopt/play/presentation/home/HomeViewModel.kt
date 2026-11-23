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
import org.sopt.play.domain.repository.BannerRepository
import org.sopt.play.domain.repository.FavoriteRepository
import org.sopt.play.domain.repository.ProductRepository

class HomeViewModel(
    private val authRepository: AuthRepository = RepositoryModule.authRepository,
    private val favoriteRepository: FavoriteRepository = RepositoryModule.favoriteRepository,
    private val productRepository: ProductRepository = RepositoryModule.productRepository,
    private val bannerRepository: BannerRepository = RepositoryModule.bannerRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(
        HomeState(userName = authRepository.getUserName())
    )
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadBanners()
        loadProducts()
        loadFavorites()
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.FavoriteClicked -> toggleFavorite(intent.id)
            HomeIntent.SearchClicked -> Unit
        }
    }

    private fun loadBanners() {
        viewModelScope.launch {
            bannerRepository.getBanners()
                .onSuccess { banners ->
                    _state.update { state -> state.copy(banners = banners) }
                }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            productRepository.getProducts()
                .onSuccess { products ->
                    _state.update { state -> state.copy(products = products) }
                }
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            favoriteRepository.getFavorites().collect { result ->
                result.onSuccess { favorites ->
                    _state.update { state ->
                        state.copy(favoriteIds = favorites.map { it.id }.toSet())
                    }
                }
            }
        }
    }

    private fun toggleFavorite(id: String) {
        val product = _state.value.products.find { it.id == id } ?: return

        viewModelScope.launch {
            if (id in _state.value.favoriteIds) favoriteRepository.delete(id)
            else favoriteRepository.insert(product)
        }
    }
}
