package org.sopt.play.presentation.postlist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.designsystem.component.button.PlayButton
import org.sopt.play.core.designsystem.component.model.PlayButtonType
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

@Composable
fun PostModal(
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = PlaySoptTheme.colors.white,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "게시글을 삭제할까요?",
            style = PlaySoptTheme.typography.m18,
            color = PlaySoptTheme.colors.gray6,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PlayButton(
                text = "취소",
                type = PlayButtonType.SECONDARY_ACTIVE,
                onClick = onCancelClick,
                modifier = Modifier.weight(1f),
            )

            PlayButton(
                text = "삭제",
                type = PlayButtonType.PRIMARY_ACTIVE,
                onClick = onDeleteClick,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PostModalPreview() {
    PlaySoptTheme {
        Box(
            modifier = Modifier
                .background(color = PlaySoptTheme.colors.black)
                .padding(24.dp),
        ) {
            PostModal(
                onCancelClick = {},
                onDeleteClick = {},
            )
        }
    }
}
