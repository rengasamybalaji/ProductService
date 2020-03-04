package com.myRetail.productService.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

import javax.validation.constraints.DecimalMin

/**
 * Product DTO object
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class Product {

  @JsonProperty('id')
  long productId

  @JsonProperty('name')
  String productName

  @JsonProperty("current_price")
  Price currentPrice

  static class Price {
    @DecimalMin('0.0')
    Double value
    @JsonProperty("currency_code")
    String currencyCode
  }

}
