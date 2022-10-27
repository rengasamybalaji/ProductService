package com.myRetail.productService

import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class LinterSpec extends Specification {

  private static final String GROOVY_FILES = '**/*.groovy'
  private static final String RULESET_FILES = 'ruleset.groovy'

  def testRunCodeNarc() {
    setup:
    def ant = new AntBuilder()

    ant.taskdef(name: 'codenarc', classname: 'org.codenarc.ant.CodeNarcTask')

    expect:
    ant.codenarc(ruleSetFiles: RULESET_FILES,
      maxPriority1Violations: 0,
      maxPriority2Violations: 0,
      maxPriority3Violations: 0) {

      fileset(dir: 'src/main/groovy') {
        include(name: GROOVY_FILES)
        exclude(name: '**/package-info.groovy')
      }
      fileset(dir: 'src/test/groovy') {
        include(name: GROOVY_FILES)
      }

      report(type: 'ide')
    }
  }
}