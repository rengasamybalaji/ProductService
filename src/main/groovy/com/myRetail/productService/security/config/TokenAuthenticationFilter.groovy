package com.myRetail.productService.security.config

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import static java.util.Optional.ofNullable
import static org.apache.commons.lang3.StringUtils.removeStart

/**
 * Overriding Spring securities default Authentication processing filter to configure token based authentication
 */
class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

  public static final String BEARER = "Bearer"

  TokenAuthenticationFilter(final RequestMatcher requiresAuth) {
    super(requiresAuth)
  }

  com.myRetail.productService.security.handler.LoginFailureHandler loginFailureHandler = new com.myRetail.productService.security.handler.LoginFailureHandler()

  @Override
  Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
    String param = ofNullable(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION)).orElse(httpServletRequest.getParameter("t"))
    String token = ofNullable(param).map { value -> removeStart(value, BEARER) } .map { it.trim() }
      .orElseThrow { new BadCredentialsException("Missing Authentication Token") }
    Authentication auth = new UsernamePasswordAuthenticationToken(token, token)
    return getAuthenticationManager().authenticate(auth)
  }

  @Override
  protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
                                          final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
    super.successfulAuthentication(request, response, chain, authResult)
    chain.doFilter(request, response)
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            AuthenticationException failed) throws IOException, ServletException {
    loginFailureHandler.onAuthenticationFailure(request, response, failed)

  }
}
