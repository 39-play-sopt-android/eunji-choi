package org.sopt.play.presentation.main.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import org.sopt.play.core.common.navigation.MainTabRoute
import org.sopt.play.core.common.navigation.Route
import org.sopt.play.presentation.auth.login.LoginRoute
import org.sopt.play.presentation.auth.register.RegisterRoute
import org.sopt.play.presentation.home.HomeRoute
import org.sopt.play.presentation.like.LikeRoute
import org.sopt.play.presentation.mypage.MyPageRoute

@Composable
fun PlayNavDisplay(
    backStack: NavBackStack<NavKey>,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    var registeredEmail by rememberSaveable { mutableStateOf("") }
    var registeredPassword by rememberSaveable { mutableStateOf("") }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        modifier = modifier,
        entryProvider = entryProvider {
            entry<Route.Login> {
                LoginRoute(
                    paddingValues = paddingValues,
                    registeredEmail = registeredEmail,
                    registeredPassword = registeredPassword,
                    navigateToRegister = { backStack.add(Route.Register) },
                    navigateToHome = {
                        backStack.clear()
                        backStack.add(MainTabRoute.Home)
                    },
                )
            }

            entry<Route.Register> {
                RegisterRoute(
                    paddingValues = paddingValues,
                    navigateToLogin = { email, password ->
                        registeredEmail = email
                        registeredPassword = password
                        backStack.removeLastOrNull()
                    },
                )
            }

            entry<MainTabRoute.Home> {
                HomeRoute(paddingValues = paddingValues)
            }

            entry<MainTabRoute.Like> {
                LikeRoute(paddingValues = paddingValues)
            }

            entry<MainTabRoute.MyPage> {
                MyPageRoute(paddingValues = paddingValues)
            }
        }
    )
}
