package org.sopt.play.data.repository

import org.sopt.play.core.util.suspendRunCatching
import org.sopt.play.data.mapper.toModel
import org.sopt.play.data.remote.dto.request.CreatePostRequest
import org.sopt.play.data.remote.service.PostService
import org.sopt.play.domain.model.PostsModel
import org.sopt.play.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postService: PostService,
) : PostRepository {
    override suspend fun getPosts(keyword: String?, cursor: Int?):
            Result<PostsModel> =
        suspendRunCatching {
            val response = postService.getPosts(keyword = keyword, cursor = cursor)
            requireNotNull(response.data) { response.message }.toModel()
        }

    override suspend fun createPost(
        title: String,
        content: String,
        author: String,
    ): Result<Unit> =
        suspendRunCatching {
            val response = postService.createPost(
                request = CreatePostRequest(
                    title = title,
                    content = content,
                    author = author
                ),
            )
            check(response.isSuccess) { response.message }
        }
}
