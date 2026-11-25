package org.sopt.play.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
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
    private val contentState = MutableStateFlow(
        HomeState(userName = authRepository.getUserName())
    )

    private val favoriteIds: Flow<Set<String>> = favoriteRepository.getFavorites()
        .mapNotNull { result -> result.getOrNull() }
        .map { favorites -> favorites.map { it.id }.toSet() }
        .onStart { emit(emptySet()) }

    val state: StateFlow<HomeState> = combine(contentState, favoriteIds) { content, ids ->
        content.copy(favoriteIds = ids)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = contentState.value,
    )

    init {
        loadBanners()
        loadProducts()
    }

    fun onIntent(intent: HomeIntent) {
        if (intent is HomeIntent.FavoriteClicked) toggleFavorite(intent.id)
    }

    private fun loadBanners() {
        viewModelScope.launch {
            bannerRepository.getBanners()
                .onSuccess { banners -> contentState.update { it.copy(banners = banners) } }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            productRepository.getProducts()
                .onSuccess { products -> contentState.update { it.copy(products = products) } }
        }
    }

    private fun toggleFavorite(id: String) {
        val current = state.value
        val product = current.products.find { it.id == id } ?: return

        viewModelScope.launch {
            if (id in current.favoriteIds) favoriteRepository.delete(id)
            else favoriteRepository.insert(product)
        }
    }
}
