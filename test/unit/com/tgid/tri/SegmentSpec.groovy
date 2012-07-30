package com.tgid.tri

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.joda.time.Duration
import spock.lang.Specification
import spock.lang.Unroll

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */

@TestFor(Segment)
@Mock([Race, Segment])
class SegmentSpec extends Specification {

    @Unroll()
    def "test speed calculations"() {
        given:
        def race = new Race(raceType: RaceType.Triathlon, duration: segmentDuration, date: new Date())
        def segment = new Segment(segmentType: segmentType, duration: segmentDuration, distance: distance, race: race)

        when:
        println segment.duration.millis
        def pace = segment.pace

        then:
        pace.speed == speed
        pace.display == display
        pace.duration == duration

        where:
        segmentType      | distance | segmentDuration                          | speed | display     | duration
        SegmentType.Bike | 15       | Duration.standardMinutes(60)             | 15    | '15.00 mph' | null
        SegmentType.Bike | 15       | Duration.standardMinutes(30)             | 30    | '30.00 mph' | null
        SegmentType.Bike | 15       | Duration.standardMinutes(15)             | 60    | '60.00 mph' | null
        SegmentType.Bike | 15       | Duration.standardSeconds(38 * 60 + 30)   | 23.38 | '23.38 mph' | null
        SegmentType.Bike | 15       | Duration.standardSeconds(39 * 60 + 30)   | 22.78 | '22.78 mph' | null
        SegmentType.Run  | 3        | Duration.standardMinutes(18)             | null  | '6:00'      | Duration.standardSeconds(6 * 60)
        SegmentType.Run  | 3        | Duration.standardMinutes(21)             | null  | '7:00'      | Duration.standardSeconds(7 * 60)
        SegmentType.Run  | 3        | Duration.standardMinutes(24)             | null  | '8:00'      | Duration.standardSeconds(8 * 60)
        SegmentType.Run  | 3        | Duration.standardSeconds((20 * 60) + 45) | null  | '6:55'      | Duration.standardSeconds(6 * 60 + 55)
    }
}

