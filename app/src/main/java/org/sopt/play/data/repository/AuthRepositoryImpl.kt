package org.sopt.play.data.repository

import org.sopt.play.data.local.datasource.AuthLocalDataSource
import org.sopt.play.domain.repository.AuthException
import org.sopt.play.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
) : AuthRepository {
    override fun register(name: String, email: String, password: String): Result<Unit> {
        return runCatching {
            authLocalDataSource.saveUser(name, email, password)
        }
    }

    override fun login(email: String, password: String): Result<Unit> {
        return runCatching {
            val savedEmail = authLocalDataSource.getEmail()
            val savedPassword = authLocalDataSource.getPassword()

            when {
                savedEmail == null || savedPassword == null -> throw AuthException.NoAccountFound()
                savedEmail != email -> throw AuthException.EmailNotFound()
                savedPassword != password -> throw AuthException.PasswordMismatch()
                else -> authLocalDataSource.setLoggedInState(true)
            }
        }
    }

    override fun isLoggedIn(): Boolean = authLocalDataSource.getIsLoggedIn()

    override fun getUserName(): String = authLocalDataSource.getName().orEmpty()
}
