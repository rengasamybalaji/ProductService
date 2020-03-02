package com.myRetail.productService.security.dto

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import groovy.util.logging.Slf4j

import javax.servlet.http.HttpServletResponse

@Slf4j
class Response {
  public static final String STATUS_FAILURE = "failure"

  String status
  String message
  String data

  Response(String status, String message, String data) {
    this.status = status
    this.message = message
    this.data = data
  }

  String toJson() throws JsonProcessingException {
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter()
    try {
      return ow.writeValueAsString(this)
    } catch (JsonProcessingException e) {
      log.error(e.getLocalizedMessage())
      throw e
    }
  }

  void send(HttpServletResponse response, int code) throws IOException {
    response.setStatus(code)
    response.setContentType("application/json")
    String errorMessage

    errorMessage = toJson()

    response.getWriter().println(errorMessage)
    response.getWriter().flush()
  }
}
