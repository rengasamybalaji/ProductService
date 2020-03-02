package com.myRetail.productService.dto

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "product_price")
class ProductPriceEntity {

  @Id
  @JsonProperty('id')
  Long productId

  @JsonProperty('price')
  Price price

  static class Price {
    @JsonProperty('value')
    Double value

    @JsonProperty('currency_code')
    String currencyCode
  }
}
