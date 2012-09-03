package com.tgid.tri

import com.tgid.tri.common.PaceService
import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.SegmentResult
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.services.ServiceUnitTestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.joda.time.Duration
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import com.tgid.tri.race.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin([GrailsUnitTestMixin, DomainClassUnitTestMixin, ServiceUnitTestMixin])
@TestFor(TriHarderTagLib)
@Mock([SegmentResult, RaceResult, Race, Segment, PaceService])
class TriHarderTagLibSpec extends Specification {

    @Shared
    PaceService paceService

    def setupSpec() {
        paceService = new PaceService()
    }

    @Unroll()
    def "test taglib format duration for running #segmentType #distance #distanceType @=#showAt #result"() {
//        assert applyTemplate('<s:bye author="${author}" />', [author: new Author(name: 'Fred')]) == 'Bye Fred'
        given:
        SegmentResult segmentResult = new SegmentResult(duration: duration,
                                                        raceSegment: new RaceSegment(
                                                                race: new Race(),
                                                                segment: new Segment(segmentType: segmentType, distance: distance, distanceType: distanceType)))
        segmentResult.paceService = paceService

        when:
        def format = applyTemplate('<tri:displayPace pace="${pace}" showAt="${showAt}" />', [pace: segmentResult.pace, showAt: showAt])

        then:
        format == result

        where:
        duration                              | segmentType      | distance | distanceType       | showAt | result
        Duration.standardSeconds(60 * 5 + 30) | SegmentType.Run  | 1        | DistanceType.Miles | true   | ' @ 05:30'
        Duration.standardSeconds(60 * 5 + 30) | SegmentType.Run  | 1        | DistanceType.Miles | false  | ' 05:30'
        Duration.standardSeconds(60 * 3)      | SegmentType.Swim | 200      | DistanceType.Yards | true   | ' @ 01:30'
        Duration.standardSeconds(60 * 3)      | SegmentType.Swim | 200      | DistanceType.Yards | false  | ' 01:30'
    }
}
