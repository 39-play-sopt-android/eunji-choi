package org.sopt.play.domain.repository

import org.sopt.play.domain.model.PostsModel

interface PostRepository {
    suspend fun getPosts(keyword: String? = null, cursor: Int? = null): Result<PostsModel>

    suspend fun createPost(title: String, content: String, author: String): Result<Unit>

    suspend fun editPost(postId: Long, title: String, content: String): Result<Unit>

    suspend fun deletePost(postId: Long): Result<Unit>
}
