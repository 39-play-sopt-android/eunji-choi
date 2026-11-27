package org.sopt.play.data.mapper

import org.sopt.play.data.remote.dto.response.PostResponse
import org.sopt.play.data.remote.dto.response.PostsResponse
import org.sopt.play.domain.model.PostModel
import org.sopt.play.domain.model.PostsModel

fun PostsResponse.toModel(): PostsModel = PostsModel(
    posts = posts.map { it.toModel() },
    nextCursor = nextCursor,
    hasNext = hasNext,
)

fun PostResponse.toModel(): PostModel = PostModel(
    id = id,
    title = title,
    content = content,
    author = author,
    createdAt = createdAt,
)
