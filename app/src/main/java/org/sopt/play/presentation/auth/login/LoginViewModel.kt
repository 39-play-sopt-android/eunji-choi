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

class LoginViewModel(
    private val registeredEmail: String,
    private val registeredPassword: String,
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
        val isLoginFailed = state.email != registeredEmail || state.password != registeredPassword

        viewModelScope.launch {
            if (isLoginFailed) {
                _sideEffect.send(LoginSideEffect.ShowToast(R.string.login_failure))
                return@launch
            }

            _sideEffect.send(LoginSideEffect.ShowToast(R.string.login_success))
            _sideEffect.send(LoginSideEffect.NavigateToHome)
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
