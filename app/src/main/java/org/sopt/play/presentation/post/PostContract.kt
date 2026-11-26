package org.sopt.play.presentation.post

import androidx.annotation.StringRes
import org.sopt.play.presentation.post.model.PostCardUiModel

data class PostState(
    val keyword: String = "",
    val title: String = "",
    val content: String = "",
    val posts: List<PostCardUiModel> = emptyList(),
    val isLoading: Boolean = true,
) {
    val completeEnabled: Boolean
        get() = title.isNotBlank() && content.isNotBlank()
}

sealed interface PostIntent {
    data class KeywordChanged(val keyword: String) : PostIntent
    data class TitleChanged(val title: String) : PostIntent
    data class ContentChanged(val content: String) : PostIntent
    data object CompleteClicked : PostIntent
    data class DeleteClicked(val id: Long) : PostIntent
    data object SearchClicked: PostIntent
}

sealed interface PostSideEffect {
    data class ShowToast(@StringRes val messageRes: Int) : PostSideEffect
}
