package org.sopt.play.presentation.post

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PostRoute(
    paddingValues: PaddingValues,
    viewModel: PostViewModel = viewModel(),
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
        onDeleteClick = { viewModel.onIntent(PostIntent.DeleteClicked) }
    )
}
