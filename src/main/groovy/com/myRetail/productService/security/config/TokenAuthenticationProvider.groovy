package com.myRetail.productService.security.config

import com.myRetail.productService.auth.service.UserAuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

/**
 * Customizing Spring Authentication provider for token based Authentication
 */
@Component
class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  @Autowired
  UserAuthenticationService auth

  @Override
  protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

  }

  @Override
  protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
    Object token = usernamePasswordAuthenticationToken.getCredentials()
    return Optional.ofNullable(token).map { String.valueOf(it) }.flatMap { auth.findByToken(it) }.orElseThrow {
      new UsernameNotFoundException("Cannot find user with authentication token=" + token)
    }
  }
}
