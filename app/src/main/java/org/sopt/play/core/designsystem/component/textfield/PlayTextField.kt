package org.sopt.play.core.designsystem.component.textfield

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

@Composable
fun PlayTextField(
    label: String,
    state: TextFieldState,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    placeholder: String = "",
    errorMessage: String? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.SingleLine,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val isError = !errorMessage.isNullOrEmpty()

    var lastErrorMessage by remember { mutableStateOf("") }
    if (isError) lastErrorMessage = errorMessage

    val textStyle = PlaySoptTheme.typography.m18
    val textColor = if (isError) PlaySoptTheme.colors.gray6
    else PlaySoptTheme.colors.gray5

    BasicTextField(
        state = state,
        interactionSource = interactionSource,
        modifier = modifier.fillMaxWidth(),
        lineLimits = lineLimits,
        textStyle = textStyle.copy(color = textColor),
        cursorBrush = SolidColor(value = textColor),
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        outputTransformation = if (isPassword) PasswordOutputTransformation else null,
        decorator = { innerTextField ->
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = label,
                    style = PlaySoptTheme.typography.sb16,
                    color = PlaySoptTheme.colors.gray6,
                    modifier = Modifier.padding(start = 8.dp),
                )

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            shape = RoundedCornerShape(size = 12.dp),
                            color = when {
                                isError -> PlaySoptTheme.colors.red
                                isFocused -> PlaySoptTheme.colors.gray5
                                else -> PlaySoptTheme.colors.gray2
                            }
                        )
                        .padding(all = 16.dp),
                ) {
                    if (state.text.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = textStyle,
                            color = PlaySoptTheme.colors.gray2,
                        )
                    }

                    innerTextField()
                }

                AnimatedVisibility(
                    visible = isError,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut(),
                ) {
                    Text(
                        text = lastErrorMessage,
                        style = PlaySoptTheme.typography.m14,
                        color = PlaySoptTheme.colors.red,
                        modifier = Modifier.padding(start = 8.dp, top = 6.dp),
                    )
                }
            }
        },
    )
}

private val PasswordOutputTransformation = OutputTransformation {
    for (i in 0 until length) {
        replace(i, i + 1, "•")
    }
}

@Preview(showBackground = true)
@Composable
private fun PlayTextFieldPreview() {
    PlaySoptTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PlayTextField(
                label = "이메일 주소",
                state = rememberTextFieldState(),
                placeholder = "abc@email.com",
            )

            PlayTextField(
                label = "이메일 주소",
                state = rememberTextFieldState("abc@email.com"),
                placeholder = "abc@email.com",
            )

            PlayTextField(
                label = "이메일 주소",
                state = rememberTextFieldState("abc.com"),
                placeholder = "abc@email.com",
                errorMessage = "올바른 이메일을 입력해주세요.",
            )

            PlayTextField(
                label = "비밀번호",
                state = rememberTextFieldState("password123"),
                isPassword = true,
                placeholder = "6자 이상의 비밀번호",
            )

            PlayTextField(
                label = "이메일 주소",
                state = rememberTextFieldState("very.long.email.address.for.preview@example.com"),
                placeholder = "abc@email.com",
            )
        }
    }
}