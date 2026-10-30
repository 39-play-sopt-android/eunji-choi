package org.sopt.play.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.rememberNavBackStack
import org.sopt.play.core.common.navigation.Route
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.di.RepositoryModule
import org.sopt.play.presentation.main.navigation.PlayNavDisplay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val startRoute = if (RepositoryModule.authRepository.isLoggedIn()) Route.MainTab else Route.Login

        setContent {
            PlaySoptTheme {
                val backStack = rememberNavBackStack(startRoute)

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = PlaySoptTheme.colors.white,
                ) { innerPadding ->
                    PlayNavDisplay(
                        backStack = backStack,
                        paddingValues = innerPadding,
                    )
                }
            }
        }
    }
}
