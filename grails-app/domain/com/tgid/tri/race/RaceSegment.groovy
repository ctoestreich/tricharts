package com.tgid.tri.race

class RaceSegment {

    Race race
    Segment segment

    static constraints = {
        race nullable: false
        segment nullable: false
    }

    static transients = ['segmentOrder','segmentType','distanceType','distance']

    Integer getSegmentOrder() {
        segment?.segmentOrder ?: 1
    }
    SegmentType getSegmentType(){
        segment?.segmentType
    }
    DistanceType getDistanceType(){
        segment?.distanceType
    }
    Float getDistance(){
        segment?.distance
    }
}
