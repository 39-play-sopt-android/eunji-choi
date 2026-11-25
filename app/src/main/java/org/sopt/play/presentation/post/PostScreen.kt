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
import org.sopt.play.presentation.post.component.PostFloatingButton
import org.sopt.play.presentation.post.component.PostModal
import org.sopt.play.presentation.post.component.PostSearchTextField
import org.sopt.play.presentation.post.model.PostCardUiModel

@Composable
fun PostScreen(
    paddingValues: PaddingValues,
    searchState: TextFieldState,
    titleState: TextFieldState,
    contentState: TextFieldState,
    completeEnabled: Boolean,
    posts: List<PostCardUiModel>,
    modifier: Modifier = Modifier,
    onCompleteClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    var showPostBottomSheet by remember { mutableStateOf(false) }
    var showPostModal by remember { mutableStateOf(false) }

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
                state = searchState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(posts) {
                    PostCard(
                        title = it.title,
                        content = it.content,
                        author = it.author,
                        createdAt = it.createdAt,
                        onLongClick = { showPostModal = true }
                    )
                }
            }


        }

        PostFloatingButton(
            onClick = { showPostBottomSheet = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 20.dp),
        )
    }

    if (showPostBottomSheet) {
        PostBottomSheet(
            titleState = titleState,
            contentState = contentState,
            completeEnabled = completeEnabled,
            onDismiss = { showPostBottomSheet = false },
            onCompleteClick = {
                onCompleteClick()
                showPostBottomSheet = false
            },
        )
    }

    if (showPostModal) {
        Dialog(onDismissRequest = { showPostModal = false }) {
            PostModal(
                onCancelClick = {
                    showPostModal = false
                },
                onDeleteClick = {
                    onDeleteClick()
                    showPostModal = false
                },
            )
        }
    }
}
