package org.sopt.play.presentation.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.sopt.play.presentation.post.component.PostBottomSheet
import org.sopt.play.presentation.post.component.PostCard
import org.sopt.play.presentation.post.component.PostEmptyContent
import org.sopt.play.presentation.post.component.PostFloatingButton
import org.sopt.play.presentation.post.component.PostModal
import org.sopt.play.presentation.post.component.PostSearchTextField

@Composable
fun PostScreen(
    state: PostState,
    paddingValues: PaddingValues,
    listState: LazyListState,
    keywordState: TextFieldState,
    titleState: TextFieldState,
    contentState: TextFieldState,
    onSearchClick: () -> Unit,
    onCreateSheetOpen: () -> Unit,
    onCreateSheetDismiss: () -> Unit,
    onCreateClick: () -> Unit,
    onEditStart: (Long) -> Unit,
    onEditDismiss: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteRequest: (Long) -> Unit,
    onDeleteDismiss: () -> Unit,
    onDeleteConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            PostSearchTextField(
                state = keywordState,
                onSearch = onSearchClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
            )

            if (state.showEmpty) {
                PostEmptyContent(
                    messageRes = state.emptyMessageRes,
                    modifier = Modifier.weight(1f),
                )
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 152.dp),
                ) {
                    items(state.posts, key = { it.id }) { post ->
                        PostCard(
                            title = post.title,
                            content = post.content,
                            author = post.author,
                            createdAt = post.createdAt,
                            onClick = {
                                titleState.setTextAndPlaceCursorAtEnd(post.title)
                                contentState.setTextAndPlaceCursorAtEnd(post.content)
                                onEditStart(post.id)
                            },
                            onLongClick = { onDeleteRequest(post.id) },
                        )
                    }
                }
            }
        }

        PostFloatingButton(
            onClick = onCreateSheetOpen,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 20.dp),
        )
    }

    if (state.isCreateSheetVisible) {
        PostBottomSheet(
            titleState = titleState,
            contentState = contentState,
            completeEnabled = state.completeEnabled,
            onDismiss = onCreateSheetDismiss,
            onCompleteClick = onCreateClick,
        )
    }

    if (state.editingPost != null) {
        PostBottomSheet(
            titleState = titleState,
            contentState = contentState,
            completeEnabled = state.editCompleteEnabled,
            onDismiss = onEditDismiss,
            onCompleteClick = onEditClick,
        )
    }

    if (state.deletingPostId != null) {
        Dialog(onDismissRequest = onDeleteDismiss) {
            PostModal(
                onCancelClick = onDeleteDismiss,
                onDeleteClick = onDeleteConfirm,
            )
        }
    }
}
