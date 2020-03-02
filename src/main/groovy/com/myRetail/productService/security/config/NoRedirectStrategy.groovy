package com.myRetail.productService.security.config

import org.springframework.security.web.RedirectStrategy

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Overriding Spring Security's redirect strategy to avoid redirecting to form based login
 */
class NoRedirectStrategy implements RedirectStrategy {

  @Override
  void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
    // There is no need to implement as there will be no redirection required for REST service authentication.
  }
}
