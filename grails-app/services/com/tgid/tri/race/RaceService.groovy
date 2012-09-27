package com.tgid.tri.race

import grails.validation.ValidationException
import jodd.servlet.tag.ElseTag

class RaceService {

    SegmentService segmentService

    Race saveRace(Race race){
        if(race.validate()) {
            try {
                race.save(flush: true)
            } catch(ValidationException e) {
                log.error "error saving race: ${race.name}"
                throw e
            }
        } else {
            println 'Race Validation Errors!'
            race?.errors?.allErrors?.each {
                log.error it
            }
            return null
        }

        return race
    }

    Race createRace(Race race, Map course = [:]) {
        if(race.raceType == RaceType.Running) { createRunSegments(race) }
        if(race.raceType == RaceType.Biking) { createBikeSegments(race) }
        if(race.raceType == RaceType.Triathlon || race.raceType == RaceType.Aquathon) { createTriathlonSegments(race, course) }
        if(race.raceType == RaceType.Duathlon) { createDuathlonSegments(race, course) }

        if(race.validate()) {
            try {
                race.save(flush: true)
            } catch(ValidationException e) {
                log.error "error saving race: ${race.name}"
                log.error e.message
                return null
            }
        } else {
            println 'Race Validation Errors!'
            race?.errors?.allErrors?.each {
                println it
                log.error it
            }
            return null
        }

        return race
    }

    void createBikeSegments(Race race) {
        def segment = Segment.findOrSaveWhere(segmentType: SegmentType.Bike, distanceType: race?.distanceType, distance: race?.distance)
        if(segment.validate()){
            race.addToSegments(new RaceSegment(segment: segment))
        }
    }

    void createRunSegments(Race race) {
        def segment = Segment.findOrSaveWhere(segmentType: SegmentType.Run, distanceType: race?.distanceType, distance: race?.distance)
        if(segment.validate()){
                race.addToSegments(new RaceSegment(segment: segment))
        } else {
            println 'Validation Errors!'
            race?.errors?.allErrors?.each {
                println it
                log.error it
            }
        }
    }

    void createDuathlonSegments(Race race, Map course = [:]) {
        Segment runSegment1 = null, t1Segment = null, bikeSegment = null, t2Segment = null, runSegment2 = null
        if(course && course.CoursePattern.contains(',')) {
            course.CoursePattern.split(',').each {
                def segment = it.toString().toUpperCase()
                if(segment.contains('RUN') && !runSegment1) {runSegment1 = mapSegment(segment, 'RUN', SegmentType.DuRun1)}
                else if(segment.contains('BIKE') && !bikeSegment) {bikeSegment = mapSegment(segment, 'BIKE', SegmentType.Bike)}
                else if(segment.contains('RUN') && !runSegment2 && runSegment1) {runSegment2 = mapSegment(segment, 'RUN', SegmentType.DuRun2)}
                if(!t1Segment) { t1Segment = Segment.findOrSaveWhere(segmentType: SegmentType.T1, distanceType: DistanceType.Meters, distance: 400d) }
                if(!t2Segment) { t2Segment = Segment.findOrSaveWhere(segmentType: SegmentType.T2, distanceType: DistanceType.Meters, distance: 400d) }
            }
            addSegmentsToRace(race, [runSegment1, t1Segment, bikeSegment, t2Segment, runSegment2])
        }
    }

    void createTriathlonSegments(Race race, Map course = [:]) {
        Segment swimSegment = null, t1Segment = null, bikeSegment = null, t2Segment = null, runSegment = null

        if(course && course.CoursePattern.contains(',')) {
            course.CoursePattern.split(',').each {
                def segment = it.toString().toUpperCase()
                if(segment.contains('SWIM') && !swimSegment) {swimSegment = mapSegment(segment, 'SWIM', SegmentType.Swim)}
                if(segment.contains('BIKE') && !bikeSegment) {bikeSegment = mapSegment(segment, 'BIKE', SegmentType.Bike)}
                if(segment.contains('RUN') && !runSegment) {runSegment = mapSegment(segment, 'RUN', SegmentType.Run)}
                if(!t1Segment) { t1Segment = Segment.findOrSaveWhere(segmentType: SegmentType.T1, distanceType: DistanceType.Meters, distance: 400d) }
                if(!t2Segment) { t2Segment = Segment.findOrSaveWhere(segmentType: SegmentType.T2, distanceType: DistanceType.Meters, distance: 400d) }
            }
            addSegmentsToRace(race, [swimSegment, t1Segment, bikeSegment, t2Segment, runSegment])
        } else {
            mapTriathlonRaceSegments(race, swimSegment, runSegment, t1Segment, t2Segment, bikeSegment)
        }

    }

