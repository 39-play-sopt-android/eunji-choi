package org.sopt.play.presentation.home.model

import androidx.compose.runtime.Immutable

@Immutable
data class ProductModel(
    val id: String,
    val imageUrl: String,
    val storeName: String,
    val caption: String,
    val discountRate: Int,
    val price: Int,
)
