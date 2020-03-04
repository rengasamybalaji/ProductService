package com.myRetail.productService

import com.github.tomakehurst.wiremock.client.WireMock
import com.myRetail.productService.dto.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.TestPropertySource
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(classes = App.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = ["redSky.baseUri=http://localhost:8091",])
@AutoConfigureWireMock(port = 8091)
class ProductServiceITSpec extends Specification {

  @LocalServerPort
  int port

  @Autowired
  TestRestTemplate restTemplate

  String authToken

  void setup() {
    HttpHeaders headers = new HttpHeaders()
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED)
    MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>()
    map.username = "test"
    map.password = "test"
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers)
    ResponseEntity<String> response = this.restTemplate.postForEntity("http://localhost:$port/auth/login", request, String.class)
    authToken = response.getBody()
  }

  @Unroll
  def 'When Redsky endpoint responds with error status code #statusCode'() {

    given:
    WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/v2/pdp/tcin/12345?excludes=taxonomy,price,promotion,bulk"))
      .willReturn(WireMock.aResponse().withStatus(statusCode)))

    when:
    ResponseEntity<Map> responseEntity = this.restTemplate.exchange("http://localhost:$port/v1/products/12345", HttpMethod.GET,
      getRequestEntity(null), Map.class)

    then:
    responseEntity.statusCode == httpStatusCode
    responseEntity.body.errorCode == errorCode
    if (statusCode == 500) {
      responseEntity.body.errorMessage == errorDescription
    } else {
      responseEntity.body.errorMessage
    }

    where:
    statusCode | httpStatusCode                   | errorCode           | errorDescription
    500        | HttpStatus.INTERNAL_SERVER_ERROR | 'External Error'    | 'RedSky API Not Reachable'
    404        | HttpStatus.NOT_FOUND             | 'PRODUCT_NOT_FOUND' | ''
    401        | HttpStatus.INTERNAL_SERVER_ERROR | 'Internal Error'    | ''
  }

  HttpEntity<Product> getRequestEntity(Product productPrice, String token = authToken) {
    HttpHeaders headers = new HttpHeaders()
    headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
    headers.add(HttpHeaders.AUTHORIZATION, com.myRetail.productService.security.config.TokenAuthenticationFilter.BEARER + " " + token)
    HttpEntity<Product> requestEntity = new HttpEntity<Product>(productPrice, headers)
    return requestEntity
  }

}
