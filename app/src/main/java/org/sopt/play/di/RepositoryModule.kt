package org.sopt.play.di

import org.sopt.play.data.repository.AuthRepositoryImpl
import org.sopt.play.data.repository.BannerRepositoryImpl
import org.sopt.play.data.repository.FavoriteRepositoryImpl
import org.sopt.play.data.repository.ProductRepositoryImpl
import org.sopt.play.domain.repository.AuthRepository
import org.sopt.play.domain.repository.BannerRepository
import org.sopt.play.domain.repository.FavoriteRepository
import org.sopt.play.domain.repository.ProductRepository

object RepositoryModule {
    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(LocalModule.authLocalDataSource)
    }

    val favoriteRepository: FavoriteRepository by lazy {
        FavoriteRepositoryImpl(LocalModule.favoriteDao)
    }

    val productRepository: ProductRepository by lazy {
        ProductRepositoryImpl()
    }

    val bannerRepository: BannerRepository by lazy {
        BannerRepositoryImpl()
    }
}
