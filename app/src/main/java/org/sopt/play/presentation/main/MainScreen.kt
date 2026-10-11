package org.sopt.play.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.rememberNavBackStack
import org.sopt.play.core.common.navigation.MainTabRoute
import org.sopt.play.core.common.navigation.Route
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.main.navigation.PlayNavDisplay

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val backStack = rememberNavBackStack(Route.Login)
    val currentKey = backStack.lastOrNull()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (currentKey is MainTabRoute) {
                // 바텀 네비게이션 바
            }
        },
    ) { innerPadding ->
        PlayNavDisplay(
            backStack = backStack,
            paddingValues = innerPadding,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    PlaySoptTheme {
        MainScreen()
    }
}