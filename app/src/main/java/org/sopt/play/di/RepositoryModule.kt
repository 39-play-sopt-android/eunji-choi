package org.sopt.play.di

import org.sopt.play.domain.repository.AuthRepository
import org.sopt.play.domain.repository.FavoriteRepository
import org.sopt.play.data.repository.AuthRepositoryImpl
import org.sopt.play.data.repository.FavoriteRepositoryImpl

object RepositoryModule {
    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(LocalModule.authLocalDataSource)
    }

    val favoriteRepository: FavoriteRepository by lazy {
        FavoriteRepositoryImpl(LocalModule.favoriteDao)
    }
}
