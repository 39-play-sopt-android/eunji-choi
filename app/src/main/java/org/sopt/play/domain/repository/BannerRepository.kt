package org.sopt.play.domain.repository

import org.sopt.play.domain.model.BannerModel

interface BannerRepository {
    suspend fun getBanners(): Result<List<BannerModel>>
}
