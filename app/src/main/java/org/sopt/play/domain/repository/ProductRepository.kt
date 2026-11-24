package org.sopt.play.domain.repository

import org.sopt.play.domain.model.ProductModel

interface ProductRepository {
    suspend fun getProducts(): Result<List<ProductModel>>
}
