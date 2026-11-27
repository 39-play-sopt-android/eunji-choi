package org.sopt.play.presentation.post

import androidx.annotation.StringRes
import org.sopt.play.R
import org.sopt.play.presentation.post.model.PostCardUiModel

data class PostState(
    val keyword: String = "",
    val title: String = "",
    val content: String = "",
    val posts: List<PostCardUiModel> = emptyList(),
    val searchedKeyword: String? = null,
    val isLoading: Boolean = true,
    val isLoadFailed: Boolean = false,
    val nextCursor: Int? = null,
    val hasNext: Boolean = false,
    val isLoadingMore: Boolean = false,
    val isCreateSheetVisible: Boolean = false,
    val editingPost: PostCardUiModel? = null,
    val deletingPostId: Long? = null,
    val isSubmitting: Boolean = false,
) {
    val completeEnabled: Boolean
        get() = title.isNotBlank() && content.isNotBlank() && !isSubmitting

    val editCompleteEnabled: Boolean
        get() {
            val original = editingPost ?: return false
            return completeEnabled &&
                    (title != original.title || content != original.content)
        }

    val showEmpty: Boolean
        get() = posts.isEmpty() && !isLoading

    @get:StringRes
    val emptyMessageRes: Int
        get() = when {
            isLoadFailed -> R.string.post_load_failure
            searchedKeyword != null -> R.string.post_search_empty
            else -> R.string.post_empty
        }
}

sealed interface PostIntent {
    data class KeywordChanged(val keyword: String) : PostIntent
    data class TitleChanged(val title: String) : PostIntent
    data class ContentChanged(val content: String) : PostIntent
    data object SearchClicked : PostIntent
    data object LoadMore : PostIntent
    data object CreateSheetOpened : PostIntent
    data object CreateSheetDismissed : PostIntent
    data object CreateClicked : PostIntent
    data class EditStarted(val id: Long) : PostIntent
    data object EditDismissed : PostIntent
    data object EditClicked : PostIntent
    data class DeleteRequested(val id: Long) : PostIntent
    data object DeleteDismissed : PostIntent
    data object DeleteConfirmed : PostIntent
}

sealed interface PostSideEffect {
    data class ShowToast(@StringRes val messageRes: Int) : PostSideEffect
    data object ClearInputs : PostSideEffect
}
