package org.sopt.play.presentation.home.model

import androidx.annotation.StringRes
import org.sopt.play.R

enum class HomeCategory(
    @StringRes val title: Int
) {
    WHOLE(R.string.home_category_whole),
    TOP(R.string.home_category_top),
    PANTS(R.string.home_category_pants),
    ONEPIECE(R.string.home_category_onepiece),
    OUTER(R.string.home_category_outer),
    SKIRT(R.string.home_category_skirt),
    SHOES(R.string.home_category_shoes),
    BAG(R.string.home_category_bag),
    INNERWEAR(R.string.home_category_innerwear),
    PHONE_DECO(R.string.home_category_phone_deco),
}

