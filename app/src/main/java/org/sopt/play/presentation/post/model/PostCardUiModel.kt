package org.sopt.play.presentation.post.model

import org.sopt.play.domain.model.PostModel

data class PostCardUiModel(
    val id: Long,
    val title: String,
    val content: String,
    val author: String,
    val createdAt: String,
)

fun PostModel.toUiModel(): PostCardUiModel = PostCardUiModel(
    id = id,
    title = title,
    content = content,
    author = author,
    createdAt = createdAt.toDisplayDate(),
)

private fun String.toDisplayDate(): String = take(10).replace("-", ".")
