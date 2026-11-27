package org.sopt.play.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.play.data.repository.AuthRepositoryImpl
import org.sopt.play.data.repository.BannerRepositoryImpl
import org.sopt.play.data.repository.FavoriteRepositoryImpl
import org.sopt.play.data.repository.PostRepositoryImpl
import org.sopt.play.data.repository.ProductRepositoryImpl
import org.sopt.play.domain.repository.AuthRepository
import org.sopt.play.domain.repository.BannerRepository
import org.sopt.play.domain.repository.FavoriteRepository
import org.sopt.play.domain.repository.PostRepository
import org.sopt.play.domain.repository.ProductRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(impl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindBannerRepository(impl: BannerRepositoryImpl): BannerRepository

    @Binds
    @Singleton
    abstract fun bindPostRepository(impl: PostRepositoryImpl): PostRepository
}
