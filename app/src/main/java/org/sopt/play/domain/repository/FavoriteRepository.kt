package org.sopt.play.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sopt.play.domain.model.ProductModel

interface FavoriteRepository {
    fun getFavorites(): Flow<Result<List<ProductModel>>>

    suspend fun insert(product: ProductModel): Result<Unit>

    suspend fun delete(id: String): Result<Unit>
}
