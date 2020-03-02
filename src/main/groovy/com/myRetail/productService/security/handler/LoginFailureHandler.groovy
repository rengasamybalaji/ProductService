package com.myRetail.productService.security.handler

import com.myRetail.productService.security.dto.Response
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AuthenticationFailureHandler

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LoginFailureHandler implements AuthenticationFailureHandler  {
  @Override
  void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

    String errorMessage = ExceptionUtils.getMessage(e)

    sendError(httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED, errorMessage, e)
  }

  private void sendError(HttpServletResponse response, int code, String message, Exception e) throws IOException {
    SecurityContextHolder.clearContext()

    Response exceptionResponse =
      new Response(com.myRetail.productService.security.dto.Response.STATUS_FAILURE, message, ExceptionUtils.getStackTrace(e))

    exceptionResponse.send(response, code)
  }
}
