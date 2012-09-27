package com.tgid.tri.race

class Segment {

    SegmentType segmentType
    DistanceType distanceType
    Double distance = 0.00

    static constraints = {
        segmentType nullable: false, unique: ['distanceType', 'distance']
        distanceType nullable: false
        distance scale: 2, nullable: false
    }

    static mapping = {
        sort 'segmentType'
    }

    static transients = ['segmentOrder']

    Integer getSegmentOrder() {
        if(SegmentType.Swim == segmentType) return 1
        if(SegmentType.DuRun1 == segmentType) return 1
        if(SegmentType.T1 == segmentType) return 2
        if(SegmentType.Bike == segmentType) return 3
        if(SegmentType.T2 == segmentType) return 4
        if(SegmentType.Run == segmentType) return 5
        if(SegmentType.DuRun2 == segmentType) return 5
        return 1
    }

    @Override
    String toString() {
       "$segmentType - $distance $distanceType"
    }
}
