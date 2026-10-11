package org.sopt.play.core.common.navigation

import kotlinx.serialization.Serializable

sealed interface MainTabRoute : Route {
    @Serializable
    data object Home : MainTabRoute

    @Serializable
    data object Like : MainTabRoute

    @Serializable
    data object MyPage : MainTabRoute
}