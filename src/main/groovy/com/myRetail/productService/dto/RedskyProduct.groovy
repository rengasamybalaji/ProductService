package com.myRetail.productService.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * RedSky domain DTO object
 */
class RedskyProduct {

  Product product

  static class Product {

    Item item
    static class Item {
      @JsonProperty("product_description")
      ProductDesc productDesc

      static class ProductDesc {
        String title
      }
    }

  }
}
