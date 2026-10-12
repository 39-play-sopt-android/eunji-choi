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
)
