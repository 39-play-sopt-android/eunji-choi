package org.sopt.play.data.remote.service

import org.sopt.play.data.remote.dto.request.AuthorizationCodeRequest
import org.sopt.play.data.remote.dto.response.AuthorizationCodeResponse
import org.sopt.play.data.remote.dto.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/v1/members/authorization-code")
    suspend fun postAuthorizationCode(
        @Body request: AuthorizationCodeRequest,
    ) : BaseResponse<AuthorizationCodeResponse>
}
