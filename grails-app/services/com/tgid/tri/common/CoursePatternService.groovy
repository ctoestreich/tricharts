package com.tgid.tri.common

import com.tgid.tri.race.DistanceType
import com.tgid.tri.race.RaceCategoryType

class CoursePatternService {

    static transactional = false

    private static final CoursePattern sprint = new CoursePattern(distance: 25.75f, raceCategoryType: RaceCategoryType.Sprint, distanceType: DistanceType.Kilometers)
    private static final CoursePattern olympic = new CoursePattern(distance: 51.5f, raceCategoryType: RaceCategoryType.Olympic, distanceType: DistanceType.Kilometers)
    private static final CoursePattern halfIronman = new CoursePattern(distance: 70.3f, raceCategoryType: RaceCategoryType.HalfIronman, distanceType: DistanceType.Miles)
    private static final CoursePattern ironman = new CoursePattern(distance: 140.6f, raceCategoryType: RaceCategoryType.Ironman, distanceType: DistanceType.Miles)
    private static final CoursePattern tenKilomter = new CoursePattern(distance: 10, raceCategoryType: RaceCategoryType.TenKilometer, distanceType: DistanceType.Kilometers)
    private static final CoursePattern halfMarathon = new CoursePattern(distance: 13.1f, raceCategoryType: RaceCategoryType.HalfMarathon, distanceType: DistanceType.Miles)
    private static final CoursePattern duathlon = new CoursePattern(distance: 45f, raceCategoryType: RaceCategoryType.Duathlon, distanceType: DistanceType.Kilometers)

    private static final HashMap<String, CoursePattern> map = [
            '1Mi Run': new CoursePattern(distance: 1, raceCategoryType: RaceCategoryType.OneMile, distanceType: DistanceType.Miles),
            '3Mi Run': new CoursePattern(distance: 3, raceCategoryType: RaceCategoryType.ThreeMile, distanceType: DistanceType.Miles),
            '5Mi Run': new CoursePattern(distance: 5, raceCategoryType: RaceCategoryType.FiveMile, distanceType: DistanceType.Miles),
            '8Mi Run': new CoursePattern(distance: 8, raceCategoryType: RaceCategoryType.EightMile, distanceType: DistanceType.Miles),
            '5K': new CoursePattern(distance: 5, raceCategoryType: RaceCategoryType.FiveKilometer, distanceType: DistanceType.Kilometers),
            '10K': tenKilomter,
            '10Mi Run': new CoursePattern(distance: 10, raceCategoryType: RaceCategoryType.TenMile, distanceType: DistanceType.Miles),
            '20Mi Run': new CoursePattern(distance: 20, raceCategoryType: RaceCategoryType.TwentyMile, distanceType: DistanceType.Miles),
            '8K Run': new CoursePattern(distance: 8, raceCategoryType: RaceCategoryType.EightKilometer, distanceType: DistanceType.Kilometers),
            '25K Run': new CoursePattern(distance: 25, raceCategoryType: RaceCategoryType.TwentyFiveKilometer, distanceType: DistanceType.Kilometers),
            '8K': new CoursePattern(distance: 8, raceCategoryType: RaceCategoryType.EightKilometer, distanceType: DistanceType.Kilometers),
            'Marathon': new CoursePattern(distance: 26.2f, raceCategoryType: RaceCategoryType.Marathon, distanceType: DistanceType.Miles),
            '13.1Mi Run': halfMarathon,
            '1/2 Mara': halfMarathon,
            '26.2Mi Run': new CoursePattern(distance: 26.2f, raceCategoryType: RaceCategoryType.Marathon, distanceType: DistanceType.Miles),
            'Half Iron': halfIronman,
            'Ironman': ironman,
            '0.5Mi Swim, 15Mi Bike/Cycle, 3Mi Run': sprint,
            '0.5Mi Swim, 0.5Mi Swim, 15Mi Bike/Cycle, 15Mi Bike/Cycle, 3.1Mi Run, 3.1Mi Run': sprint,
            '0.5Mi Swim, 0.5Mi Swim, 13.3Mi Bike/Cycle, 13.3Mi Bike/Cycle, 3.1Mi Run, 3.1Mi Run': sprint,
            '0.5Mi Swim, 21Mi Bike/Cycle, 5.3Mi Run': sprint,
            '0.5Mi Swim': new CoursePattern(distance: 0.5f, raceCategoryType: RaceCategoryType.HalfMile, distanceType: DistanceType.Miles),
            '5Mi Mountain Bike': new CoursePattern(distance: 5, raceCategoryType: RaceCategoryType.FiveMile, distanceType: DistanceType.Miles),
            '5Mi Swim': new CoursePattern(distance: 5, raceCategoryType: RaceCategoryType.FiveMile, distanceType: DistanceType.Miles),
            '7.3Mi Run': new CoursePattern(distance: 7.3f, raceCategoryType: RaceCategoryType.Run, distanceType: DistanceType.Miles),
            '1Mi Swim': new CoursePattern(distance: 1, raceCategoryType: RaceCategoryType.OneMile, distanceType: DistanceType.Miles),
    ]

    CoursePattern lookup(Map course) {
        def result = map.get(course?.CoursePattern)
        if(!result) {
            log.debug "Key not found ${course?.CoursePattern}"
            result = lookupByName(course)
        }
        result
    }

    private CoursePattern lookupByName(Map course) {
        def name = course?.CourseName?.toUpperCase()?.trim() ?: ''
        if(name.contains('SPRINT')) { return sprint }
        else if(name.contains('OLY')) { return olympic }
        else if(name.contains('HALF IRON')) { return halfIronman }
        else if(name.contains('IRONMAN')) { return ironman }
        else if(testForSprintKeys(name)) { return sprint }
        else if(testForOlympicKeys(name)) { return olympic }
        else if(testForTenKilometer(name)) { return tenKilomter }
        else if(testForDuathlon(name)) { return duathlon }

        log.debug "!! Lookup failed for ${name}"

        return new CoursePattern(distance: 1, distanceType: DistanceType.Miles, raceCategoryType: RaceCategoryType.Unknown)
    }

    private boolean testForDuathlon(String name){
        ((name =~ /DUATHLON/).find() || (name =~ /DUA/).find())
    }

    private boolean testForTenKilometer(String name) {
        (name =~ /RUN[\-\s]*10\.*\d*K/).find()
    }

    private boolean testForOlympicKeys(String name) {
        name.contains('SWIM') && ((name =~ /6\.2\s*MI/).find() ||
                                  (name =~ /10\s*K\s*RUN/).find())
    }

    private boolean testForSprintKeys(String name) {
        name.contains('SWIM') && ((name =~ /3\.1\s*MI/).find() ||
                                  (name =~ /5\s*K\s*RUN/).find() ||
                                  (name =~ /RUN[ -]*[3\.*1*|5]*/).find())
    }
}
