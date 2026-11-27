package org.sopt.play.data.repository

import org.sopt.play.core.util.suspendRunCatching
import org.sopt.play.data.dummy.BannerDummyData
import org.sopt.play.domain.model.BannerModel
import org.sopt.play.domain.repository.BannerRepository
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor() : BannerRepository {
    override suspend fun getBanners(): Result<List<BannerModel>> =
        suspendRunCatching { BannerDummyData.banners }
}
