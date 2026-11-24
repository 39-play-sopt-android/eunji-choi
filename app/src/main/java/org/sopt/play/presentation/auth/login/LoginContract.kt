package org.sopt.play.presentation.auth.login

import androidx.annotation.StringRes
import org.sopt.play.presentation.auth.util.AuthValidator

data class LoginState(
    val email: String = "",
    val password: String = "",
) {
    @get:StringRes
    val emailError: Int?
        get() = AuthValidator.emailError(email)

    @get:StringRes
    val passwordError: Int?
        get() = AuthValidator.passwordError(password)

    val loginEnabled: Boolean
        get() = AuthValidator.isValidEmail(email) && AuthValidator.isValidPassword(password)
}

sealed interface LoginIntent {
    data class EmailChanged(val email: String) : LoginIntent
    data class PasswordChanged(val password: String) : LoginIntent
    data object LoginClicked : LoginIntent
}

sealed interface LoginSideEffect {
    data class ShowToast(@StringRes val messageRes: Int) : LoginSideEffect
    data object NavigateToHome : LoginSideEffect
}
