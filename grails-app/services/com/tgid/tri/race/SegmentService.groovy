package com.tgid.tri.race

class SegmentService {

    Segment findOrSaveSegment(Float distance, DistanceType distanceType, SegmentType segmentType) {
        Segment.findOrSaveWhere(distance: distance, distanceType: distanceType, segmentType: segmentType)
    }
}
