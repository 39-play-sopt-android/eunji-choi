package org.sopt.play.presentation.post

import org.sopt.play.presentation.post.model.PostCardUiModel

data class PostState(
    val search: String = "",
    val title: String = "",
    val content: String = "",
    val posts: List<PostCardUiModel> = emptyList(),
) {
    val completeEnabled: Boolean
        get() = title.isNotBlank() && content.isNotBlank() &&
                title.length <= 20 && content.length <= 1000
}

sealed interface PostIntent {
    data class SearchChanged(val search: String) : PostIntent
    data class TitleChanged(val title: String) : PostIntent
    data class ContentChanged(val content: String) : PostIntent
    data object CompleteClicked : PostIntent
    data object DeleteClicked : PostIntent
}
