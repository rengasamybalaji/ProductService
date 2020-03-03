package com.myRetail.productService.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.myRetail.productService.dto.Product
import com.myRetail.productService.service.MetricService
import com.myRetail.productService.service.ProductService
import com.myRetail.productService.validator.ProductValidator
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.nio.charset.Charset

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

class ProductControllerSpec extends Specification {

  ProductController productController

  ProductService productService = Mock(ProductService)

  MetricService metricService = Mock(MetricService)

  ProductValidator productValidator = new ProductValidator()

  MockMvc mockMvc

  void setup() {
    productController = new ProductController(productService: productService,
    productValidator: productValidator, metricService: metricService)
    mockMvc = MockMvcBuilders.standaloneSetup(productController).build()
  }

  def 'successfully fetch the response for a given product id'() {

    given:
    Product product = new Product(productId: 12345, productName: 'My Product',
      currentPrice: new Product.Price(value: 2.99, currencyCode: 'USD'))

    when:
    def results  = mockMvc.perform(MockMvcRequestBuilders.get('/v1/products/12345'))

    then:
    1 * productService.getProduct(12345) >> product
    1 * metricService.recordCount(_ as String)
    results.andExpect(MockMvcResultMatchers.status().isOk())
    results.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    results.andExpect(MockMvcResultMatchers.jsonPath('id').value(12345))
    results.andExpect(MockMvcResultMatchers.jsonPath('name').value('My Product'))
    results.andExpect(MockMvcResultMatchers.jsonPath('current_price.value').value(2.99))
    results.andExpect(MockMvcResultMatchers.jsonPath('current_price.currency_code').value('USD'))
    0 * _
  }

  def 'bad request for an invalid product id'() {
    expect:
    mockMvc.perform(MockMvcRequestBuilders.get('/v1/products/abc'))
      .andExpect(MockMvcResultMatchers.status().isBadRequest())
  }

  def 'successfully invokes the update for a valid request'() {
    given:
    Product product = new Product(productId: 12345, currentPrice: new Product.Price(value: 2.99, currencyCode: 'USD'))

    when:
    def results  = mockMvc.perform(MockMvcRequestBuilders.put('/v1/products/12345').
      contentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
        Charset.forName("utf8")))
      .content(new ObjectMapper().writeValueAsString(product)))

    then:
    1 * productService.updateProductPrice(_ as Product)
    1 * metricService.recordCount(_ as String)
    results.andExpect(MockMvcResultMatchers.status().isOk())
    0 * _
  }

  def 'bad request when id is different from the url and in the request'() {
    given:
    Product product = new Product(productId: 13860428, currentPrice: new Product.Price(value: 2.99, currencyCode: 'USD'))

    when:
    mockMvc.perform(MockMvcRequestBuilders.put('/v1/products/13860429').
      contentType(new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
        Charset.forName("utf8")))
      .content(new ObjectMapper().writeValueAsString(product)))

    then:
    thrown(Exception.class)
    0 * productService.updateProductPrice(_ as Product)
  }

}
