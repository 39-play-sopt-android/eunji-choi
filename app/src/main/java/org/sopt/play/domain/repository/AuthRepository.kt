package org.sopt.play.domain.repository

interface AuthRepository {
    fun register(name: String, email: String, password: String): Result<Unit>

    suspend fun login(email: String, password: String): Result<Unit>

    fun isLoggedIn(): Boolean

    fun getUserName(): String

    fun hasAuthorizationCode() : Boolean

}

sealed class AuthException : Exception() {
    class NoAccountFound : AuthException()
    class EmailNotFound : AuthException()
    class PasswordMismatch : AuthException()
}
