package com.myRetail.productService.auth.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * DTO object to hold user
 */
class User implements UserDetails {

  String id
  String username
  String password

  @Override
  Collection<? extends GrantedAuthority> getAuthorities() {
    return []
  }

  @Override
  String getPassword() {
    return password
  }

  @Override
  String getUsername() {
    return username
  }

  @Override
  boolean isAccountNonExpired() {
    return true
  }

  @Override
  boolean isAccountNonLocked() {
    return true
  }

  @Override
  boolean isCredentialsNonExpired() {
    return true
  }

  @Override
  boolean isEnabled() {
    return true
  }
}