    private void addSegmentsToRace(Race race, List<Segment> segments) {
        segments.each {
            if(it) {
                race.addToSegments(new RaceSegment(segment: it))
            }
        }
    }

    private void mapTriathlonRaceSegments(Race race, Segment swimSegment, Segment runSegment, Segment t1Segment, Segment t2Segment, Segment bikeSegment) {
        def swimDistance = [distance: 750d, distanceType: DistanceType.Meters]
        def bikeDistance = [distance: 20d, distanceType: DistanceType.Kilometers]
        def runDistance = [distance: 5d, distanceType: DistanceType.Kilometers]

        //map known race distances
        if(race.raceCategoryType == RaceCategoryType.Olympic) {
            swimDistance = [distance: 1.5d, distanceType: DistanceType.Kilometers]
            bikeDistance = [distance: 40d, distanceType: DistanceType.Kilometers]
            runDistance = [distance: 10d, distanceType: DistanceType.Kilometers]
        } else if(race.raceCategoryType == RaceCategoryType.Ironman) {
            swimDistance = [distance: 2.4d, distanceType: DistanceType.Miles]
            bikeDistance = [distance: 112d, distanceType: DistanceType.Miles]
            runDistance = [distance: 26.2d, distanceType: DistanceType.Miles]
        } else if(race.raceCategoryType == RaceCategoryType.HalfIronman) {
            swimDistance = [distance: 1.2d, distanceType: DistanceType.Miles]
            bikeDistance = [distance: 56d, distanceType: DistanceType.Miles]
            runDistance = [distance: 13.1d, distanceType: DistanceType.Miles]
        }

        if(!swimSegment) {swimSegment = Segment.findOrSaveWhere(segmentType: SegmentType.Swim, distanceType: swimDistance.distanceType, distance: swimDistance.distance)}
        if(!t1Segment) {t1Segment = Segment.findOrSaveWhere(segmentType: SegmentType.T1, distanceType: DistanceType.Meters, distance: 400d)}
        if(!bikeSegment) {bikeSegment = Segment.findOrSaveWhere(segmentType: SegmentType.Bike, distanceType: bikeDistance.distanceType, distance: bikeDistance.distance)}
        if(!t2Segment) {t2Segment = Segment.findOrSaveWhere(segmentType: SegmentType.T2, distanceType: DistanceType.Meters, distance: 400d)}
        if(!runSegment) {runSegment = Segment.findOrSaveWhere(segmentType: SegmentType.Run, distanceType: runDistance.distanceType, distance: runDistance.distance)}

        addSegmentsToRace(race, [swimSegment, t1Segment, bikeSegment, t2Segment, runSegment])
    }

    private Segment mapSegment(String segment, String search, SegmentType segmentType) {
        segment = segment.replaceAll(/[ ]*${search}.*/, '')
        def distance = segment.replaceAll(/(\d+\.*\d*).*/, '$1')
        def distanceType = mapDistanceType(segment.replaceAll(/\d+\.*\d*([ A-Z]+)/, '$1'))
        try {
            return Segment.findOrSaveWhere(segmentType: segmentType, distanceType: distanceType, distance: Double.parseDouble(distance))
        } catch(Exception e){
            log.error e
            return null
        }
    }

    private DistanceType mapDistanceType(String distanceType) {
        switch(distanceType.toUpperCase().trim()) {
            case 'MI':
            case 'MILE':
            case 'MILES':
            case 'MI MOUNTAIN':
            case 'MI TRAIL':
                return DistanceType.Miles
            case 'K':
            case 'K TRAIL':
            case 'K MOUNTAIN':
            case 'KM':
            case 'KILOMETER':
            case 'KILOMETERS':
                return DistanceType.Kilometers
            case 'YARD':
            case 'YARDS':
            case 'Y':
            case 'YD':
                return DistanceType.Yards
            case 'FT':
                return DistanceType.Feet
            case 'M':
                return DistanceType.Meters
        }
        println "!! Could not mapDistanceType - $distanceType"
        return DistanceType.Miles
    }
}
