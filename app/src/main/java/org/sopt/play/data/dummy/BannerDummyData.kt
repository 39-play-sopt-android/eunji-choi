package org.sopt.play.data.dummy

import org.sopt.play.presentation.home.model.BannerModel

object BannerDummyData {
    val banners = listOf(
        BannerModel(id = "1", imageUrl = "https://raw.githubusercontent.com/chldmswll/play-sopt-assets/main/img_home_banner_waiting.png"),
        BannerModel(id = "2", imageUrl = "https://raw.githubusercontent.com/chldmswll/play-sopt-assets/main/img_home_banner_western.png"),
        BannerModel(id = "3", imageUrl = "https://raw.githubusercontent.com/chldmswll/play-sopt-assets/main/img_home_banner_spring.png"),
    )
}
