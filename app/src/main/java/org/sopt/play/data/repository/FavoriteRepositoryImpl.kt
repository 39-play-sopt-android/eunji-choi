package org.sopt.play.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.sopt.play.data.local.dao.FavoriteDao
import org.sopt.play.data.mapper.toEntity
import org.sopt.play.data.mapper.toModel
import org.sopt.play.domain.repository.FavoriteRepository
import org.sopt.play.domain.model.ProductModel

class FavoriteRepositoryImpl(
    private val favoriteDao: FavoriteDao,
) : FavoriteRepository {
    override fun getFavorites(): Flow<Result<List<ProductModel>>> =
        favoriteDao.getFavorites()
            .map { entities -> Result.success(entities.map { it.toModel() }) }
            .catch { emit(Result.failure(it)) }

    override fun getFavoriteIds(): Flow<Result<Set<String>>> =
        favoriteDao.getFavoriteIds()
            .map { ids -> Result.success(ids.toSet()) }
            .catch { emit(Result.failure(it)) }

    override suspend fun insert(product: ProductModel): Result<Unit> =
        runCatching { favoriteDao.insert(product.toEntity()) }

    override suspend fun delete(id: String): Result<Unit> =
        runCatching { favoriteDao.deleteById(id) }
}
