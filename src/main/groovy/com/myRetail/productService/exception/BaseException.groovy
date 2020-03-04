package com.myRetail.productService.exception

/**
 * Base Exception class
 */
class BaseException extends RuntimeException {

  String errorCode
  String errorMessage
}
