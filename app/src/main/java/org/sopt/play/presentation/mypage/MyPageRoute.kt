package org.sopt.play.presentation.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.sopt.play.core.designsystem.theme.PlaySoptTheme

@Composable
fun MyPageRoute(
    paddingValues: PaddingValues,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = PlaySoptTheme.colors.white)
            .padding(paddingValues),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "마이페이지",
            style = PlaySoptTheme.typography.m18,
            color = PlaySoptTheme.colors.black,
        )
    }
}
