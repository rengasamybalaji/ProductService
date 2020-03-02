package com.myRetail.productService.client

import com.myRetail.productService.dto.RedskyProduct
import com.myRetail.productService.exception.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class ProductClientSpec extends Specification {

  ProductClient productClient

  RestTemplate mockRestTemplate = Mock(RestTemplate)

  void setup() {
    productClient = new ProductClient(restTemplate: mockRestTemplate)
  }

  def 'successfully retrieves the product info from redSky'() {
    when:
    RedskyProduct product = productClient.fetchProduct(13860435)

    then:
    1 * mockRestTemplate.getForObject(_ as String, RedskyProduct.class) >>
      new RedskyProduct(product: new RedskyProduct.Product(item: new RedskyProduct.Product.Item(productDesc:
        new RedskyProduct.Product.Item.ProductDesc(title: 'Xbox one'))))
    product.product.item.productDesc.title == 'Xbox one'
    0 * _
  }

  def 'when 404 from redSky throw exception'() {
    when:
    productClient.fetchProduct(4334)

    then:
    1 * mockRestTemplate.getForObject(_ as String, RedskyProduct.class) >>
      { throw new HttpClientErrorException(HttpStatus.NOT_FOUND, '404') }
    thrown NotFoundException
  }
}
