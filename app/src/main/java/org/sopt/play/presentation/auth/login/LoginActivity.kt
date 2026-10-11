package org.sopt.play.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.clearText
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
import org.sopt.play.presentation.auth.register.RegisterActivity
import org.sopt.play.presentation.auth.util.AuthValidator
import org.sopt.play.presentation.auth.util.EMAIL_KEY
import org.sopt.play.presentation.auth.util.PASSWORD_KEY
import org.sopt.play.presentation.main.MainActivity

class LoginActivity : ComponentActivity() {
    private var resultEmail = ""
    private var resultPassword = ""

    private val registerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            resultEmail = result.data?.getStringExtra(EMAIL_KEY) ?: ""
            resultPassword = result.data?.getStringExtra(PASSWORD_KEY) ?: ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
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

            PlaySoptTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        emailState = emailState,
                        passwordState = passwordState,
                        emailErrorMessage = emailErrorMessage,
                        passwordErrorMessage = passwordErrorMessage,
                        loginEnabled = loginEnabled,
                        onLoginClick = {
                            onLoginClick(
                                emailText = emailState.text.toString(),
                                passwordText = passwordState.text.toString()
                            )
                        },
                        onRegisterClick = {
                            emailState.clearText()
                            passwordState.clearText()

                            registerLauncher.launch(Intent(this, RegisterActivity::class.java))
                        },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @StringRes
    private fun getLoginErrorRes(
        emailText: String,
        passwordText: String,
    ): Int? {
        return if (emailText != resultEmail || passwordText != resultPassword) {
            R.string.login_failure
        } else null
    }

    private fun onLoginClick(
        emailText: String,
        passwordText: String,
    ) {
        val error = getLoginErrorRes(emailText, passwordText)
        if (error != null) {
            Toast.makeText(this, getString(error), Toast.LENGTH_SHORT).show()
            return
        }

        this.toast(getString(R.string.login_success))

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }
}
