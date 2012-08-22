package com.tgid.tri.data.parsing

import grails.plugin.spock.IntegrationSpec
import com.tgid.tri.auth.User
import com.tgid.tri.results.RaceResult

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
class AthlinksResultsParsingServiceSpec extends IntegrationSpec {

   AthlinksResultsParsingService athlinksResultsParsingService

    def "test importing results for athlete"() {
        given:
        def user = User.findByUsername('acetrike@yahoo.com')

        when:
        athlinksResultsParsingService.retrieveResults(user)

        then:
        RaceResult.findAllByUser(user).size() > 0
    }
}
