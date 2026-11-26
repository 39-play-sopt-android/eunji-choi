package org.sopt.play.data.repository

import org.sopt.play.core.util.suspendRunCatching
import org.sopt.play.data.local.datasource.AuthLocalDataSource
import org.sopt.play.data.remote.dto.request.AuthorizationCodeRequest
import org.sopt.play.data.remote.service.AuthService
import org.sopt.play.domain.repository.AuthException
import org.sopt.play.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authService: AuthService,
) : AuthRepository {
    override fun register(name: String, email: String, password: String): Result<Unit> {
        return runCatching {
            authLocalDataSource.saveUser(name, email, password)
        }
    }

    override suspend fun login(email: String, password: String): Result<Unit> = suspendRunCatching {
        val savedEmail = authLocalDataSource.getEmail()
        val savedPassword = authLocalDataSource.getPassword()

        when {
            savedEmail == null || savedPassword == null -> throw AuthException.NoAccountFound()
            savedEmail != email -> throw AuthException.EmailNotFound()
            savedPassword != password -> throw AuthException.PasswordMismatch()
        }

        val response =
            authService.postAuthorizationCode(request = AuthorizationCodeRequest(part = PART_ANDROID))
        val code = requireNotNull(response.data) { response.message }.authorizationCode

        authLocalDataSource.saveAuthorizationCode(code)
        authLocalDataSource.setLoggedInState(true)
    }

    override fun isLoggedIn(): Boolean = authLocalDataSource.getIsLoggedIn()

    override fun getUserName(): String = authLocalDataSource.getName().orEmpty()

    override fun hasAuthorizationCode(): Boolean =
        authLocalDataSource.getAuthorizationCode().isNullOrBlank()

    companion object {
        private const val PART_ANDROID = "ANDROID"
    }
}
