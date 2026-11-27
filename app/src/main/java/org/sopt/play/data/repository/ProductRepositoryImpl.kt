package org.sopt.play.data.repository

import org.sopt.play.core.util.suspendRunCatching
import org.sopt.play.data.dummy.ProductDummyData
import org.sopt.play.domain.model.ProductModel
import org.sopt.play.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor() : ProductRepository {
    override suspend fun getProducts(): Result<List<ProductModel>> =
        suspendRunCatching { ProductDummyData.products }
}
