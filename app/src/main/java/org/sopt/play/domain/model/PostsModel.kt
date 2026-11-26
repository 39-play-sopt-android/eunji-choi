package org.sopt.play.domain.model

data class PostsModel(
    val posts: List<PostModel>,
    val nextCursor: Int,
    val hasNext: Boolean,
)


data class PostModel(
    val title: String,
    val content: String,
    val author: String,
    val createdAt: String,
)
