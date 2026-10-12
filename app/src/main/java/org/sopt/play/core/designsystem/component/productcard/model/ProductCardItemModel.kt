package org.sopt.play.core.designsystem.component.productcard.model

import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
data class ProductCardItemModel(
    val imgUrl: String,
    val storeName: String,
    val caption: String,
    val discountRate: Int,
    val price: Int,
    val id: String = UUID.randomUUID().toString(),
) {
    companion object {
        val productCardItems = listOf(
            ProductCardItemModel(
                imgUrl = "https://raw.githubusercontent.com/chldmswll/play-sopt-assets/main/img_home_autumn.png",
                storeName = "트랜디어패럴",
                caption = "[카프리1위\uD83C\uDFC6/5부,7부,9부기장/MADE] 클립 스판 슬릿 바이커 카프리 데님 팬츠",
                discountRate = 36,
                price = 28800
            ),
            ProductCardItemModel(
                imgUrl = "https://raw.githubusercontent.com/chldmswll/play-sopt-assets/main/img_home_autumn.png",
                storeName = "슬로우앤드",
                caption = "[\uD83C\uDF41가을/3기장선택!] made. 모먼트 투커버 올데이슬랙스 (+숏/베이직/롱ver) - 4 size",
                discountRate = 50,
                price = 26820
            ),
            ProductCardItemModel(
                imgUrl = "https://raw.githubusercontent.com/chldmswll/play-sopt-assets/main/img_home_autumn.png",
                storeName = "어텀",
                caption = "보울7부티셔츠",
                discountRate = 10,
                price = 17100
            ),
            ProductCardItemModel(
                    imgUrl = "https://raw.githubusercontent.com/chldmswll/play-sopt-assets/main/img_home_autumn.png",
            storeName = "슬로우앤드",
            caption = "[오픈할인중] made. 도브 클래식 탄탄핏 무지셔츠 - 4 color",
            discountRate = 40,
            price = 35200
        )
        )
    }
}
