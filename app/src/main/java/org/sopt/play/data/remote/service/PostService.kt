package org.sopt.play.data.remote.service

import org.sopt.play.data.remote.dto.response.BaseResponse
import org.sopt.play.data.remote.dto.response.PostsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PostService {
    @GET("api/v1/posts")
    suspend fun getPosts(
        @Query("keyword") keyword: String? = null,
        @Query("cursor") cursor: Int? = null,
        @Query("size") size: Int = 10,
    ): BaseResponse<PostsResponse>
}
