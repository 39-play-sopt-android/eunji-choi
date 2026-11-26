package org.sopt.play.presentation.post

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PostRoute(
    paddingValues: PaddingValues,
    viewModel: PostViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val searchState = rememberTextFieldState()
    val titleState = rememberTextFieldState()
    val contentState = rememberTextFieldState()

    LaunchedEffect(searchState) {
        snapshotFlow { searchState.text.toString() }
            .collect { viewModel.onIntent(PostIntent.SearchChanged(it)) }
    }

    LaunchedEffect(titleState) {
        snapshotFlow { titleState.text.toString() }
            .collect { viewModel.onIntent(PostIntent.TitleChanged(it)) }
    }

    LaunchedEffect(contentState) {
        snapshotFlow { contentState.text.toString() }
            .collect { viewModel.onIntent(PostIntent.ContentChanged(it)) }
    }

    PostScreen(
        paddingValues = paddingValues,
        searchState = searchState,
        titleState = titleState,
        contentState = contentState,
        completeEnabled = state.completeEnabled,
        posts = state.posts,
        onCompleteClick = { viewModel.onIntent(PostIntent.CompleteClicked) },
        onDeleteClick = { id ->
        viewModel.onIntent(PostIntent.DeleteClicked(id)) }
    )
}
