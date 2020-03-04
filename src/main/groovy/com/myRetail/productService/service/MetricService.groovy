package com.myRetail.productService.service

import com.codahale.metrics.MetricRegistry
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.util.concurrent.TimeUnit

/**
 * Service class to record metrics
 */

@Component
@Slf4j
class MetricService {
  @Autowired
  MetricRegistry metricRegistry

  void recordCount(String name, long n = 1) {
    metricRegistry.counter(name).inc(n)
  }

  void recordTimer(String name, long duration, TimeUnit unit = TimeUnit.MILLISECONDS) {
    metricRegistry.timer(name).update(duration, unit)
  }

  void recordErrorCountAndLogging(String action, String message, long n = 1) {
    metricRegistry.counter(action + "_error").inc(n)
    log.error("action=${action}, error=${message}")
  }
}
