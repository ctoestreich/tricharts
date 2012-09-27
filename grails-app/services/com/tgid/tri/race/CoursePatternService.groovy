package com.tgid.tri.race

import com.tgid.tri.common.CoursePatternLocal
import com.tgid.tri.common.CoursePatternLocalMap
import com.tgid.tri.data.ImportLog
import com.tgid.tri.data.ImportLoggingService

class CoursePatternService {

//    static transactional = true
    ImportLoggingService importLoggingService

    CoursePatternLocal lookup(Map course) {
        def result = CoursePatternLocalMap.findByMapKey(course?.CoursePattern)?.coursePatternLocal
        if(!result) {
            log.debug "Key not found ${course?.CoursePattern}"
            result = lookupByName(course)
        }
        result
    }

    private CoursePatternLocal lookupByName(Map course) {
        def name = course?.CourseName?.toUpperCase()?.trim() ?: ''
        if(name.contains('SPRINT')) { return CoursePatternLocal.findOrSaveWhere(distance: 25.75d, raceCategoryType: RaceCategoryType.Sprint, distanceType: DistanceType.Kilometers) }
        else if(name.contains('OLY')) { return CoursePatternLocal.findOrSaveWhere(distance: 51.5d, raceCategoryType: RaceCategoryType.Olympic, distanceType: DistanceType.Kilometers) }
        else if(name.contains('HALF IRON')) { return CoursePatternLocal.findOrSaveWhere(distance: 70.3d, raceCategoryType: RaceCategoryType.HalfIronman, distanceType: DistanceType.Miles) }
        else if(name.contains('IRONMAN')) { return CoursePatternLocal.findOrSaveWhere(distance: 140.6d, raceCategoryType: RaceCategoryType.Ironman, distanceType: DistanceType.Miles) }
        else if(testForSprintKeys(name)) { return CoursePatternLocal.findOrSaveWhere(distance: 25.75d, raceCategoryType: RaceCategoryType.Sprint, distanceType: DistanceType.Kilometers) }
        else if(testForOlympicKeys(name)) { return CoursePatternLocal.findOrSaveWhere(distance: 51.5d, raceCategoryType: RaceCategoryType.Olympic, distanceType: DistanceType.Kilometers) }
        else if(testForTenKilometer(name)) { return CoursePatternLocal.findOrSaveWhere(distance: 10d, raceCategoryType: RaceCategoryType.TenKilometer, distanceType: DistanceType.Kilometers) }
        else if(testForDuathlon(name)) { return CoursePatternLocal.findOrSaveWhere(distance: 45d, raceCategoryType: RaceCategoryType.Duathlon, distanceType: DistanceType.Kilometers) }

        importLoggingService.save(new ImportLog(importName: 'Pattern Lookup Failure', error: true, description: "${course?.CoursePattern}", complete: true))

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
                                  (name =~ /RUN[ -]*[3\.*1*|5]+/).find())
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
                        log.error it
                    }
                }
            }
        }
    }

    void seedPatternMaps() {
        CoursePatternLocal sprint = CoursePatternLocal.findOrSaveWhere(distance: 25.75d, raceCategoryType: RaceCategoryType.Sprint, distanceType: DistanceType.Kilometers)
        CoursePatternLocal olympic = CoursePatternLocal.findOrSaveWhere(distance: 51.5d, raceCategoryType: RaceCategoryType.Olympic, distanceType: DistanceType.Kilometers)
        CoursePatternLocal halfIronman = CoursePatternLocal.findOrSaveWhere(distance: 70.3d, raceCategoryType: RaceCategoryType.HalfIronman, distanceType: DistanceType.Miles)
        CoursePatternLocal ironman = CoursePatternLocal.findOrSaveWhere(distance: 140.6d, raceCategoryType: RaceCategoryType.Ironman, distanceType: DistanceType.Miles)
        CoursePatternLocal tenKilomter = CoursePatternLocal.findOrSaveWhere(distance: 10d, raceCategoryType: RaceCategoryType.TenKilometer, distanceType: DistanceType.Kilometers)
        CoursePatternLocal halfMarathon = CoursePatternLocal.findOrSaveWhere(distance: 13.1d, raceCategoryType: RaceCategoryType.HalfMarathon, distanceType: DistanceType.Miles)
        CoursePatternLocal duathlon = CoursePatternLocal.findOrSaveWhere(distance: 45d, raceCategoryType: RaceCategoryType.Duathlon, distanceType: DistanceType.Kilometers)

        CoursePatternLocalMap.findOrSaveWhere(mapKey: '1Mi Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 1d, raceCategoryType: RaceCategoryType.OneMile, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '2Mi Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 2d, raceCategoryType: RaceCategoryType.Run, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '2.8Mi Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 2.8d, raceCategoryType: RaceCategoryType.Run, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '3Mi Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 3d, raceCategoryType: RaceCategoryType.ThreeMile, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '4Mi Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 4d, raceCategoryType: RaceCategoryType.Run, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '5Mi Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 5d, raceCategoryType: RaceCategoryType.FiveMile, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '5K', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 5d, raceCategoryType: RaceCategoryType.FiveKilometer, distanceType: DistanceType.Kilometers))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '5.7K Trail Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 5.7d, raceCategoryType: RaceCategoryType.Run, distanceType: DistanceType.Kilometers))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '6Mi Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 6d, raceCategoryType: RaceCategoryType.Run, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '7Mi Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 7d, raceCategoryType: RaceCategoryType.Run, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '7K Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 7d, raceCategoryType: RaceCategoryType.Run, distanceType: DistanceType.Kilometers))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '7.3Mi Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 7.3d, raceCategoryType: RaceCategoryType.Run, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '8Mi Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 8d, raceCategoryType: RaceCategoryType.EightMile, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '8K Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 8d, raceCategoryType: RaceCategoryType.EightKilometer, distanceType: DistanceType.Kilometers))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '8.2Mi Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 8.2d, raceCategoryType: RaceCategoryType.Run, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '10K', coursePatternLocal: tenKilomter)
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '10Mi Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 10d, raceCategoryType: RaceCategoryType.TenMile, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '20Mi Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 20d, raceCategoryType: RaceCategoryType.TwentyMile, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '21Mi Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 21d, raceCategoryType: RaceCategoryType.Run, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '25K Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 25d, raceCategoryType: RaceCategoryType.TwentyFiveKilometer, distanceType: DistanceType.Kilometers))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '8K', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 8d, raceCategoryType: RaceCategoryType.EightKilometer, distanceType: DistanceType.Kilometers))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '13.1Mi Run', coursePatternLocal: halfMarathon)
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '13.1K Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 13.1d, raceCategoryType: RaceCategoryType.HalfMarathon, distanceType: DistanceType.Kilometers))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '1/2 Mara', coursePatternLocal: halfMarathon)
        CoursePatternLocalMap.findOrSaveWhere(mapKey: 'Marathon', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 26.2d, raceCategoryType: RaceCategoryType.Marathon, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '26.2Mi Run', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 26.2d, raceCategoryType: RaceCategoryType.Marathon, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: 'Half Iron', coursePatternLocal: halfIronman)
        CoursePatternLocalMap.findOrSaveWhere(mapKey: 'Ironman', coursePatternLocal: ironman)
        CoursePatternLocalMap.findOrSaveWhere(mapKey: 'TRIATHLON/50 YARD SWIM - 21M BIKE - 5M RUN', coursePatternLocal: sprint)
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '0.3Mi Swim, 10Mi Bike/Cycle, 3.1Mi Run', coursePatternLocal: sprint)
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '0.5Mi Swim, 15Mi Bike/Cycle, 3Mi Run', coursePatternLocal: sprint)
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '0.5Mi Swim, 0.5Mi Swim, 15Mi Bike/Cycle, 15Mi Bike/Cycle, 3.1Mi Run, 3.1Mi Run', coursePatternLocal: sprint)
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '0.5Mi Swim, 0.5Mi Swim, 13.3Mi Bike/Cycle, 13.3Mi Bike/Cycle, 3.1Mi Run, 3.1Mi Run', coursePatternLocal: sprint)
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '0.5Mi Swim, 21Mi Bike/Cycle, 5.3Mi Run', coursePatternLocal: sprint)
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '0.4Mi Swim, 15Mi Bike/Cycle, 3.1Mi Run', coursePatternLocal: sprint)
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '0.5Mi Swim', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 0.5d, raceCategoryType: RaceCategoryType.HalfMile, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '5Mi Mountain Bike', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 5d, raceCategoryType: RaceCategoryType.FiveMile, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '5Mi Swim', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 5d, raceCategoryType: RaceCategoryType.FiveMile, distanceType: DistanceType.Miles))
        CoursePatternLocalMap.findOrSaveWhere(mapKey: '1Mi Swim', coursePatternLocal: CoursePatternLocal.findOrSaveWhere(distance: 1d, raceCategoryType: RaceCategoryType.OneMile, distanceType: DistanceType.Miles))
    }

}
