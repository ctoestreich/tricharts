package com.tgid.tri.data.parsing

import com.tgid.tri.auth.Racer
import com.tgid.tri.auth.User
import com.tgid.tri.common.CoursePatternService
import com.tgid.tri.race.Race
import com.tgid.tri.race.RaceType
import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.RaceResultService
import com.tgid.tri.results.RaceService
import groovy.json.JsonSlurper

class AthlinksResultsParsingService {

    static transactional = false

    def grailsApplication
    RaceService raceService
    RaceResultService raceResultService
    CoursePatternService coursePatternService

    def retrieveResults(User user) {
        importAthlinkRaces(user)
    }

    def retrieveResults(Racer racer) {
        importRacerRaces(racer, racer.user)
    }

    private void importAthlinkRaces(User user) {
        user.racers.each {
            importRacerRaces(it, user)
        }
    }

    private void importRacerRaces(Racer racer, User user) {
        def racesUrl = "http://api.athlinks.com/athletes/results/${racer.racerID}?key=${grailsApplication.config.athlinks.key}&format=json"
        def races = null
        try {
            String apiString = new URL(racesUrl).text
            races = new JsonSlurper().parseText(apiString)
        } catch(Exception e) {
            log.error e
        }

        races.each { raceMap ->
            importRaces(user, raceMap)
        }
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
            def coursePattern = coursePatternService.lookup(course)
            def race = new Race(
                    name: raceMap.Race.RaceName,
                    date: new Date(raceMap.Race.RaceDate.toString().replaceAll(/\/Date\((\d+)\)\//, '$1') as Long),
                    raceType: mapRaceType(course),
                    raceCategoryType: coursePattern?.raceCategoryType,
                    distanceType: coursePattern?.distanceType,
                    distance: coursePattern?.distance,
                    athlinkRaceID: raceMap.Race.RaceID,
                    eventCourseID: course.EventCourseID
            )
            race = raceService.saveRace(race, course)
            if(race) {
                importResult(user, raceMap.EntryID, course.EventCourseID)
            }
        } catch(Exception e) {
            log.error e
        }
    }

    private RaceResult importResult(User user, Long entryID, Long eventCourseID) {
        def raceResult = RaceResult.findByAthlinkEntryID(entryID)
        if(!entryID || !eventCourseID){
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
            log.error e
        }
        if(result?.EntryID) {
            return raceResultService.mapRaceResultAthlinks(user, eventCourseID, result)
        }
        return null
    }

    private RaceType mapRaceType(Map course) {
        switch(course?.RaceCatDesc) {
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
            case 'Aquathon':
                return RaceType.Aquathon
        }

        println "!! Could not mapRaceType - ${course?.RaceCatDesc}"

        return RaceType.Triathlon
    }
}
