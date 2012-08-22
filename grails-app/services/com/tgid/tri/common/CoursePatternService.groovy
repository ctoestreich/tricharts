package com.tgid.tri.common

import com.tgid.tri.race.RaceCategoryType
import com.tgid.tri.race.DistanceType

class CoursePatternService {

    static transactional = false

    private static final HashMap<String, CoursePattern> map = [
            '1Mi Run': new CoursePattern(distance: 1, raceCategoryType: RaceCategoryType.OneMile, distanceType: DistanceType.Miles),
            '5K': new CoursePattern(distance: 5, raceCategoryType: RaceCategoryType.FiveKilometer, distanceType: DistanceType.Kilometers),
            '10K': new CoursePattern(distance: 10, raceCategoryType: RaceCategoryType.TenKilometer, distanceType: DistanceType.Kilometers),
            '10Mi Run': new CoursePattern(distance: 10, raceCategoryType: RaceCategoryType.TenMile, distanceType: DistanceType.Miles),
            '20Mi Run': new CoursePattern(distance: 20, raceCategoryType: RaceCategoryType.TwentyMile, distanceType: DistanceType.Miles),
            '8K Run': new CoursePattern(distance: 8, raceCategoryType: RaceCategoryType.EightKilometer, distanceType: DistanceType.Kilometers),
            '8K': new CoursePattern(distance: 8, raceCategoryType: RaceCategoryType.EightKilometer, distanceType: DistanceType.Kilometers),
            'Marathon': new CoursePattern(distance: 26.2f, raceCategoryType: RaceCategoryType.Marathon, distanceType: DistanceType.Miles),
            '26.2Mi Run': new CoursePattern(distance: 26.2f, raceCategoryType: RaceCategoryType.Marathon, distanceType: DistanceType.Miles),
            '1/2 Mara': new CoursePattern(distance: 13.1f, raceCategoryType: RaceCategoryType.HalfMarathon, distanceType: DistanceType.Miles),
            'Half Iron': new CoursePattern(distance: 70.3f, raceCategoryType: RaceCategoryType.HalfIronman, distanceType: DistanceType.Miles),
            'Ironman': new CoursePattern(distance: 140.6f, raceCategoryType: RaceCategoryType.Ironman, distanceType: DistanceType.Miles)
    ]

    CoursePattern lookup(String key) {
        def result = map.get(key)
        if(!result){ log.info "Key not found ${key}" }
        return result ?: new CoursePattern(distance: 1, raceCategoryType: RaceCategoryType.OneMile, distanceType: DistanceType.Miles)
    }
}
