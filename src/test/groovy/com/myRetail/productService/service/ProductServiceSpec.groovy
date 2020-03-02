package com.myRetail.productService.service

import com.myRetail.productService.dto.Product
import com.myRetail.productService.dto.RedskyProduct
import com.myRetail.productService.client.ProductClient
import com.myRetail.productService.dto.ProductPriceEntity
import spock.lang.Specification
import spock.lang.Unroll

class ProductServiceSpec extends Specification {

  ProductService productService

  com.myRetail.productService.repository.ProductPriceRepository mockProductPriceRepository = Mock(com.myRetail.productService.repository.ProductPriceRepository)

  ProductClient mockProductClient = Mock(ProductClient)

  MetricService mockMetricService = Mock(MetricService)

  void setup() {
    productService = new ProductService(productPriceRepository: mockProductPriceRepository,
    productClient: mockProductClient, metricService: mockMetricService)
  }

  @Unroll
  def 'get a valid product for #scenario'() {
    given:
    RedskyProduct redskyProduct = new RedskyProduct(product: new RedskyProduct.Product(
      item: new RedskyProduct.Product.Item(productDesc: new RedskyProduct.Product.Item.ProductDesc(title: 'Xbox One')) ))

    when:
    Product product = productService.getProduct(productId)

    then:
    1 * mockProductClient.fetchProduct(productId) >> redskyProduct
    1 * mockProductPriceRepository.findById(productId) >> Optional.ofNullable(productPriceEntity)
    1 * mockMetricService.recordTimer("getProduct", _ as Long)
    product.productId == productId
    product.productName == 'Xbox One'
    if (productPriceEntity) {
      product.currentPrice
      product.currentPrice.value == 100.99
      product.currentPrice.currencyCode == 'USD'
    } else {
      !product.currentPrice
    }
    0 * _

    where:
    scenario                              | productId | productPriceEntity
    'when price available in db for id'   | 13860429  | new ProductPriceEntity(productId: 13860429, price: new ProductPriceEntity.Price(value: 100.99, currencyCode: 'USD'))
    'when there is no price in db for id' | 13860429  | null
  }

  def 'update product'() {
    when:
    productService.updateProductPrice(new Product(productId: 13860435, currentPrice: new Product.Price(value: 2.99, currencyCode: 'USD')))

    then:
    1 * mockProductPriceRepository.save(_ as ProductPriceEntity) >> {
      ProductPriceEntity productPriceEntity ->
      assert productPriceEntity.productId == 13860435
      assert productPriceEntity.price.value == 2.99
      assert productPriceEntity.price.currencyCode == 'USD'
    }
    0 * _
  }
}
