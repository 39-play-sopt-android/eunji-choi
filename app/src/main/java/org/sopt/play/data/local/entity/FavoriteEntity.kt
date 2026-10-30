package org.sopt.play.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey val id: String,
    val imageUrl: String,
    val storeName: String,
    val caption: String,
    val discountRate: Int,
    val price: Int,
)
