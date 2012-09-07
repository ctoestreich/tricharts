package com.tgid.tri.results

import com.tgid.tri.common.JodaTimeHelper
import com.tgid.tri.race.DistanceType
import com.tgid.tri.race.Pace
import com.tgid.tri.race.RaceSegment
import com.tgid.tri.race.SegmentType
import org.joda.time.Duration
import org.joda.time.format.PeriodFormatter

import java.math.RoundingMode
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class SegmentResult {

    transient paceService

    RaceSegment raceSegment
    Duration duration
    Integer placeAgeGroup
    Integer placeOverall
    Integer placeGender

    static belongsTo = [raceResult: RaceResult]

    static constraints = {
        raceSegment nullable: true
        placeAgeGroup nullable: true
        placeOverall nullable: true
        placeGender nullable: true
        duration nullable: false
        raceResult nullable: false
    }

    static transients = ['segmentOrder', 'pace', 'date', 'segmentType']

    @Override
    String toString() {
        "${raceSegment?.segmentType} ${raceSegment?.distance} ${raceSegment?.distanceType} @ ${pace?.display}"
    }

    transient Integer getSegmentOrder() {
        raceSegment.segmentOrder
    }

    transient Date getDate() {
        raceResult.date
    }

    transient SegmentType getSegmentType() {
        raceSegment.segmentType
    }

    transient Pace getPace() {
        paceService.getPace(this)
    }
}
