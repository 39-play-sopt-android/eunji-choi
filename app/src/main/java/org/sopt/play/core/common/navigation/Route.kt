package org.sopt.play.core.common.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Route : NavKey {
    @Serializable
    data object Login : Route

    @Serializable
    data object Register : Route
}