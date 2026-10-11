package org.sopt.play.presentation.auth.register

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import org.sopt.play.R
import org.sopt.play.core.common.extension.toast
import org.sopt.play.presentation.auth.util.AuthValidator

@Composable
fun RegisterRoute(
    paddingValues: PaddingValues,
    navigateToLogin: () -> Unit,
) {
    val context = LocalContext.current
    val nameState = rememberTextFieldState()
    val emailState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()
    val passwordConfirmState = rememberTextFieldState()

    val registerEnabled by remember {
        derivedStateOf {
            val password = passwordState.text.toString()
            nameState.text.isNotBlank() &&
                    AuthValidator.isValidEmail(emailState.text.toString()) &&
                    AuthValidator.isValidPassword(password) &&
                    AuthValidator.isValidPasswordConfirm(
                        password,
                        passwordConfirmState.text.toString()
                    )
        }
    }

    val isEmailError by remember {
        derivedStateOf { AuthValidator.emailError(emailState.text.toString()) }
    }
    val emailErrorMessage = isEmailError?.let { stringResource(it) }

    val isPasswordError by remember {
        derivedStateOf { AuthValidator.passwordError(passwordState.text.toString()) }
    }
    val passwordErrorMessage = isPasswordError?.let { stringResource(it) }

    val isPasswordConfirmError by remember {
        derivedStateOf {
            AuthValidator.passwordConfirmError(
                passwordState.text.toString(), passwordConfirmState.text.toString()
            )
        }
    }
    val passwordConfirmErrorMessage = isPasswordConfirmError?.let { stringResource(it) }

    val registerSuccessMessage = stringResource(R.string.register_success)

    RegisterScreen(
        nameState = nameState,
        emailState = emailState,
        passwordState = passwordState,
        passwordConfirmState = passwordConfirmState,
        emailErrorMessage = emailErrorMessage,
        passwordErrorMessage = passwordErrorMessage,
        passwordConfirmErrorMessage = passwordConfirmErrorMessage,
        registerEnabled = registerEnabled,
        onRegisterClick = {
            context.toast(registerSuccessMessage)
            navigateToLogin()
        },
        modifier = Modifier
            .padding(paddingValues)
            .consumeWindowInsets(paddingValues),
    )
}