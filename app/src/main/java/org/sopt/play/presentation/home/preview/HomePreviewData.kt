package org.sopt.play.presentation.home.preview

import org.sopt.play.presentation.home.model.BannerUiModel
import org.sopt.play.presentation.home.model.ProductUiModel

object HomePreviewData {
    val banners = listOf(
        BannerUiModel(id = "1", imageUrl = "https://raw.githubusercontent.com/chldmswll/play-sopt-assets/main/img_home_banner_waiting.png"),
        BannerUiModel(id = "2", imageUrl = "https://raw.githubusercontent.com/chldmswll/play-sopt-assets/main/img_home_banner_western.png"),
        BannerUiModel(id = "3", imageUrl = "https://raw.githubusercontent.com/chldmswll/play-sopt-assets/main/img_home_banner_spring.png"),
    )

    val products = listOf(
        ProductUiModel(
            id = "1",
            imageUrl = "https://raw.githubusercontent.com/chldmswll/play-sopt-assets/main/img_home_trendapparel.png",
            storeName = "트랜디어패럴",
            productName = "[카프리1위🏆/5부,7부,9부기장/MADE] 클립 스판 슬릿 바이커 카프리 데님 팬츠",
            discountRate = 36,
            price = "28,800"
        ),
        ProductUiModel(
            id = "2",
            imageUrl = "https://raw.githubusercontent.com/chldmswll/play-sopt-assets/main/img_home_slowend_pants.png",
            storeName = "슬로우앤드",
            productName = "[🍁가을/3기장선택!] made. 모먼트 투커버 올데이슬랙스 (+숏/베이직/롱ver) - 4 size",
            discountRate = 50,
            price = "26,820"
        ),
        ProductUiModel(
            id = "3",
            imageUrl = "https://raw.githubusercontent.com/chldmswll/play-sopt-assets/main/img_home_autumn.png",
            storeName = "어텀",
            productName = "보울7부티셔츠",
            discountRate = 10,
            price = "17,100"
        ),
        ProductUiModel(
            id = "4",
            imageUrl = "https://raw.githubusercontent.com/chldmswll/play-sopt-assets/main/img_home_slowend_shirts.png",
            storeName = "슬로우앤드",
            productName = "[오픈할인중] made. 도브 클래식 탄탄핏 무지셔츠 - 4 color",
            discountRate = 40,
            price = "35,200"
        )
    )
}
