package org.sopt.play.data.repository

import org.sopt.play.core.util.suspendRunCatching
import org.sopt.play.data.mapper.toModel
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
}
