package org.sopt.play.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class PostsResponseDto(
    val posts: List<PostResponse>,
    val nextCursor: Int?,
    val hasNext: Boolean,
)

@Serializable
data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val author: String,
    val createdAt: String,
    val updatedAt: String,
)
