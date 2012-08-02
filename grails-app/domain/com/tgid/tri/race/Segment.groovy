package com.tgid.tri.race

class Segment {

    SegmentType segmentType
    DistanceType distanceType
    Float distance = 0.00

    static belongsTo = [race: Race]

    static mapping = {
        sort 'segmentOrder'
    }

    static constraints = {
        segmentType nullable: false
        distanceType nullable: false
        distance scale: 2, nullable:  false
    }

    transient Integer getSegmentOrder(){
        (race.raceType == RaceType.Triathlon) ? triathlonSegmentOrder : 1
    }

    private getTriathlonSegmentOrder(){
        if(segmentType == SegmentType.Swim) return 1
        if(segmentType == SegmentType.T1) return 2
        if(segmentType == SegmentType.Bike) return 3
        if(segmentType == SegmentType.T2) return 4
        if(segmentType == SegmentType.Run) return 5
    }
}
