package org.sopt.play.data.repository

import org.sopt.play.core.util.suspendRunCatching
import org.sopt.play.data.dummy.BannerDummyData
import org.sopt.play.domain.model.BannerModel
import org.sopt.play.domain.repository.BannerRepository

class BannerRepositoryImpl : BannerRepository {
    override suspend fun getBanners(): Result<List<BannerModel>> =
        suspendRunCatching { BannerDummyData.banners }
}
