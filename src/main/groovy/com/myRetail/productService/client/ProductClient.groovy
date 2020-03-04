package com.myRetail.productService.client

import com.myRetail.productService.dto.RedskyProduct
import com.myRetail.productService.exception.NotFoundException
import com.myRetail.productService.service.MetricService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

/**
 * Redsky Client to interact with Redsky API
 */
@Component
@Slf4j
class ProductClient {

  @Autowired
  RestTemplate restTemplate

  @Autowired
  MetricService metricService

  RedskyProduct fetchProduct(long productId) {
    try {
      RedskyProduct redSkyProduct = restTemplate.getForObject("/v2/pdp/tcin/$productId?excludes=taxonomy,price,promotion,bulk", RedskyProduct)
      return redSkyProduct
    } catch (HttpClientErrorException exception) {
      metricService.recordErrorCountAndLogging('Redsky API Failure', exception.message)
      if (exception.message.contains('404')) {
        throw new NotFoundException(errorCode: 'PRODUCT_NOT_FOUND', errorMessage: exception.message)
      }
      throw exception
    }
  }
}
