package org.sopt.play.presentation.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import org.sopt.play.presentation.post.model.PostCardUiModel

@Composable
fun PostScreen(
    paddingValues: PaddingValues,
    keywordState: TextFieldState,
    titleState: TextFieldState,
    contentState: TextFieldState,
    completeEnabled: Boolean,
    isLoading: Boolean,
    posts: List<PostCardUiModel>,
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit,
    onCreateClick: () -> Unit,
    onEditClick: (Long) -> Unit,
    onDeleteClick: (Long) -> Unit,
) {
    var showCreatePostBottomSheet by remember { mutableStateOf(false) }
    var deletePostId by remember { mutableStateOf<Long?>(null) }
    var editPostId by remember { mutableStateOf<Long?>(null) }

    Box(
        modifier = modifier
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            PostSearchTextField(
                state = keywordState,
                onSearch = onSearchClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
            )

            if (posts.isEmpty() && !isLoading) {
                PostEmptyContent(
                    modifier = Modifier.weight(1f)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 152.dp),
                ) {
                    items(posts) {
                        PostCard(
                            title = it.title,
                            content = it.content,
                            author = it.author,
                            createdAt = it.createdAt,
                            onShortClick = {
                                titleState.setTextAndPlaceCursorAtEnd(it.title)
                                contentState.setTextAndPlaceCursorAtEnd(it.content)
                                editPostId = it.id
                            },
                            onLongClick = { deletePostId = it.id }
                        )
                    }
                }
            }
        }

        PostFloatingButton(
            onClick = { showCreatePostBottomSheet = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 20.dp),
        )
    }

    if (showCreatePostBottomSheet) {
        PostBottomSheet(
            titleState = titleState,
            contentState = contentState,
            completeEnabled = completeEnabled,
            onDismiss = { showCreatePostBottomSheet = false },
            onCompleteClick = {
                onCreateClick()
                titleState.clearText()
                contentState.clearText()
                showCreatePostBottomSheet = false
            },
        )
    }

    editPostId?.let { postId ->
        val originalPost = posts.find { it.id == postId }
        val editCompleteEnabled = completeEnabled &&
                originalPost != null &&
                (titleState.text.toString() != originalPost.title ||
                        contentState.text.toString() != originalPost.content)

        PostBottomSheet(
            titleState = titleState,
            contentState = contentState,
            completeEnabled = editCompleteEnabled,
            onDismiss = {
                titleState.clearText()
                contentState.clearText()
                editPostId = null
            },
            onCompleteClick = {
                onEditClick(postId)
                titleState.clearText()
                contentState.clearText()
                editPostId = null
            },
        )
    }

    deletePostId?.let { postId ->
        Dialog(onDismissRequest = { deletePostId = null }) {
            PostModal(
                onCancelClick = {
                    deletePostId = null
                },
                onDeleteClick = {
                    onDeleteClick(postId)
                    deletePostId = null
                },
            )
        }
    }
}

