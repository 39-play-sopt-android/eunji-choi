package org.sopt.play.data.remote.service

import org.sopt.play.data.remote.dto.request.CreatePostRequest
import org.sopt.play.data.remote.dto.response.BaseResponse
import org.sopt.play.data.remote.dto.response.PostResponse
import org.sopt.play.data.remote.dto.response.PostsResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostService {
    @GET("api/v1/posts")
    suspend fun getPosts(
        @Query("keyword") keyword: String? = null,
        @Query("cursor") cursor: Int? = null,
        @Query("size") size: Int = 10,
    ): BaseResponse<PostsResponse>

    @POST("api/v1/posts")
    suspend fun createPost(
        @Body request: CreatePostRequest
    ): BaseResponse<PostResponse>

    @DELETE("api/v1/posts/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: Long,
    ): BaseResponse<Unit>
}
