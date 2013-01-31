package com.tgid.tri.common

import com.tgid.tri.race.DistanceType
import com.tgid.tri.race.Pace
import com.tgid.tri.race.RaceSegment
import com.tgid.tri.race.SegmentType
import com.tgid.tri.results.SegmentResult
import org.joda.time.Duration
import org.joda.time.format.PeriodFormatter

import java.math.RoundingMode

class PaceService {

    Pace getPace(SegmentResult result) {
        def display = null, paceDuration = null, paceSpeed = null

        switch(result?.raceSegment?.segmentType) {
            case SegmentType.Bike:
                (display, paceSpeed) = calcBikePacing(result)
                break
            case SegmentType.Run:
                (display, paceDuration) = calcRunPacing(result)
                break
            case SegmentType.Swim:
                (display, paceDuration) = calcSwimPacing(result)
                break
            default:
                break
        }
        return (display) ? new Pace(display: display, duration: paceDuration, speed: paceSpeed) : null
    }

    private List calcBikePacing(SegmentResult result) {
        if(!result.duration || result.duration == Duration.standardSeconds(0) || result.duration < Duration.standardSeconds(120)) {
            return ['', '']
        }
        def distance = result.raceSegment.distance / runAndBikeMultiplier(result?.raceSegment)
        def paceSpeed = new BigDecimal(distance / (((result.duration.millis / 1000) / 60) / 60)).setScale(2, RoundingMode.HALF_UP)
        def display = "${paceSpeed}"
        [display, paceSpeed]
    }

    private List calcSwimPacing(SegmentResult result) {
//        if(!result.duration || result.duration == Duration.standardSeconds(0) || result.duration < Duration.standardSeconds(120)) {
        if(!result.duration || result.duration == Duration.standardSeconds(0)) {
            return ['', '']
        }
        BigDecimal distance = result.raceSegment.distance / swimMultiplier(result.raceSegment)
        Duration swimPace = Duration.standardSeconds(new Duration(Math.round(result.duration.millis / (distance ?: 1))).standardSeconds)
        def paceDuration = swimPace
        PeriodFormatter formatter = JodaTimeHelper.periodFormat

        String display = formatter.print(swimPace.toPeriod())
        [display, paceDuration]
    }

    private List calcRunPacing(SegmentResult result) {
        if(!result.duration || result.duration == Duration.standardSeconds(0) || result.duration < Duration.standardSeconds(120)) {
            return ['', '']
        }
        def distance = result.raceSegment.distance / runAndBikeMultiplier(result.raceSegment)
        Duration runPace = Duration.standardSeconds(new Duration(Math.round(result.duration.millis / (distance?:1))).standardSeconds)
        def paceDuration = runPace
        PeriodFormatter formatter = JodaTimeHelper.periodFormat
        def display = formatter.print(runPace.toPeriod())
        [display, paceDuration]
    }

    private runAndBikeMultiplier(RaceSegment segment) {
        switch(segment.distanceType) {
            case DistanceType.Kilometers:
                return 1.60934
            default:
                return 1
        }
    }

    private swimMultiplier(RaceSegment segment) {
        switch(segment.distanceType) {
            case DistanceType.Kilometers:
                return 0.0914107
            case DistanceType.Meters:
                return 91.44
            case DistanceType.Miles:
                return 0.0568
            case DistanceType.Feet:
                return 300
            default:
                return 100
        }
    }
}
