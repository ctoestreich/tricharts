package com.tgid.tri.data.parsing

import com.tgid.tri.auth.Racer
import com.tgid.tri.results.RaceResult
import grails.plugin.spock.IntegrationSpec
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
class AthlinksResultsParsingServiceSpec extends IntegrationSpec {

    AthlinksResultsParsingService athlinksResultsParsingService

    @Unroll()
    def "test importing results for racer #racer"() {
        when:
        athlinksResultsParsingService.retrieveResults(racer)

        then:
        RaceResult.findAllByUser(racer.user).size() > 0

        where:
        racer << Racer.list()
    }
}
