package com.tgid.tri.race

import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.SegmentResult
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.joda.time.Duration
import spock.lang.Specification
import spock.lang.Unroll
import com.tgid.tri.common.PaceService

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */

@TestFor(Segment)
@Mock([Race, Segment, RaceResult, SegmentResult, RaceSegment])
class SegmentSpec extends Specification {

    def setup() {
        mockForConstraintsTests(Race)
        mockForConstraintsTests(Segment)
        mockForConstraintsTests(RaceSegment)
        mockForConstraintsTests(RaceResult)
        mockForConstraintsTests(SegmentResult)
    }

    @Unroll()
    def "test speed calculations #segmentType #distance #distanceType. #display"() {
        given:
        def race = new Race(name: 'Some Race', raceType: RaceType.Triathlon, date: new Date())
        def segment = new Segment(distance: distance, segmentType: segmentType, distanceType: distanceType)
        def raceSegment = RaceSegment.findOrSaveWhere(race: race, segment: segment)
        race.addToSegments(raceSegment)
        def raceResult = new RaceResult(race: race, duration: segmentDuration)
        def segmentResults = new SegmentResult(raceSegment: raceSegment, raceResult: raceResult, duration: segmentDuration)
        segmentResults.paceService = new PaceService()
        raceResult.addToSegmentResults(segmentResults)

        when:
        def pace = segmentResults.pace

        then:
        pace.speed == speed
        pace.display == display
        pace.duration == duration

        where:
        segmentType      | distance | distanceType            | segmentDuration                          | speed | display | duration
        SegmentType.Bike | 15       | DistanceType.Miles      | Duration.standardMinutes(60)             | 15    | '15.00' | null
        SegmentType.Bike | 15       | DistanceType.Miles      | Duration.standardMinutes(30)             | 30    | '30.00' | null
        SegmentType.Bike | 15       | DistanceType.Miles      | Duration.standardMinutes(15)             | 60    | '60.00' | null
        SegmentType.Bike | 15       | DistanceType.Miles      | Duration.standardSeconds(38 * 60 + 30)   | 23.38 | '23.38' | null
        SegmentType.Bike | 15       | DistanceType.Miles      | Duration.standardSeconds(39 * 60 + 30)   | 22.78 | '22.78' | null

        SegmentType.Run  | 3        | DistanceType.Miles      | Duration.standardMinutes(18)             | null  | '06:00'  | Duration.standardSeconds(6 * 60)
        SegmentType.Run  | 3        | DistanceType.Miles      | Duration.standardMinutes(21)             | null  | '07:00'  | Duration.standardSeconds(7 * 60)
        SegmentType.Run  | 3        | DistanceType.Miles      | Duration.standardMinutes(24)             | null  | '08:00'  | Duration.standardSeconds(8 * 60)
        SegmentType.Run  | 3        | DistanceType.Miles      | Duration.standardSeconds((20 * 60) + 45) | null  | '06:55'  | Duration.standardSeconds(6 * 60 + 55)
        SegmentType.Run  | 5        | DistanceType.Kilometers | Duration.standardSeconds((19 * 60) + 30) | null  | '06:16'  | Duration.standardSeconds(6 * 60 + 16)
        SegmentType.Run  | 5        | DistanceType.Kilometers | Duration.standardSeconds((19 * 60) + 45) | null  | '06:21'  | Duration.standardSeconds(6 * 60 + 21)

        SegmentType.Swim | 0.5      | DistanceType.Miles      | Duration.standardMinutes(12)             | null  | '01:21'  | Duration.standardSeconds(81)
        SegmentType.Swim | 0.5      | DistanceType.Miles      | Duration.standardMinutes(13)             | null  | '01:28'  | Duration.standardSeconds(88)
        SegmentType.Swim | 0.5      | DistanceType.Miles      | Duration.standardMinutes(14)             | null  | '01:35'  | Duration.standardSeconds(95)
        SegmentType.Swim | 0.4      | DistanceType.Miles      | Duration.standardMinutes(11)             | null  | '01:33'  | Duration.standardSeconds(93)
        SegmentType.Swim | 100      | DistanceType.Yards      | Duration.standardMinutes(1)              | null  | '01:00'  | Duration.standardSeconds(60)
        SegmentType.Swim | 200      | DistanceType.Yards      | Duration.standardMinutes(2)              | null  | '01:00'  | Duration.standardSeconds(60)
        SegmentType.Swim | 200      | DistanceType.Yards      | Duration.standardMinutes(3)              | null  | '01:30'  | Duration.standardSeconds(90)
        SegmentType.Swim | 100      | DistanceType.Meters     | Duration.standardMinutes(1)              | null  | '00:54'  | Duration.standardSeconds(54)
        SegmentType.Swim | 200      | DistanceType.Meters     | Duration.standardMinutes(2)              | null  | '00:54'  | Duration.standardSeconds(54)
        SegmentType.Swim | 200      | DistanceType.Meters     | Duration.standardMinutes(3)              | null  | '01:22'  | Duration.standardSeconds(82)
    }
}

