package com.tgid.tri.data.parsing

import com.tgid.tri.auth.Racer
import com.tgid.tri.auth.User
import com.tgid.tri.data.ImportLog
import com.tgid.tri.data.ImportLoggingService
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
    ImportLoggingService importLoggingService

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

    def updateAthlinksRace(Race race) {
        def racesUrl = "http://api.athlinks.com/races/${race.athlinkRaceID}?key=${grailsApplication.config.athlinks.key}&format=json"

        log.debug "fetching ${racesUrl}"

        def raceMap = [:]
        try {
            String apiString = new URL(racesUrl).text
            raceMap = new JsonSlurper().parseText(apiString)
        } catch(Exception e) {
            throw e
        }

        raceMap?.Courses?.each { course ->
            def coursePatternLocal = coursePatternService.lookup(course)
            def raceUpdate = Race.findByAthlinkRaceIDAndCourseID(race.athlinkRaceID, course.CourseID)
            def state = com.tgid.tri.auth.State.findByProvID(raceMap.StateProvID)
            def country = com.tgid.tri.auth.Country.findByCountryID(raceMap.CountryID)
            Race.withTransaction {
                try {
                    if(raceUpdate) {
                        raceUpdate.resultsUrl = getUrl(raceMap?.WebSite)
                        raceUpdate.name = raceMap?.RaceName
                        raceUpdate.date = new Date(raceMap?.RaceDate?.toString()?.replaceAll(/\/Date\((\d+)\)\//, '$1') as Long)
                        raceUpdate.raceType = mapRaceType(course)
                        raceUpdate.raceCategoryType = coursePatternLocal?.raceCategoryType
                        raceUpdate.distanceType = coursePatternLocal?.distanceType
                        raceUpdate.distance = coursePatternLocal?.distance
                        raceUpdate.athlinkRaceID = raceMap.RaceID
                        raceUpdate.eventCourseID = course.EventCourseID
                        raceUpdate.courseID = course.CourseID
                        raceUpdate.coursePattern = com.tgid.tri.race.CoursePattern.get(course?.CoursePatternID)
                        raceUpdate.raceCategory = com.tgid.tri.race.RaceCategory.get(course?.RaceCatID)
                        raceUpdate.state = state
                        raceUpdate.country = country
                        raceUpdate = raceService.saveRace(raceUpdate)
                    }
                } catch(Exception e) {
                    throw e
                }
            }
        }
    }

    private String getUrl(String url) {
        try {
            new URL(url)
            return url
        } catch(MalformedURLException e) {
            log.error e
            return ''
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

        log.debug "fetching ${racesUrl}"

        def races
        try {
            String apiString = new URL(racesUrl).text
            races = new JsonSlurper().parseText(apiString)
        } catch(Exception e) {
            throw e
        }

        races?.List?.each { raceMap ->
            importRaces(user, raceMap)
        }

        racer.lastImport = new Date()
        racer.save(flush: true)
    }

    private void importRaces(user, raceMap) {
        log.debug "Race id: ${raceMap?.Race?.RaceID}"
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
                    log.info "Can't resolve pattern for ${course?.CoursePattern} - skipping"
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
                        courseID: course.CourseID,
                        coursePattern: com.tgid.tri.race.CoursePattern.get(course?.CoursePatternID),
                        raceCategory: com.tgid.tri.race.RaceCategory.get(course?.RaceCatID),
                        statusType: StatusType.Approved,
                        state: com.tgid.tri.auth.State.findByAbbrev(raceMap.Race.StateProvAbbrev),
                        country: com.tgid.tri.auth.Country.findByCountryID(raceMap.Race.CountryID),
                        city: raceMap?.Race?.City,
                        resultsUrl: raceMap?.Race?.WebSite
                )
                race = raceService.createRace(race, course)
            }
            if(race) {
                importResult(user, raceMap.EntryID, course.EventCourseID)
            }

            importLoggingService.save(new ImportLog(importName: 'Race Import', error: false, description: "Race imported ${race.toString()}", complete: true))

        } catch(Exception e) {
            importLoggingService.save(new ImportLog(importName: 'Race Import', error: true, description: e.message, complete: true))
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

        log.debug "fetching ${racesUrl}"

        def result

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
            case 'Cycling':
                return RaceType.Biking
            case 'Swimming':
                return RaceType.Swimming
            case 'Running':
            case 'Trail Running':
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

        importLoggingService.save(new ImportLog(importName: 'Map Race Type Failure', error: true, description: course?.RaceCateDesc, complete: true))

        return RaceType.Triathlon
    }

    def importCoursePatterns() {
        def results = true
        def page = 1
        while(results) {
            def racesUrl = "http://api.athlinks.com/enums/CoursePatterns?key=${grailsApplication.config.athlinks.key}&page=${page}&pageSize=10000&format=json"

            log.debug "fetching ${racesUrl}"

            def coursePatterns = null
            try {
                String apiString = new URL(racesUrl).text
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
        def racesUrl = "http://api.athlinks.com/enums/RaceCategories?key=${grailsApplication.config.athlinks.key}&format=json"
        log.debug "fetching ${racesUrl}"
        def raceCategories = null
        try {
            String apiString = new URL(racesUrl).text
            raceCategories = new JsonSlurper().parseText(apiString)
        } catch(Exception e) {
            log.error e
        }

        raceCategories.each { raceCategory ->
            raceCategoryService.importRaceCategory(raceCategory)
        }
    }
}
