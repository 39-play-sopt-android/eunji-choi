package org.sopt.play.presentation.main.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.sopt.play.R


enum class MainTab(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unselectedIcon: Int,
    @StringRes val label: Int,
) {
    HOME(
        selectedIcon = R.drawable.ic_home_filled_24,
        unselectedIcon = R.drawable.ic_home_unfilled_24,
        label = R.string.main_tab_home,
    ),
    LIKE(
        selectedIcon = R.drawable.ic_favorite_filled_24,
        unselectedIcon = R.drawable.ic_favorite_unfilled_24,
        label = R.string.main_tab_like,
    ),
    MYPAGE(
        selectedIcon = R.drawable.ic_user_filled_24,
        unselectedIcon = R.drawable.ic_user_unfilled_24,
        label = R.string.main_tab_mypage,
    );
}
