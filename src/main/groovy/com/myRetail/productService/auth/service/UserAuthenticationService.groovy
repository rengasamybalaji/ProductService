package com.myRetail.productService.auth.service

import com.myRetail.productService.auth.dto.User
import org.springframework.stereotype.Service

/**
 * Service to handle authentication
 */
@Service
class UserAuthenticationService {

  Map<String, User> users = [:]

  String login(String username, String password) {
    String token = UUID.randomUUID().toString()
    User user = new User(id: token, username: username, password: password)
    users.put(token, user)
    return token
  }

  Optional<User> findByToken(String token) {
    User user = users.get(token)
    return Optional.ofNullable(user)
  }
}
