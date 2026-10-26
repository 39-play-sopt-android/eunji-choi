package org.sopt.play.presentation.auth.register

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

class RegisterViewModel : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    private val _sideEffect = Channel<RegisterSideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onIntent(intent: RegisterIntent) {
        when (intent) {
            is RegisterIntent.NameChanged -> changeName(intent.name)
            is RegisterIntent.EmailChanged -> changeEmail(intent.email)
            is RegisterIntent.PasswordChanged -> changePassword(intent.password)
            is RegisterIntent.PasswordConfirmChanged -> changePasswordConfirm(intent.passwordConfirm)
            RegisterIntent.RegisterClicked -> register()
        }
    }

    private fun register() {
        // 로컬 스토리지에 회원 정보 저장
        viewModelScope.launch {
            _sideEffect.send(RegisterSideEffect.ShowToast(R.string.register_success))
            _sideEffect.send(RegisterSideEffect.NavigateToLogin)
        }
    }

    private fun changeName(name: String) {
        _state.update { state ->
            state.copy(name = name)
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

    private fun changePasswordConfirm(passwordConfirm: String) {
        _state.update { state ->
            state.copy(passwordConfirm = passwordConfirm)
        }
    }
}
