package org.sopt.play.presentation.home.model

import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
data class BannerModel(
    val imageUrl: String,
    val id: String = UUID.randomUUID().toString()
)
