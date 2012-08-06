package com.tgid.tri.race

import com.tgid.tri.results.RaceResults

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.joda.time.Duration
import spock.lang.Specification
import spock.lang.Unroll
import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.SegmentResult

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */

@TestFor(Segment)
@Mock([Race, Segment, RaceResults, SegmentResult])
class SegmentSpec extends Specification {

    def setup() {
        mockForConstraintsTests(Race)
        mockForConstraintsTests(Segment)
        mockForConstraintsTests(RaceResult)
        mockForConstraintsTests(SegmentResult)
    }

    @Unroll()
    def "test speed calculations"() {
        given:
        def race = new Race(name: 'Some Race', raceType: RaceType.Triathlon, date: new Date())
        def segment = new Segment(distance: distance, segmentType: segmentType, distanceType: distanceType, order: 1)
        race.addToSegments(segment)
        def raceResults = new RaceResults(race: race, duration: segmentDuration)
        def segmentResults = new SegmentResult(segment: segment, raceResults: raceResults, duration: segmentDuration)
        raceResults.addToSegmentResults(segmentResults)

        when:
        def pace = segmentResults.pace

        then:
        pace.speed == speed
        pace.display == display
        pace.duration == duration

        where:
        segmentType      | distance | distanceType        | segmentDuration                          | speed | display           | duration
        SegmentType.Bike | 15       | DistanceType.Miles  | Duration.standardMinutes(60)             | 15    | '15.00 mph'       | null
        SegmentType.Bike | 15       | DistanceType.Miles  | Duration.standardMinutes(30)             | 30    | '30.00 mph'       | null
        SegmentType.Bike | 15       | DistanceType.Miles  | Duration.standardMinutes(15)             | 60    | '60.00 mph'       | null
        SegmentType.Bike | 15       | DistanceType.Miles  | Duration.standardSeconds(38 * 60 + 30)   | 23.38 | '23.38 mph'       | null
        SegmentType.Bike | 15       | DistanceType.Miles  | Duration.standardSeconds(39 * 60 + 30)   | 22.78 | '22.78 mph'       | null

        SegmentType.Run  | 3        | DistanceType.Miles  | Duration.standardMinutes(18)             | null  | '6:00 / mile'     | Duration.standardSeconds(6 * 60)
        SegmentType.Run  | 3        | DistanceType.Miles  | Duration.standardMinutes(21)             | null  | '7:00 / mile'     | Duration.standardSeconds(7 * 60)
        SegmentType.Run  | 3        | DistanceType.Miles  | Duration.standardMinutes(24)             | null  | '8:00 / mile'     | Duration.standardSeconds(8 * 60)
        SegmentType.Run  | 3        | DistanceType.Miles  | Duration.standardSeconds((20 * 60) + 45) | null  | '6:55 / mile'     | Duration.standardSeconds(6 * 60 + 55)
        SegmentType.Run  | 5        | DistanceType.Kilometers  | Duration.standardSeconds((19 * 60) + 30) | null  | '6:16 / mile'     | Duration.standardSeconds(6 * 60 + 16)
        SegmentType.Run  | 5        | DistanceType.Kilometers  | Duration.standardSeconds((19 * 60) + 45) | null  | '6:21 / mile'     | Duration.standardSeconds(6 * 60 + 21)

        SegmentType.Swim | 0.5      | DistanceType.Miles  | Duration.standardMinutes(12)             | null  | '1:21 / 100 yard' | Duration.standardSeconds(81)
        SegmentType.Swim | 0.5      | DistanceType.Miles  | Duration.standardMinutes(13)             | null  | '1:28 / 100 yard' | Duration.standardSeconds(88)
        SegmentType.Swim | 0.5      | DistanceType.Miles  | Duration.standardMinutes(14)             | null  | '1:35 / 100 yard' | Duration.standardSeconds(95)
        SegmentType.Swim | 0.4      | DistanceType.Miles  | Duration.standardMinutes(11)             | null  | '1:33 / 100 yard' | Duration.standardSeconds(93)
        SegmentType.Swim | 100      | DistanceType.Yards  | Duration.standardMinutes(1)              | null  | '1:00 / 100 yard' | Duration.standardSeconds(60)
        SegmentType.Swim | 200      | DistanceType.Yards  | Duration.standardMinutes(2)              | null  | '1:00 / 100 yard' | Duration.standardSeconds(60)
        SegmentType.Swim | 200      | DistanceType.Yards  | Duration.standardMinutes(3)              | null  | '1:30 / 100 yard' | Duration.standardSeconds(90)
        SegmentType.Swim | 100      | DistanceType.Meters | Duration.standardMinutes(1)              | null  | '0:54 / 100 yard' | Duration.standardSeconds(54)
        SegmentType.Swim | 200      | DistanceType.Meters | Duration.standardMinutes(2)              | null  | '0:54 / 100 yard' | Duration.standardSeconds(54)
        SegmentType.Swim | 200      | DistanceType.Meters | Duration.standardMinutes(3)              | null  | '1:22 / 100 yard' | Duration.standardSeconds(82)
    }
}

