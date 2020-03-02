package com.myRetail.productService.service

import com.codahale.metrics.MetricRegistry
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.util.concurrent.TimeUnit

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
}
