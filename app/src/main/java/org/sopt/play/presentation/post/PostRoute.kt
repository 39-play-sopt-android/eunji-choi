package org.sopt.play.presentation.post

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.sopt.play.core.common.extension.toast

@Composable
fun PostRoute(
    paddingValues: PaddingValues,
    viewModel: PostViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val keywordState = rememberTextFieldState()
    val titleState = rememberTextFieldState()
    val contentState = rememberTextFieldState()

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is PostSideEffect.ShowToast -> context.toast(effect.messageRes)
            }
        }
    }

    LaunchedEffect(keywordState) {
        snapshotFlow { keywordState.text.toString() }
            .collect { viewModel.onIntent(PostIntent.KeywordChanged(it)) }
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
        keywordState = keywordState,
        titleState = titleState,
        contentState = contentState,
        completeEnabled = state.completeEnabled,
        isLoading = state.isLoading,
        posts = state.posts,
        onSearchClick = { viewModel.onIntent(PostIntent.SearchClicked) },
        onCompleteClick = { viewModel.onIntent(PostIntent.CompleteClicked) },
        onDeleteClick = { id ->
        viewModel.onIntent(PostIntent.DeleteClicked(id)) }
    )
}
