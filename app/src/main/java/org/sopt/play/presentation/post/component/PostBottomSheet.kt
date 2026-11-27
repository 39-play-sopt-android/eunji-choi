package org.sopt.play.presentation.post.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.R
import org.sopt.play.core.designsystem.component.bottomsheet.PlayBottomSheet
import org.sopt.play.core.designsystem.component.button.PlayButton
import org.sopt.play.core.designsystem.component.model.PlayButtonType
import org.sopt.play.core.designsystem.component.textfield.PlayTextField
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.post.model.PostPolicy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostBottomSheet(
    titleState: TextFieldState,
    contentState: TextFieldState,
    completeEnabled: Boolean,
    onDismiss: () -> Unit,
    onCompleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PlayBottomSheet(
        onDismiss = onDismiss,
        modifier = modifier
    ) {
        PostBottomSheetContent(
            titleState = titleState,
            contentState = contentState,
            completeEnabled = completeEnabled,
            onCompleteClick = onCompleteClick,
        )
    }
}

@Composable
private fun PostBottomSheetContent(
    titleState: TextFieldState,
    contentState: TextFieldState,
    completeEnabled: Boolean,
    onCompleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        PlayTextField(
            label = stringResource(R.string.post_title_label),
            state = titleState,
            placeholder = stringResource(R.string.post_title_placeholder),
            maxLength = PostPolicy.TITLE_MAX_LENGTH,
        )

        Spacer(modifier = Modifier.height(12.dp))

        PlayTextField(
            label = stringResource(R.string.post_content_label),
            state = contentState,
            placeholder = stringResource(R.string.post_content_placeholder),
            maxLength = PostPolicy.CONTENT_MAX_LENGTH,
        )

        Spacer(modifier = Modifier.height(20.dp))

        PlayButton(
            text = stringResource(R.string.post_complete_button),
            type = if (completeEnabled) PlayButtonType.PRIMARY_ACTIVE else PlayButtonType.DISABLED,
            onClick = onCompleteClick,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PostBottomSheetContentPreview() {
    PlaySoptTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp),
        ) {
            PostBottomSheetContent(
                titleState = rememberTextFieldState(),
                contentState = rememberTextFieldState(),
                completeEnabled = false,
                onCompleteClick = {},
            )

            PostBottomSheetContent(
                titleState = rememberTextFieldState("오늘의 플레이리스트"),
                contentState = rememberTextFieldState("비 오는 날 듣기 좋은 노래 모음"),
                completeEnabled = true,
                onCompleteClick = {},
            )
        }
    }
}
