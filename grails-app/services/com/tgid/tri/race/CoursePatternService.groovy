package com.tgid.tri.race

import com.tgid.tri.common.CoursePatternLocal

class CoursePatternService {

//    static transactional = true

    private static final CoursePatternLocal sprint = new CoursePatternLocal(distance: 25.75f, raceCategoryType: RaceCategoryType.Sprint, distanceType: DistanceType.Kilometers)
    private static final CoursePatternLocal olympic = new CoursePatternLocal(distance: 51.5f, raceCategoryType: RaceCategoryType.Olympic, distanceType: DistanceType.Kilometers)
    private static final CoursePatternLocal halfIronman = new CoursePatternLocal(distance: 70.3f, raceCategoryType: RaceCategoryType.HalfIronman, distanceType: DistanceType.Miles)
    private static final CoursePatternLocal ironman = new CoursePatternLocal(distance: 140.6f, raceCategoryType: RaceCategoryType.Ironman, distanceType: DistanceType.Miles)
    private static final CoursePatternLocal tenKilomter = new CoursePatternLocal(distance: 10, raceCategoryType: RaceCategoryType.TenKilometer, distanceType: DistanceType.Kilometers)
    private static final CoursePatternLocal halfMarathon = new CoursePatternLocal(distance: 13.1f, raceCategoryType: RaceCategoryType.HalfMarathon, distanceType: DistanceType.Miles)
    private static final CoursePatternLocal duathlon = new CoursePatternLocal(distance: 45f, raceCategoryType: RaceCategoryType.Duathlon, distanceType: DistanceType.Kilometers)

    private static final HashMap<String, CoursePatternLocal> map = [
            '1Mi Run': new CoursePatternLocal(distance: 1, raceCategoryType: RaceCategoryType.OneMile, distanceType: DistanceType.Miles),
            '3Mi Run': new CoursePatternLocal(distance: 3, raceCategoryType: RaceCategoryType.ThreeMile, distanceType: DistanceType.Miles),
            '5Mi Run': new CoursePatternLocal(distance: 5, raceCategoryType: RaceCategoryType.FiveMile, distanceType: DistanceType.Miles),
            '8Mi Run': new CoursePatternLocal(distance: 8, raceCategoryType: RaceCategoryType.EightMile, distanceType: DistanceType.Miles),
            '5K': new CoursePatternLocal(distance: 5, raceCategoryType: RaceCategoryType.FiveKilometer, distanceType: DistanceType.Kilometers),
            '10K': tenKilomter,
            '10Mi Run': new CoursePatternLocal(distance: 10, raceCategoryType: RaceCategoryType.TenMile, distanceType: DistanceType.Miles),
            '20Mi Run': new CoursePatternLocal(distance: 20, raceCategoryType: RaceCategoryType.TwentyMile, distanceType: DistanceType.Miles),
            '8K Run': new CoursePatternLocal(distance: 8, raceCategoryType: RaceCategoryType.EightKilometer, distanceType: DistanceType.Kilometers),
            '25K Run': new CoursePatternLocal(distance: 25, raceCategoryType: RaceCategoryType.TwentyFiveKilometer, distanceType: DistanceType.Kilometers),
            '8K': new CoursePatternLocal(distance: 8, raceCategoryType: RaceCategoryType.EightKilometer, distanceType: DistanceType.Kilometers),
            'Marathon': new CoursePatternLocal(distance: 26.2f, raceCategoryType: RaceCategoryType.Marathon, distanceType: DistanceType.Miles),
            '13.1Mi Run': halfMarathon,
            '13.1K Run': new CoursePatternLocal(distance: 13.1f, raceCategoryType: RaceCategoryType.HalfMarathon, distanceType: DistanceType.Kilometers),
            '1/2 Mara': halfMarathon,
            '26.2Mi Run': new CoursePatternLocal(distance: 26.2f, raceCategoryType: RaceCategoryType.Marathon, distanceType: DistanceType.Miles),
            'Half Iron': halfIronman,
            'Ironman': ironman,
            '0.5Mi Swim, 15Mi Bike/Cycle, 3Mi Run': sprint,
            '0.5Mi Swim, 0.5Mi Swim, 15Mi Bike/Cycle, 15Mi Bike/Cycle, 3.1Mi Run, 3.1Mi Run': sprint,
            '0.5Mi Swim, 0.5Mi Swim, 13.3Mi Bike/Cycle, 13.3Mi Bike/Cycle, 3.1Mi Run, 3.1Mi Run': sprint,
            '0.5Mi Swim, 21Mi Bike/Cycle, 5.3Mi Run': sprint,
            '0.5Mi Swim': new CoursePatternLocal(distance: 0.5f, raceCategoryType: RaceCategoryType.HalfMile, distanceType: DistanceType.Miles),
            '5Mi Mountain Bike': new CoursePatternLocal(distance: 5, raceCategoryType: RaceCategoryType.FiveMile, distanceType: DistanceType.Miles),
            '5Mi Swim': new CoursePatternLocal(distance: 5, raceCategoryType: RaceCategoryType.FiveMile, distanceType: DistanceType.Miles),
            '7.3Mi Run': new CoursePatternLocal(distance: 7.3f, raceCategoryType: RaceCategoryType.Run, distanceType: DistanceType.Miles),
            '1Mi Swim': new CoursePatternLocal(distance: 1, raceCategoryType: RaceCategoryType.OneMile, distanceType: DistanceType.Miles),
            '2.8Mi Run': new CoursePatternLocal(distance: 2.8f, raceCategoryType: RaceCategoryType.Run, distanceType: DistanceType.Miles),
            '4Mi Run': new CoursePatternLocal(distance: 4f, raceCategoryType: RaceCategoryType.Run, distanceType: DistanceType.Miles),
            '8.2Mi Run': new CoursePatternLocal(distance: 8.2f, raceCategoryType: RaceCategoryType.Run, distanceType: DistanceType.Miles),
    ]

    CoursePatternLocal lookup(Map course) {
        def result = map.get(course?.CoursePattern)
        if(!result) {
            log.debug "Key not found ${course?.CoursePattern}"
            result = lookupByName(course)
        }
        result
    }

    private CoursePatternLocal lookupByName(Map course) {
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

        return null
    }

    private boolean testForDuathlon(String name) {
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

    void importCoursePattern(Map course) {
        CoursePattern.withTransaction {
            if(!com.tgid.tri.race.CoursePattern.get(course?.ID)) {
                def coursePattern = new com.tgid.tri.race.CoursePattern(parentID: course?.OuterID, name: course?.Name, weight: course?.CourseCount)
                coursePattern.id = course?.ID
                if(coursePattern.validate()) {
                    try {
                        coursePattern.save(flush: true)
                    } catch(Exception e) {
                        log.error course
                        log.error e
                    }
                } else {
                    println "CoursePatternLocal Validation Errors! ${course}"
                    coursePattern?.errors?.allErrors?.each {
                        println it
                        log.error it
                    }
                }
            }
        }

    }
}
