package org.sopt.play.presentation.auth.util

import android.util.Patterns
import androidx.annotation.StringRes
import org.sopt.play.R

object AuthValidator {
    fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassword(password: String): Boolean = password.length >= 6

    fun isValidPasswordConfirm(password: String, passwordConfirm: String): Boolean =
        passwordConfirm == password

    @StringRes
    fun emailError(email: String): Int? = when {
        email.isEmpty() -> null
        isValidEmail(email) -> null
        else -> R.string.email_error
    }

    @StringRes
    fun passwordError(password: String): Int? = when {
        password.isEmpty() -> null
        isValidPassword(password) -> null
        else -> R.string.password_error
    }

    @StringRes
    fun passwordConfirmError(password: String, passwordConfirm: String): Int? = when {
        passwordConfirm.isEmpty() -> null
        isValidPasswordConfirm(password, passwordConfirm) -> null
        else -> R.string.password_confirm_error
    }
}
