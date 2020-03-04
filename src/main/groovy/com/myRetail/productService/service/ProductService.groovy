package com.myRetail.productService.service

import com.myRetail.productService.client.ProductClient
import com.myRetail.productService.dto.Product
import com.myRetail.productService.dto.ProductPriceEntity
import com.myRetail.productService.dto.RedskyProduct
import com.myRetail.productService.repository.ProductPriceRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.concurrent.CompletableFuture

/**
 * Service class to handle product business functionality
 */
@Service
@Slf4j
class ProductService {

  @Autowired
  ProductClient productClient

  @Autowired
  ProductPriceRepository productPriceRepository

  @Autowired
  MetricService metricService

  Product getProduct(long productId) {

    long now = System.currentTimeMillis()
    CompletableFuture<RedskyProduct> redSkyFuture = CompletableFuture.supplyAsync {
      return productClient.fetchProduct(productId)
      }

    CompletableFuture<ProductPriceEntity> dbFuture = CompletableFuture.supplyAsync {
      Optional<ProductPriceEntity> optionalProductPriceEntity = productPriceRepository.findById(productId)
      return optionalProductPriceEntity.orElse(null)
      }.handle { productPriceEntity, ex ->
      if (ex) {
        metricService.recordErrorCountAndLogging('DB price fetch failed', ex.message)
        return null
      }
      return productPriceEntity
      }

    CompletableFuture<Product> combinedFuture = redSkyFuture.thenCombine(dbFuture)
      {
        redskyProduct, productPriceEntity -> Product product = new Product(productId: productId,
        productName: redskyProduct?.product?.item?.productDesc?.title,
        currentPrice: productPriceEntity ? new Product.Price(value: productPriceEntity?.price?.value,
        currencyCode: productPriceEntity?.price?.currencyCode) : null)
      return product
      }

    long timeTakenToFetchProduct = System.currentTimeMillis() - now
    metricService.recordTimer("getProduct", timeTakenToFetchProduct)

    return combinedFuture.get()
  }

  void updateProductPrice(Product product) {
    if (product) {
      productPriceRepository.save(new ProductPriceEntity(productId: product.productId,
        price: new ProductPriceEntity.Price(value: product.currentPrice.value, currencyCode: product.currentPrice.currencyCode)))
    }
  }
}
