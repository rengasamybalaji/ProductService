package com.myRetail.productService.repository

import com.myRetail.productService.dto.ProductPriceEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductPriceRepository extends MongoRepository<ProductPriceEntity, Long> {
}