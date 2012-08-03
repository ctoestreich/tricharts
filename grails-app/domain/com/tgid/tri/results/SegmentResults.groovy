package com.tgid.tri.results

import com.tgid.tri.race.Pace
import com.tgid.tri.race.SegmentType
import com.tgid.tri.race.Segment
import org.joda.time.Duration
import org.joda.time.format.PeriodFormatter
import org.joda.time.format.PeriodFormatterBuilder

import java.math.RoundingMode
import com.tgid.tri.race.DistanceType
import com.tgid.tri.common.JodaTimeHelper
import com.tgid.tri.race.RaceSegment

class SegmentResults {

    RaceSegment raceSegment
    Duration duration
    Integer placeAgeGroup
    Integer placeOverall

    static belongsTo = [raceResults: RaceResults]

    static constraints = {
        raceSegment nullable:  true
        placeAgeGroup nullable:  true
        placeOverall nullable:  true
        duration nullable: false
        raceResults nullable:  false
    }

    static transients = ['segmentOrder', 'pace']

    @Override
    String toString() {
        "${raceSegment?.segmentType} ${raceSegment?.distance} ${raceSegment?.distanceType} @ ${pace?.display}"
    }

    transient Integer getSegmentOrder(){
        raceSegment.segmentOrder
    }

    transient Pace getPace() {
        def display = null, paceDuration = null, paceSpeed = null

        switch(raceSegment?.segmentType) {
            case SegmentType.Bike:
                (display, paceSpeed) = calcBikePacing()
                break
            case SegmentType.Run:
                (display, paceDuration) = calcRunPacing()
                break
            case SegmentType.Swim:
                (display, paceDuration) = calcSwimPacing()
                break
            default:
                break
        }
        return (display) ? new Pace(display: display, duration: paceDuration, speed: paceSpeed) : null
    }

    private List calcBikePacing() {
        def distance = raceSegment.distance * runAndBikeMultiplier(raceSegment)
        def paceSpeed = new BigDecimal(distance / (((duration.millis / 1000) / 60) / 60)).setScale(2, RoundingMode.HALF_UP)
        def display = "${paceSpeed} mph"
        [display, paceSpeed]
    }

    private List calcSwimPacing() {
        def distance = raceSegment.distance * swimMultiplier(raceSegment)
        def swimPace = Duration.standardSeconds(new Duration(Math.round(duration.millis / distance)).standardSeconds)
        def paceDuration = swimPace
        PeriodFormatter formatter = JodaTimeHelper.periodFormat
        def display = formatter.print(swimPace.toPeriod()) + " / 100 yard"
        [display, paceDuration]
    }

    private List calcRunPacing() {
        def distance = raceSegment.distance / runAndBikeMultiplier(raceSegment)
        def runPace = Duration.standardSeconds(new Duration(Math.round(duration.millis / distance)).standardSeconds)
        def paceDuration = runPace
        PeriodFormatter formatter = JodaTimeHelper.periodFormat
        def display = formatter.print(runPace.toPeriod()) + " / mile"
        [display, paceDuration]
    }

    private runAndBikeMultiplier(RaceSegment segment){
        switch(segment.distanceType){
            case DistanceType.Kilometers:
                return 1.60934
            default:
                return 1
        }
    }

    private swimMultiplier(RaceSegment segment){
        switch(segment.distanceType){
            case DistanceType.Meters:
                return 0.0109361
            case DistanceType.Miles:
                return 17.6
            default:
                return 0.01
        }
    }
}