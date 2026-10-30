package org.sopt.play.domain.model

data class ProductModel(
    val id: String,
    val imageUrl: String,
    val storeName: String,
    val caption: String,
    val discountRate: Int,
    val price: Int,
)
