package org.sopt.play.presentation.auth.login

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.clearText
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
fun LoginRoute(
    paddingValues: PaddingValues,
    registeredEmail: String,
    registeredPassword: String,
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
){
    val context = LocalContext.current
    val emailState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()

    val loginEnabled by remember {
        derivedStateOf {
            AuthValidator.isValidEmail(emailState.text.toString()) &&
                    AuthValidator.isValidPassword(passwordState.text.toString())
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

    val loginFailureMessage = stringResource(R.string.login_failure)
    val loginSuccessMessage = stringResource(R.string.login_success)

    LoginScreen(
        emailState = emailState,
        passwordState = passwordState,
        emailErrorMessage = emailErrorMessage,
        passwordErrorMessage = passwordErrorMessage,
        loginEnabled = loginEnabled,
        onLoginClick = {
            val isLoginFailed = emailState.text.toString() != registeredEmail || passwordState.text.toString() != registeredPassword

            if (isLoginFailed) {
                context.toast(loginFailureMessage)
                return@LoginScreen
            }

            context.toast(loginSuccessMessage)
            navigateToHome()
        },
        onRegisterClick = {
            emailState.clearText()
            passwordState.clearText()

            navigateToRegister()
        },
        modifier = Modifier
            .padding(paddingValues)
            .consumeWindowInsets(paddingValues)
    )
}
