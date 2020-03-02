package com.myRetail.productService.exception

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class ExceptionAdvice extends ResponseEntityExceptionHandler {

  @Autowired
  ObjectMapper objectMapper

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  void handleNotFoundException(HttpServletResponse response, NotFoundException ex) {
    setResponse(response, ex)
  }

  @ExceptionHandler(InvalidInputException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  void handleInvalidInputException(HttpServletResponse response, InvalidInputException ex) {
    setResponse(response, ex)
  }

  private Map setResponse(HttpServletResponse response, BaseException ex) {
    response.setContentType('application/json')
    response.getWriter().write(objectMapper.writeValueAsString(['errorCode': ex.errorCode, 'errorMessage': ex.errorMessage]))
  }

}
