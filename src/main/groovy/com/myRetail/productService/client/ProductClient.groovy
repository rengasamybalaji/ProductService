package com.myRetail.productService.client

import com.myRetail.productService.dto.RedskyProduct
import com.myRetail.productService.exception.NotFoundException
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Component
@Slf4j
class ProductClient {

  @Autowired
  RestTemplate restTemplate

  RedskyProduct fetchProduct(long productId) {
    try {
      RedskyProduct redSkyProduct = restTemplate.getForObject("/v2/pdp/tcin/$productId?excludes=taxonomy,price,promotion,bulk", RedskyProduct)
      return redSkyProduct
    } catch (HttpClientErrorException exception) {
      log.warn("Exception with redSky call for id: $productId", exception.toString())
      if (exception.message.contains('404')) {
        throw new NotFoundException(errorCode: 'PRODUCT_NOT_FOUND', errorMessage: exception.message)
      }
    }
  }
}
