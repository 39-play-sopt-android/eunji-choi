package org.sopt.play.presentation.auth.login

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.play.core.common.extension.toast

@Composable
fun LoginRoute(
    paddingValues: PaddingValues,
    registeredEmail: String,
    registeredPassword: String,
    navigateToRegister: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: LoginViewModel = viewModel { LoginViewModel(registeredEmail, registeredPassword) }
){
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val emailState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()

    LaunchedEffect(emailState) {
        snapshotFlow { emailState.text.toString() }
            .collect { viewModel.onIntent(LoginIntent.EmailChanged(it)) }
    }

    LaunchedEffect(passwordState) {
        snapshotFlow { passwordState.text.toString() }
            .collect { viewModel.onIntent(LoginIntent.PasswordChanged(it)) }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is LoginSideEffect.ShowToast -> context.toast(effect.messageRes)
                LoginSideEffect.NavigateToHome -> navigateToHome()
            }
        }
    }

    LoginScreen(
        emailState = emailState,
        passwordState = passwordState,
        emailErrorMessage = state.emailError?.let { stringResource(it) },
        passwordErrorMessage = state.passwordError?.let { stringResource(it)},
        loginEnabled = state.loginEnabled,
        onLoginClick = { viewModel.onIntent(LoginIntent.LoginClicked) },
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
