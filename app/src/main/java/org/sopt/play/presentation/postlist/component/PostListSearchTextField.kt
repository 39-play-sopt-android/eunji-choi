package org.sopt.play.presentation.postlist.component

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

@Composable
fun PostListSearchTextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    placeholder: String = "검색어를 입력하세요",
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.SingleLine,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
) {
    val isEmpty by remember(state) { derivedStateOf { state.text.isEmpty() } }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val textStyle = PlaySoptTheme.typography.m18
    val textColor = PlaySoptTheme.colors.gray5

    BasicTextField(
        state = state,
        interactionSource = interactionSource,
        modifier = modifier.fillMaxWidth(),
        lineLimits = lineLimits,
        textStyle = textStyle.copy(color = textColor),
        cursorBrush = SolidColor(value = textColor),
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        decorator = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        shape = RoundedCornerShape(12.dp),
                        color = when {
                            isFocused -> PlaySoptTheme.colors.gray5
                            else -> PlaySoptTheme.colors.gray2
                        }
                    )
                    .padding(all = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (isEmpty) {
                        Text(
                            text = placeholder,
                            style = textStyle,
                            color = PlaySoptTheme.colors.gray2,
                            maxLines = 1,
                        )
                    }
                    innerTextField()
                }

                Icon(
                    painter = painterResource(R.drawable.ic_search_28),
                    tint = PlaySoptTheme.colors.gray3,
                    contentDescription = "검색"
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun PostListSearchTextFieldPreview() {
    PlaySoptTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PostListSearchTextField(
                state = rememberTextFieldState(),
            )

            PostListSearchTextField(
                state = rememberTextFieldState("솝트"),
            )
        }
    }
}
