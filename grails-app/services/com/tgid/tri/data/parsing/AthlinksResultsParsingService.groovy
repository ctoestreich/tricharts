package com.tgid.tri.data.parsing

import com.tgid.tri.auth.Racer
import com.tgid.tri.auth.User
import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.RaceResultService
import groovy.json.JsonSlurper
import com.tgid.tri.race.*

class AthlinksResultsParsingService {

    static transactional = false

    def grailsApplication
    RaceService raceService
    RaceResultService raceResultService
    CoursePatternService coursePatternService
    RaceCategoryService raceCategoryService

    def retrieveResults(User user) {
        importAthlinkRaces(user)
    }

    def retrieveResults(Racer racer) {
        try {
            importRacerRaces(racer, racer.user)
            racer.save(lastImport: new Date())
        } catch(Exception e) {
            log.error e
        }
    }

    private void importAthlinkRaces(User user) {
        user?.racers?.each {
            if(it.lastImport < new Date() - 1) {
                importRacerRaces(it, user)
            }
        }
    }

    private void importRacerRaces(Racer racer, User user) {
        def racesUrl = "http://api.athlinks.com/athletes/results/${racer.racerID}?key=${grailsApplication.config.athlinks.key}&format=json"
        def races = null
        try {
            String apiString = new URL(racesUrl).text
            races = new JsonSlurper().parseText(apiString)
        } catch(Exception e) {
            throw e
        }

        races.each { raceMap ->
            importRaces(user, raceMap)
        }

        racer.save(lastImport: new Date())
    }

    private void importRaces(User user, Map raceMap) {
        def race = Race.findByAthlinkRaceID(raceMap?.Race?.RaceID)
        if(!race && raceMap?.Race?.RaceID) {
            log.info "Creating race for ${raceMap.Race.RaceID} - ${raceMap.Race.RaceName}"
            raceMap.Race.Courses.each { course ->
                mapCourseAndResult(user, raceMap, course)
            }
        }

        raceMap?.Race?.Courses?.each { course ->
            importResult(user, raceMap.EntryID, course.EventCourseID)
        }
    }

    private void mapCourseAndResult(User user, Map raceMap, Map course) {
        try {
            def race = null
            Race.withNewTransaction {
                def coursePatternLocal = coursePatternService.lookup(course)
                if(!coursePatternLocal) {
                    log.info "!! Can't resolve pattern for ${course?.CoursePattern} - skipping"
                    return;
                }
                race = new Race(
                        name: raceMap.Race.RaceName,
                        date: new Date(raceMap.Race.RaceDate.toString().replaceAll(/\/Date\((\d+)\)\//, '$1') as Long),
                        raceType: mapRaceType(course),
                        raceCategoryType: coursePatternLocal?.raceCategoryType,
                        distanceType: coursePatternLocal?.distanceType,
                        distance: coursePatternLocal?.distance,
                        athlinkRaceID: raceMap.Race.RaceID,
                        eventCourseID: course.EventCourseID,
                        coursePattern: com.tgid.tri.race.CoursePattern.get(course?.CoursePatternID),
                        raceCategory: com.tgid.tri.race.RaceCategory.get(course?.RaceCatID),
                        statusType: StatusType.Approved
                )
                race = raceService.saveRace(race, course)
            }
            if(race) {
                importResult(user, raceMap.EntryID, course.EventCourseID)
            }
        } catch(Exception e) {
            throw e
        }
    }

    private RaceResult importResult(User user, Long entryID, Long eventCourseID) {
        def raceResult = RaceResult.findByAthlinkEntryID(entryID)
        if(!entryID || !eventCourseID) {
            return null
        }

        if(raceResult) {
            return raceResult
        }

        log.info "Creating result for ${eventCourseID} entry: ${entryID}"

        def racesUrl = "http://api.athlinks.com/results/${entryID}?key=${grailsApplication.config.athlinks.key}&format=json"
        def result = null

        try {
            String apiString = new URL(racesUrl).text
            result = new JsonSlurper().parseText(apiString)
        } catch(Exception e) {
            throw e
        }
        if(result?.EntryID) {
            return raceResultService.mapRaceResultAthlinks(user, eventCourseID, result)
        }
        return null
    }

    private RaceType mapRaceType(Map course) {
        switch(course?.RaceCatDesc) {
            case 'Mountain Biking':
                return RaceType.Biking
            case 'Swimming':
                return RaceType.Swimming
            case 'Running':
                return RaceType.Running
            case 'Triathlon & Multisport':
            case 'Off-Road Triathlon':
                return RaceType.Triathlon
            case 'Duathlon':
            case 'Off-Road Duathlon':
                return RaceType.Duathlon
            case 'Aquathlon':
            case 'Aquathon':
                return RaceType.Aquathon
        }

        println "!! Could not mapRaceType - ${course?.RaceCatDesc}"

        return RaceType.Triathlon
    }

    def importCoursePatterns() {
        def results = true
        def page = 1
        while(results) {
            def url = "http://api.athlinks.com/enums/CoursePatterns?key=${grailsApplication.config.athlinks.key}&page=${page}&pageSize=10000&format=json"
            def coursePatterns = null
            try {
                String apiString = new URL(url).text
                coursePatterns = new JsonSlurper().parseText(apiString)
            } catch(Exception e) {
                log.error e
            }

            results = ((coursePatterns?.List?.size() ?: 0) > 0)
            coursePatterns?.List?.each { coursePattern ->
                coursePatternService.importCoursePattern(coursePattern)
            }
            page++
        }
    }

    def importRaceCategories() {
        def url = "http://api.athlinks.com/enums/RaceCategories?key=${grailsApplication.config.athlinks.key}&format=json"
        def raceCategories = null
        try {
            String apiString = new URL(url).text
            raceCategories = new JsonSlurper().parseText(apiString)
        } catch(Exception e) {
            log.error e
        }

        raceCategories.each { raceCategory ->
            raceCategoryService.importRaceCategory(raceCategory)
        }
    }
}
