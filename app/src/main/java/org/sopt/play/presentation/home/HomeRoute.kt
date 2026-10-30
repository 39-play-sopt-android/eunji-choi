package org.sopt.play.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        userName = state.userName,
        paddingValues = paddingValues,
        banners = state.banners,
        products = state.products,
        favorites = state.favorites,
        onFavoriteClick = { viewModel.onIntent(HomeIntent.FavoriteClicked(it)) },
        onSearchClick = { viewModel.onIntent(HomeIntent.SearchClicked) },
    )
}
