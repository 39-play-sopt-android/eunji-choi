package org.sopt.play.domain.repository

import org.sopt.play.domain.model.PostsModel

interface PostRepository {
    suspend fun getPosts(keyword: String? = null, cursor: Int? = null): Result<PostsModel>
}
