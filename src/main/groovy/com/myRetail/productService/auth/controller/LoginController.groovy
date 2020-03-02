package com.myRetail.productService.auth.controller

import com.myRetail.productService.auth.service.UserAuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/auth')
class LoginController {

  @Autowired
  UserAuthenticationService authentication

  @PostMapping("/login")
  String login(@RequestParam("username") final String username,
                      @RequestParam("password") final String password){

    return authentication.login(username, password)
  }
}
