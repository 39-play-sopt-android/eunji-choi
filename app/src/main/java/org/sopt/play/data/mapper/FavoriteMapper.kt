package org.sopt.play.data.mapper

import org.sopt.play.data.local.entity.FavoriteEntity
import org.sopt.play.domain.model.ProductModel

fun FavoriteEntity.toModel() = ProductModel(
    id = this.id,
    imageUrl = this.imageUrl,
    storeName = this.storeName,
    caption = this.caption,
    discountRate = this.discountRate,
    price = this.price,
)

fun ProductModel.toEntity() = FavoriteEntity(
    id = this.id,
    imageUrl = this.imageUrl,
    storeName = this.storeName,
    caption = this.caption,
    discountRate = this.discountRate,
    price = this.price,
)
