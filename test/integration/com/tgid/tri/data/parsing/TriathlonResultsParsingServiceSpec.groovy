package com.tgid.tri.data.parsing

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*
import spock.lang.Specification
import grails.plugin.spock.IntegrationSpec

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
class TriathlonResultsParsingServiceSpec extends IntegrationSpec {

    def triathlonResultsParsingService

    def "test parse of results url"() {
        given:
        def url = "http://www.pigmantri.com/jmsracing/results12/usatage12e.html"

        when:
        def data = triathlonResultsParsingService.parseResults(url)

        then:
        data.contains('Patrick')
    }
}
