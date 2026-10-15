package org.sopt.play.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.rememberNavBackStack
import org.sopt.play.core.common.navigation.Route
import org.sopt.play.presentation.main.navigation.PlayNavDisplay

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val backStack = rememberNavBackStack(Route.Login)

    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        PlayNavDisplay(
            backStack = backStack,
            paddingValues = innerPadding,
        )
    }
}
