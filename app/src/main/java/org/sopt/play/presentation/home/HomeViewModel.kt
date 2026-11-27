package org.sopt.play.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
import org.sopt.play.domain.model.ProductModel
import org.sopt.play.domain.repository.AuthRepository
import org.sopt.play.domain.repository.BannerRepository
import org.sopt.play.domain.repository.FavoriteRepository
import org.sopt.play.domain.repository.ProductRepository
import org.sopt.play.presentation.home.model.toUiModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val favoriteRepository: FavoriteRepository,
    private val productRepository: ProductRepository,
    private val bannerRepository: BannerRepository,
) : ViewModel() {
    private val contentState = MutableStateFlow(
        HomeState(userName = authRepository.getUserName())
    )

    private var productModels: List<ProductModel> = emptyList()

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
                .onSuccess { banners ->
                    contentState.update { it.copy(banners = banners.map { banner -> banner.toUiModel() }) }
                }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            productRepository.getProducts()
                .onSuccess { products ->
                    productModels = products
                    contentState.update { it.copy(products = products.map { product -> product.toUiModel() }) }
                }
        }
    }

    private fun toggleFavorite(id: String) {
        val product = productModels.find { it.id == id } ?: return
        val isFavorite = id in state.value.favoriteIds

        viewModelScope.launch {
            if (isFavorite) favoriteRepository.delete(id)
            else favoriteRepository.insert(product)
        }
    }
}
