package org.sopt.play.presentation.main.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import org.sopt.play.core.common.navigation.Route
import org.sopt.play.presentation.auth.login.LoginRoute
import org.sopt.play.presentation.auth.register.RegisterRoute
import org.sopt.play.presentation.maintab.MainTabRoute

@Composable
fun PlayNavDisplay(
    backStack: NavBackStack<NavKey>,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
) {
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        modifier = modifier,
        entryProvider = entryProvider {
            entry<Route.Login> {
                LoginRoute(
                    paddingValues = paddingValues,
                    navigateToRegister = { backStack.add(Route.Register) },
                    navigateToHome = {
                        backStack.clear()
                        backStack.add(Route.MainTab)
                    },
                )
            }

            entry<Route.Register> {
                RegisterRoute(
                    paddingValues = paddingValues,
                    navigateToLogin = {
                        backStack.removeLastOrNull()
                    },
                )
            }

            entry<Route.MainTab> {
                MainTabRoute(
                    paddingValues = paddingValues
                )
            }
        }
    )
}
