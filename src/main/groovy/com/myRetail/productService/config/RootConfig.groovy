package com.myRetail.productService.config

import com.codahale.metrics.MetricRegistry
import com.codahale.metrics.jmx.JmxReporter
import com.fasterxml.classmate.TypeResolver
import com.google.common.base.Predicates
import com.myRetail.productService.dto.ProductPriceEntity
import com.myRetail.productService.repository.ProductPriceRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.request.async.DeferredResult
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.schema.AlternateTypeRules
import springfox.documentation.schema.WildcardType
import springfox.documentation.service.Tag
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

import java.time.LocalDate

@Configuration
@EnableSwagger2
@PropertySources([
  @PropertySource('classpath:/config/${environment:local}.properties')
])
@Slf4j
class RootConfig {

  @Value('${redSky.baseUri}')
  String redSkyBaseUri

  @Autowired
  ProductPriceRepository productPriceRepository

  @Bean
  RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder
      .rootUri(redSkyBaseUri)
      .build()
  }

  @Bean
  MetricRegistry metricRegistry() {
    MetricRegistry registry = new MetricRegistry()
    JmxReporter reporter = JmxReporter.forRegistry(registry).build()
    reporter.start()
    return registry
  }

  /**
   * Bean definition to to populate the data in Mongo DB with sample Product ID
   * with price information during application startup
   * @return
   */
  @Bean
  CommandLineRunner runner() {
    log.info("Prepopulate mongo db for product price values")
    return  {
      productPriceRepository.save(new ProductPriceEntity(productId: 13860416,
        price: new ProductPriceEntity.Price(value: 5.67, currencyCode: Currency.getInstance(Locale.getDefault()).getCurrencyCode())))
      productPriceRepository.save(new ProductPriceEntity(productId: 13860418,
        price: new ProductPriceEntity.Price(value: 9.99, currencyCode: Currency.getInstance(Locale.getDefault()).getCurrencyCode())))
      productPriceRepository.save(new ProductPriceEntity(productId: 13860420,
        price: new ProductPriceEntity.Price(value: 10.49, currencyCode: Currency.getInstance(Locale.getDefault()).getCurrencyCode())))
      productPriceRepository.save(new ProductPriceEntity(productId: 13860421,
        price: new ProductPriceEntity.Price(value: 34.99, currencyCode: Currency.getInstance(Locale.getDefault()).getCurrencyCode())))
      productPriceRepository.save(new ProductPriceEntity(productId: 13860424,
        price: new ProductPriceEntity.Price(value: 99.99, currencyCode: Currency.getInstance(Locale.getDefault()).getCurrencyCode())))
      productPriceRepository.save(new ProductPriceEntity(productId: 13860425,
        price: new ProductPriceEntity.Price(value: 20.00, currencyCode: Currency.getInstance(Locale.getDefault()).getCurrencyCode())))
      productPriceRepository.save(new ProductPriceEntity(productId: 13860428,
        price: new ProductPriceEntity.Price(value: 12.99, currencyCode: Currency.getInstance(Locale.getDefault()).getCurrencyCode())))
    }
  }

  @Autowired
  private TypeResolver typeResolver
  /**
   * Method to define the Swagger docs specification for the product service
   * @return
   */
  @Bean
  Docket productDetailsApi() {
    return new Docket(DocumentationType.SWAGGER_2).select().apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework")))
      .paths(PathSelectors.any()).build().directModelSubstitute(LocalDate.class, String.class)
      .genericModelSubstitutes(ResponseEntity.class)
      .alternateTypeRules(AlternateTypeRules.newRule(
        typeResolver.resolve(DeferredResult.class,
          typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
        typeResolver.resolve(WildcardType.class)))
      .useDefaultResponseMessages(false).enableUrlTemplating(true)
      .tags(new Tag("Product Service",
        "REST API to fetch/update the product details from Redsky and Mongo DB"))
  }

//  @Bean
//  UiConfiguration uiConfig() {
//    return UiConfigurationBuilder.builder().deepLinking(true).displayOperationId(false).defaultModelsExpandDepth(1)
//      .defaultModelExpandDepth(1).defaultModelRendering(ModelRendering.EXAMPLE).displayRequestDuration(true)
//      .docExpansion(DocExpansion.NONE).filter(false).maxDisplayedTags(null)
//      .operationsSorter(OperationsSorter.ALPHA).showExtensions(false).tagsSorter(TagsSorter.ALPHA)
//      .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS).validatorUrl(null).build()
//  }
}
