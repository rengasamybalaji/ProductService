package com.myRetail.productService.service

import com.myRetail.productService.dto.ProductPriceEntity
import com.myRetail.productService.dto.Product
import com.myRetail.productService.dto.RedskyProduct
import com.myRetail.productService.client.ProductClient
import com.myRetail.productService.repository.ProductPriceRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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
    RedskyProduct redskyProduct = productClient.fetchProduct(productId)

    Optional<ProductPriceEntity> optionalProductPriceEntity = productPriceRepository.findById(productId)
    ProductPriceEntity productPriceEntity = optionalProductPriceEntity.orElse(null)

    Product product = new Product(productId: productId,
      productName: redskyProduct?.product?.item?.productDesc?.title,
      currentPrice: productPriceEntity ? new Product.Price(value:  productPriceEntity?.price?.value,
        currencyCode: productPriceEntity?.price?.currencyCode) : null)

    long timeTakenToFetchProduct = System.currentTimeMillis() - now
    metricService.recordTimer("getProduct", timeTakenToFetchProduct)

    return product
  }

  void updateProductPrice(Product product) {
    if (product) {
      productPriceRepository.save(new ProductPriceEntity(productId: product.productId,
        price: new ProductPriceEntity.Price(value: product.currentPrice.value, currencyCode: product.currentPrice.currencyCode)))
    }
  }
}
