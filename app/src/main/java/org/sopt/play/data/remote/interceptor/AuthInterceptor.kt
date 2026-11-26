package org.sopt.play.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import org.sopt.play.data.local.datasource.AuthLocalDataSource
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val code = authLocalDataSource.getAuthorizationCode()

        val request = chain.request().newBuilder().apply {
            if (code != null) header(HEADER_AUTHORIZATION, code)
        }.build()

        return chain.proceed(request)
    }

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
    }
}
