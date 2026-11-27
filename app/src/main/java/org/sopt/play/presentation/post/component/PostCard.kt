package org.sopt.play.presentation.post.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.play.core.common.extension.noRippleCombinedClickable
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

@Composable
fun PostCard(
    title: String,
    content: String,
    author: String,
    createdAt: String,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = PlaySoptTheme.colors.gray1)
            .noRippleCombinedClickable(
                onLongClick = onLongClick,
                onClick = onClick,
            )
            .padding(20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = author,
                style = PlaySoptTheme.typography.m12,
                color = PlaySoptTheme.colors.gray3,
            )

            VerticalDivider(
                modifier = Modifier.height(12.dp),
                thickness = 1.dp,
                color = PlaySoptTheme.colors.gray3,
            )

            Text(
                text = createdAt,
                style = PlaySoptTheme.typography.m12,
                color = PlaySoptTheme.colors.gray3,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            style = PlaySoptTheme.typography.m16,
            color = PlaySoptTheme.colors.gray5,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = content,
            style = PlaySoptTheme.typography.m14,
            color = PlaySoptTheme.colors.gray5,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PostCardListPreview() {
    PlaySoptTheme {
        Column(
            modifier = Modifier
                .background(color = PlaySoptTheme.colors.black)
                .padding(10.dp)
        ) {
            PostCard(
                title = "솝트 36기 안드로이드 파트 모집",
                content = "안드로이드 파트에서 함께 성장할 YB, OB를 모집합니다. 많은 관심 부탁드려요!",
                author = "솝트",
                createdAt = "2026.11.25",
                onClick = {},
                onLongClick = {},
            )
        }
    }
}
