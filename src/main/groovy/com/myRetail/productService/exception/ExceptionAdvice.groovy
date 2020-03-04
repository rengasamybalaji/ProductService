package com.myRetail.productService.exception

import com.fasterxml.jackson.databind.ObjectMapper
import com.myRetail.productService.constants.ProductConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import java.util.concurrent.ExecutionException

/**
 * Controller Advice that handles all exceptions.
 */
@ControllerAdvice
class ExceptionAdvice extends ResponseEntityExceptionHandler {

  @Autowired
  ObjectMapper objectMapper

  @ExceptionHandler(Exception.class)
  ResponseEntity handleAllExceptions(NotFoundException ex) {
    return new ResponseEntity(['errorCode': ProductConstants.INTERNAL_ERROR_CODE,
                               'errorMessage': ex.errorMessage], HttpStatus.INTERNAL_SERVER_ERROR)
  }

  @ExceptionHandler(InvalidInputException.class)
  ResponseEntity handleInvalidInputException(InvalidInputException ex) {
    return new ResponseEntity(['errorCode': ex.errorCode, 'errorMessage': ex.errorMessage], HttpStatus.BAD_REQUEST)
  }

  @ExceptionHandler(ExecutionException.class)
  ResponseEntity handleAllException(ExecutionException ex) {
    if (ex.cause instanceof NotFoundException) {
      return new ResponseEntity(['errorCode': ex.cause.errorCode,
                                 'errorMessage': ex.cause.errorMessage], HttpStatus.NOT_FOUND)
    }
    if (ex.cause instanceof HttpServerErrorException) {
      return new ResponseEntity(['errorCode': ProductConstants.EXTERNAL_ERROR_CODE,
                                 'errorMessage': ProductConstants.EXTERNAL_ERROR_DESCRIPTION], HttpStatus.INTERNAL_SERVER_ERROR)
    }
    return new ResponseEntity(['errorCode': ProductConstants.INTERNAL_ERROR_CODE,
                               'errorMessage': ex.message], HttpStatus.INTERNAL_SERVER_ERROR)
  }

}
