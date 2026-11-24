package org.sopt.play.presentation.auth.register

import androidx.annotation.StringRes
import org.sopt.play.presentation.auth.util.AuthValidator

data class RegisterState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirm: String = "",
) {
    @get:StringRes
    val emailError: Int?
        get() = AuthValidator.emailError(email)

    @get:StringRes
    val passwordError: Int?
        get() = AuthValidator.passwordError(password)

    @get:StringRes
    val passwordConfirmError: Int?
        get() = AuthValidator.passwordConfirmError(password, passwordConfirm)

    val registerEnabled: Boolean
        get() = name.isNotBlank() && AuthValidator.isValidEmail(email) && AuthValidator.isValidPassword(password)
                && AuthValidator.isValidPasswordConfirm(password, passwordConfirm)
}

sealed interface RegisterIntent {
    data class NameChanged(val name: String) : RegisterIntent
    data class EmailChanged(val email: String) : RegisterIntent
    data class PasswordChanged(val password: String) : RegisterIntent
    data class PasswordConfirmChanged(val passwordConfirm: String) : RegisterIntent
    data object RegisterClicked : RegisterIntent
}

sealed interface RegisterSideEffect {
    data class ShowToast(@StringRes val messageRes: Int) : RegisterSideEffect
    data object NavigateToLogin : RegisterSideEffect
}
