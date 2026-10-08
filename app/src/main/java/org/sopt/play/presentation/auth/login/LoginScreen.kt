package org.sopt.play.presentation.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.core.common.extension.noRippleClickable
import org.sopt.play.core.designsystem.component.button.PlayButton
import org.sopt.play.core.designsystem.component.textfield.PlayTextField
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.auth.util.AuthValidator

@Composable
fun LoginScreen(
    emailState: TextFieldState,
    passwordState: TextFieldState,
    loginEnabled: Boolean,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = PlaySoptTheme.colors.white
            )
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))

        Text(
            text = stringResource(R.string.login_title),
            style = PlaySoptTheme.typography.b28,
            color = PlaySoptTheme.colors.black
        )

        Spacer(modifier = Modifier.height(40.dp))

        PlayTextField(
            label = stringResource(R.string.auth_email_label),
            state = emailState,
            placeholder = stringResource(R.string.auth_email_placeholder),
            errorMessage = AuthValidator.emailError(emailState.text.toString())
                ?.let { stringResource(it) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            onKeyboardAction = {
                focusManager.moveFocus(FocusDirection.Next)
            },
        )

        Spacer(modifier = Modifier.height(32.dp))

        PlayTextField(
            label = stringResource(R.string.auth_password_label),
            state = passwordState,
            isPassword = true,
            placeholder = stringResource(R.string.auth_password_placeholder),
            errorMessage = AuthValidator.passwordError(passwordState.text.toString())
                ?.let { stringResource(it) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            onKeyboardAction = {
                keyboardController?.hide()
                focusManager.clearFocus()
            },
        )

        Spacer(modifier = Modifier.height(40.dp))

        PlayButton(
            text = stringResource(R.string.login_button),
            onClick = onLoginClick,
            enabled = loginEnabled
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.login_no_account),
                style = PlaySoptTheme.typography.m14,
                color = PlaySoptTheme.colors.gray3
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = stringResource(R.string.login_go_register),
                style = PlaySoptTheme.typography.m14,
                color = PlaySoptTheme.colors.gray6,
                modifier = Modifier.noRippleClickable {
                    emailState.clearText()
                    passwordState.clearText()
                    onRegisterClick()
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    PlaySoptTheme {
        LoginScreen(
            emailState = rememberTextFieldState(),
            passwordState = rememberTextFieldState(),
            loginEnabled = true,
            onLoginClick = {},
            onRegisterClick = {},
        )
    }
}