package com.myRetail.productService.validator

import com.myRetail.productService.constants.ProductConstants
import com.myRetail.productService.dto.Product
import com.myRetail.productService.exception.InvalidInputException
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.Validator

/**
 * Validator to handle custom validation.
 */

@Component
@Slf4j
class ProductValidator implements Validator {

  @Override
  boolean supports(Class<?> clazz) {
    return Product.class.isAssignableFrom(clazz)
  }

  @Override
  void validate(Object target, Errors errors) {
    Product product = (Product) target

    if (!product.currentPrice) {
      log.warn(ProductConstants.MISSING_PRODUCT_PRICE_ERROR_CODE)
      throw new InvalidInputException(errorCode: ProductConstants.MISSING_PRODUCT_PRICE_ERROR_CODE,
        errorMessage: ProductConstants.MISSING_PRODUCT_PRICE_ERROR_DESCRIPTION)
    }

    else if (!this.isValidProductPrice(product.currentPrice.value)) {
      log.warn(ProductConstants.INVALID_PRODUCT_PRICE_ERROR_DESCRIPTION)
      throw new InvalidInputException(errorCode: ProductConstants.INVALID_PRODUCT_PRICE_ERROR_CODE,
        errorMessage: ProductConstants.INVALID_PRODUCT_PRICE_ERROR_DESCRIPTION)
    }

    else if (!product.currentPrice.currencyCode || product.currentPrice.currencyCode.trim().isEmpty()) {
      log.warn(ProductConstants.MISSING_CURRENCY_ERROR_CODE)
      throw new InvalidInputException(errorCode: ProductConstants.MISSING_CURRENCY_ERROR_CODE,
        errorMessage: ProductConstants.MISSING_CURRENCY_ERROR_DESCRIPTION)
    }

    else if (!isValidCurrencyCode(product.currentPrice.currencyCode.trim())) {
      log.warn(ProductConstants.INVALID_CURRENCY_ERROR_CODE)
      throw new InvalidInputException(errorCode: ProductConstants.INVALID_CURRENCY_ERROR_CODE,
        errorMessage: ProductConstants.INVALID_CURRENCY_ERROR_DESCRIPTION)
    }

  }

  /**
   * Validates whether the productID given in the URI matches with the one
   * provided in the request body
   *
   * @param productJSON
   * @param productId
   * @return
   */
  boolean isValidProductMatch(Product productJSON, long productId) {
    if (productId != productJSON.productId) {
      log.warn('Product Id given in request does not match with the one given in the url')
      throw new InvalidInputException(errorCode: ProductConstants.MISMATCHED_ID_ERROR_CODE, errorMessage:
      ProductConstants.MISMATCHED_ID_ERROR_DESCRIPTION)
    }
    return true
  }

  /**
   * validates whether the given price is valid or not
   *
   * @param price
   * @return
   */
  boolean isValidProductPrice(Double price) {
    boolean valid = true
    if (price == null || price <= 0.0) {
      valid = false
    }
    return valid
  }

  /**
   * Validates the currency Code against ISO 4217 currency code list
   *
   * @param currencyCode
   * @return
   */
  boolean isValidCurrencyCode(String currencyCode) {
    boolean valid = true
    try {
      Currency.getInstance(currencyCode)
    } catch (IllegalArgumentException illegalArgumentException) {
      valid = false
    }
    return valid
  }

}
