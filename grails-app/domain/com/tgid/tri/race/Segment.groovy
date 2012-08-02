package com.tgid.tri.race

class Segment {

    SegmentType segmentType
    DistanceType distanceType
    Float distance = 0.00

    static belongsTo = [race: Race]

    static transients = ['segmentOrder']

    static constraints = {
        segmentType nullable: false
        distanceType nullable: false
        distance scale: 2, nullable: false
    }

    Integer getSegmentOrder() {
        (race?.raceType == RaceType.Triathlon) ? triathlonSegmentOrder : 1
    }

    private getTriathlonSegmentOrder() {
        if(SegmentType.Swim == segmentType) return 1
        if(SegmentType.T1 == segmentType) return 2
        if(SegmentType.Bike == segmentType) return 3
        if(SegmentType.T2 == segmentType) return 4
        if(SegmentType.Run == segmentType) return 5
        return 1
    }

    @Override
    String toString() {
       "${race.toString()} - $segmentType"
    }
}
