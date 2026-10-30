package org.sopt.play.data.repository

import org.sopt.play.data.local.datasource.AuthLocalDataSource
import org.sopt.play.domain.repository.AuthException
import org.sopt.play.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authPrefs: AuthLocalDataSource,
) : AuthRepository {
    override fun register(name: String, email: String, password: String): Result<Unit> {
        return runCatching {
            authPrefs.saveUser(name, email, password)
        }
    }

    override fun login(email: String, password: String): Result<Unit> {
        return runCatching {
            val savedEmail = authPrefs.getEmail()
            val savedPassword = authPrefs.getPassword()

            when {
                savedEmail == null || savedPassword == null -> throw AuthException.NoAccountFound()
                savedEmail != email -> throw AuthException.EmailNotFound()
                savedPassword != password -> throw AuthException.PasswordMismatch()
                else -> authPrefs.setLoggedInState(true)
            }
        }
    }

    override fun isLoggedIn(): Boolean = authPrefs.getIsLoggedIn()

    override fun getUserName(): String = authPrefs.getName().orEmpty()
}
