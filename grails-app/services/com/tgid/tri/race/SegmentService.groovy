package com.tgid.tri.race

class SegmentService {

    Segment findOrSaveSegment(Double distance, DistanceType distanceType, SegmentType segmentType) {
        try {
            Segment.findOrSaveWhere(distance: distance, distanceType: distanceType, segmentType: segmentType)
        } catch(Exception e) {
            log.error e
        }
    }

    void seedSegments() {
        findOrSaveSegment(0.3, DistanceType.Miles, SegmentType.Swim)
        findOrSaveSegment(0.5, DistanceType.Miles, SegmentType.Swim)
        findOrSaveSegment(0.9, DistanceType.Miles, SegmentType.Swim)
        findOrSaveSegment(1, DistanceType.Miles, SegmentType.Swim)
        findOrSaveSegment(1.2, DistanceType.Miles, SegmentType.Swim)
        findOrSaveSegment(2.4, DistanceType.Miles, SegmentType.Swim)
        findOrSaveSegment(2, DistanceType.Miles, SegmentType.Swim)
        findOrSaveSegment(3, DistanceType.Miles, SegmentType.Swim)
        findOrSaveSegment(4, DistanceType.Miles, SegmentType.Swim)
        findOrSaveSegment(5, DistanceType.Miles, SegmentType.Swim)

        findOrSaveSegment(11, DistanceType.Miles, SegmentType.Bike)
        findOrSaveSegment(13, DistanceType.Miles, SegmentType.Bike)
        findOrSaveSegment(13.5, DistanceType.Miles, SegmentType.Bike)
        findOrSaveSegment(15, DistanceType.Miles, SegmentType.Bike)
        findOrSaveSegment(20, DistanceType.Kilometers, SegmentType.Bike)
        findOrSaveSegment(25, DistanceType.Kilometers, SegmentType.Bike)
        findOrSaveSegment(40, DistanceType.Kilometers, SegmentType.Bike)
        findOrSaveSegment(100, DistanceType.Miles, SegmentType.Bike)
        findOrSaveSegment(100, DistanceType.Kilometers, SegmentType.Bike)

        findOrSaveSegment(0, DistanceType.Yards, SegmentType.T1)
        findOrSaveSegment(0, DistanceType.Yards, SegmentType.T2)

        findOrSaveSegment(1, DistanceType.Miles, SegmentType.Run)
        findOrSaveSegment(3, DistanceType.Miles, SegmentType.Run)
        findOrSaveSegment(3.1, DistanceType.Miles, SegmentType.Run)
        findOrSaveSegment(5, DistanceType.Kilometers, SegmentType.Run)
        findOrSaveSegment(4, DistanceType.Miles, SegmentType.Run)
        findOrSaveSegment(5, DistanceType.Miles, SegmentType.Run)
        findOrSaveSegment(8, DistanceType.Kilometers, SegmentType.Run)
        findOrSaveSegment(10, DistanceType.Kilometers, SegmentType.Run)
        findOrSaveSegment(25, DistanceType.Kilometers, SegmentType.Run)
        findOrSaveSegment(13.1, DistanceType.Miles, SegmentType.Run)
        findOrSaveSegment(26.2, DistanceType.Miles, SegmentType.Run)
    }
}
