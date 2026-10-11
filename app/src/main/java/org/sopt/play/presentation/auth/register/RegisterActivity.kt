package org.sopt.play.presentation.auth.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.sopt.play.R
import org.sopt.play.core.common.extension.toast
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.auth.util.AuthValidator
import org.sopt.play.presentation.auth.util.EMAIL_KEY
import org.sopt.play.presentation.auth.util.PASSWORD_KEY

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
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

            PlaySoptTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
                            onRegisterClick(
                                emailText = emailState.text.toString(),
                                passwordText = passwordState.text.toString(),
                            )
                        },
                        modifier = Modifier
                            .padding(innerPadding)
                            .consumeWindowInsets(innerPadding),
                    )
                }
            }
        }
    }

    private fun onRegisterClick(
        emailText: String,
        passwordText: String,
    ) {

        this.toast(getString(R.string.register_success))

        val intent = Intent()
            .putExtra(EMAIL_KEY, emailText)
            .putExtra(PASSWORD_KEY, passwordText)

        setResult(RESULT_OK, intent)
        finish()
    }
}