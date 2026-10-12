package org.sopt.play.presentation.home.model

import androidx.annotation.StringRes
import org.sopt.play.R

enum class HomeMenu(
    @StringRes val title: Int
) {
    DOUBLE_REWARD(R.string.home_menu_double_reward),
    HOME(R.string.home_menu_home),
    SELECTED(R.string.home_menu_selected),
    BRAND(R.string.home_menu_brand),
    BEAUTY(R.string.home_menu_beauty),
    LIFE(R.string.home_menu_life),
    RANKING(R.string.home_menu_ranking),
}
