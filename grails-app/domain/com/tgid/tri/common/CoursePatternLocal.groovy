package com.tgid.tri.common

import com.tgid.tri.race.RaceCategoryType
import com.tgid.tri.race.DistanceType

class CoursePatternLocal {
    Double distance
    RaceCategoryType raceCategoryType
    DistanceType distanceType

    static constraints = {
        distance min: 0.0d
    }

    static mapping = {
        autoTimestamp true
        version true
        sort 'raceCategoryType'
    }

    @Override
    String toString() {
        "${raceCategoryType?.raceCategoryType} $distance ${distanceType}"
    }
}
