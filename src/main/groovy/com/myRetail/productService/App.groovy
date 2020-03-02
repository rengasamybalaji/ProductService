package com.myRetail.productService

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

/**
 * Entry point to the Springboot application
 */

@SpringBootApplication
class App extends SpringBootServletInitializer {

  static void main(String[] args) {
    SpringApplication.run(App.class, args)
  }
}
