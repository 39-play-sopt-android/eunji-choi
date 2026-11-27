package org.sopt.play.presentation.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    navigateToPost: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        userName = state.userName,
        paddingValues = paddingValues,
        banners = state.banners,
        products = state.products,
        favoriteIds = state.favoriteIds,
        onFavoriteClick = { viewModel.onIntent(HomeIntent.FavoriteClicked(it)) },
        onSearchClick = navigateToPost,
    )
}
