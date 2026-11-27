package org.sopt.play.presentation.post

import androidx.annotation.StringRes
import org.sopt.play.presentation.post.model.PostCardUiModel

data class PostState(
    val keyword: String = "",
    val title: String = "",
    val content: String = "",
    val posts: List<PostCardUiModel> = emptyList(),
    val isLoading: Boolean = true,
    val editingPost: PostCardUiModel? = null,
    val nextCursor: Int? = null,
    val hasNext: Boolean = false,
    val isLoadingMore: Boolean = false,
) {
    val completeEnabled: Boolean
        get() = title.isNotBlank() && content.isNotBlank()

    val editCompleteEnabled: Boolean
        get() {
            val original = editingPost ?: return false
            return completeEnabled &&
                    (title != original.title || content != original.content)
        }
}

sealed interface PostIntent {
    data class KeywordChanged(val keyword: String) : PostIntent
    data class TitleChanged(val title: String) : PostIntent
    data class ContentChanged(val content: String) : PostIntent
    data object CreateClicked : PostIntent
    data class EditStarted(val id: Long) : PostIntent
    data object EditDismissed : PostIntent
    data object EditClicked : PostIntent
    data class DeleteClicked(val id: Long) : PostIntent
    data object SearchClicked : PostIntent
    data object LoadMore : PostIntent
}

sealed interface PostSideEffect {
    data class ShowToast(@StringRes val messageRes: Int) : PostSideEffect
}
