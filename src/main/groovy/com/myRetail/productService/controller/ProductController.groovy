package com.myRetail.productService.controller

import com.myRetail.productService.dto.Product
import com.myRetail.productService.service.MetricService
import com.myRetail.productService.service.ProductService
import com.myRetail.productService.validator.ProductValidator
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

/**
 * RestController to hold product endpoints
 */
@RestController
@RequestMapping('/v1/products')
@Slf4j
class ProductController {

  @Autowired
  ProductService productService

  @Autowired
  ProductValidator productValidator

  @Autowired
  MetricService metricService

  @GetMapping(path = '/{id}', produces = MediaType.APPLICATION_JSON_VALUE)
  Product getProduct(@PathVariable('id') long productId) {
    log.info("Fetching productInfo for id $productId")
    Product product = productService.getProduct(productId)
    metricService.recordCount('fetchProduct')
    return product
  }

  @PutMapping(path = '/{id}', consumes = MediaType.APPLICATION_JSON_VALUE)
  void updateProductPrice(@PathVariable('id') long productId, @Valid @RequestBody Product productRequest) {
    log.info("Updating product pricing for id $productId")
    metricService.recordCount('updateProduct')
    if (productValidator.isValidProductMatch(productRequest, productId)) {
      productService.updateProductPrice(productRequest)
    }
  }

  @InitBinder("product")
  void setProductBinder(WebDataBinder webDataBinder) {
    webDataBinder.setValidator(productValidator)
  }
}
