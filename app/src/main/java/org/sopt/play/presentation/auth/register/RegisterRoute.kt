package org.sopt.play.presentation.auth.register

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
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
fun RegisterRoute(
    paddingValues: PaddingValues,
    navigateToLogin: (name: String, email: String, password: String) -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val nameState = rememberTextFieldState()
    val emailState = rememberTextFieldState()
    val passwordState = rememberTextFieldState()
    val passwordConfirmState = rememberTextFieldState()

    LaunchedEffect(nameState) {
        snapshotFlow { nameState.text.toString() }
            .collect { viewModel.onIntent(RegisterIntent.NameChanged(it)) }
    }
    
    LaunchedEffect(emailState) {
        snapshotFlow { emailState.text.toString() }
            .collect { viewModel.onIntent(RegisterIntent.EmailChanged(it)) }
    }

    LaunchedEffect(passwordState) {
        snapshotFlow { passwordState.text.toString() }
            .collect { viewModel.onIntent(RegisterIntent.PasswordChanged(it)) }
    }

    LaunchedEffect(passwordConfirmState) {
        snapshotFlow { passwordConfirmState.text.toString() }
            .collect { viewModel.onIntent(RegisterIntent.PasswordConfirmChanged(it)) }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is RegisterSideEffect.ShowToast -> context.toast(effect.messageRes)
                RegisterSideEffect.NavigateToLogin -> navigateToLogin(state.name, state.email, state.password)
            }
        }
    }

    RegisterScreen(
        nameState = nameState,
        emailState = emailState,
        passwordState = passwordState,
        passwordConfirmState = passwordConfirmState,
        emailErrorMessage = state.emailError?.let { stringResource(it) },
        passwordErrorMessage = state.passwordError?.let { stringResource(it) },
        passwordConfirmErrorMessage = state.passwordConfirmError?.let { stringResource(it) },
        registerEnabled = state.registerEnabled,
        onRegisterClick = { viewModel.onIntent(RegisterIntent.RegisterClicked) },
        modifier = Modifier
            .padding(paddingValues)
            .consumeWindowInsets(paddingValues),
    )
}
