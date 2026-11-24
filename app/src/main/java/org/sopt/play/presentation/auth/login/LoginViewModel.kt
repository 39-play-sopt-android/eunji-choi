package org.sopt.play.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.play.R
import org.sopt.play.di.RepositoryModule
import org.sopt.play.domain.repository.AuthException
import org.sopt.play.domain.repository.AuthRepository

class LoginViewModel(
    private val authRepository: AuthRepository = RepositoryModule.authRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _sideEffect = Channel<LoginSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> changeEmail(intent.email)
            is LoginIntent.PasswordChanged -> changePassword(intent.password)
            LoginIntent.LoginClicked -> login()
        }
    }

    private fun login() {
        val state = _state.value

        viewModelScope.launch {
            authRepository.login(state.email, state.password)
                .onSuccess {
                    _sideEffect.send(LoginSideEffect.ShowToast(R.string.login_success))
                    _sideEffect.send(LoginSideEffect.NavigateToHome)
                }
                .onFailure { error ->
                    val message = when (error) {
                        is AuthException.EmailNotFound -> R.string.login_failure_email_not_found
                        is AuthException.NoAccountFound -> R.string.login_failure_no_account
                        is AuthException.PasswordMismatch -> R.string.login_failure_password_mismatch
                        else -> R.string.login_failure
                    }
                    _sideEffect.send(LoginSideEffect.ShowToast(message))
                }
        }
    }

    private fun changeEmail(email: String) {
        _state.update { state ->
            state.copy(email = email)
        }
    }

    private fun changePassword(password: String) {
        _state.update { state ->
            state.copy(password = password)
        }
    }
}
