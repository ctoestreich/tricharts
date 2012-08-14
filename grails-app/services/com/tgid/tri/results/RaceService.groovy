package com.tgid.tri.results

import com.tgid.tri.race.*

class RaceService {

    void createBikeSegments(Race race) {
        def segment = Segment.findOrCreateWhere(segmentType: SegmentType.Bike, distanceType: race?.distanceType, distance: race?.distance)
        if(segment.validate()) {
            race.addToSegments(new RaceSegment(segment: segment))
        }
    }

    void createRunSegments(Race race) {
        def segment = Segment.findOrCreateWhere(segmentType: SegmentType.Run, distanceType: race?.distanceType, distance: race?.distance)
        if(segment.validate()) {
            race.addToSegments(new RaceSegment(segment: segment))
        }
    }

    void createTriathlonSegments(Race race) {
        def swimSegment = Segment.findOrCreateWhere(segmentType: SegmentType.Swim, distanceType: DistanceType.Miles, distance: 0.5f)
        def t1Segment = Segment.findOrCreateWhere(segmentType: SegmentType.T1, distanceType: DistanceType.Meters, distance: 400f)
        def bikeSegment = Segment.findOrCreateWhere(segmentType: SegmentType.Bike, distanceType: DistanceType.Miles, distance: 15f)
        def t2Segment = Segment.findOrCreateWhere(segmentType: SegmentType.T2, distanceType: DistanceType.Meters, distance: 400f)
        def runSegment = Segment.findOrCreateWhere(segmentType: SegmentType.Run, distanceType: DistanceType.Kilometers, distance: 5f)
        race.addToSegments(new RaceSegment(segment: swimSegment))
        race.addToSegments(new RaceSegment(segment: t1Segment))
        race.addToSegments(new RaceSegment(segment: bikeSegment))
        race.addToSegments(new RaceSegment(segment: t2Segment))
        race.addToSegments(new RaceSegment(segment: runSegment))
    }
}
