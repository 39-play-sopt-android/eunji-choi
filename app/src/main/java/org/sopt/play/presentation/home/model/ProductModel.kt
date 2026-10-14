package org.sopt.play.presentation.home.model

import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
data class ProductModel(
    val imageUrl: String,
    val storeName: String,
    val caption: String,
    val discountRate: Int,
    val price: Int,
    val id: String = UUID.randomUUID().toString(),
)
