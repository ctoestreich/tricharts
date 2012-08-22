package com.tgid.tri.data.parsing

import com.tgid.tri.auth.Racer
import com.tgid.tri.auth.User
import com.tgid.tri.common.CoursePatternService
import com.tgid.tri.race.DistanceType
import com.tgid.tri.race.Race
import com.tgid.tri.race.RaceCategoryType
import com.tgid.tri.race.RaceType
import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.RaceResultService
import com.tgid.tri.results.RaceService
import groovy.json.JsonSlurper

class AthlinksResultsParsingService {

    def grailsApplication
    RaceService raceService
    RaceResultService raceResultService
    CoursePatternService coursePatternService

    def retrieveResults(User user) {
        importRaces(user)
    }

    def retrieveResults(Racer racer) {
        importRacerRaces(racer, racer.user)
    }

    private void importRaces(User user) {
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
            def race = importRace(raceMap)
            def result = importResult(user, race, raceMap.EntryID)
        }
    }

    private Race importRace(raceMap) {
        def race = Race.findByAthlinkRaceId(raceMap?.Race?.RaceID)
        if(!race && raceMap?.Race?.RaceID) {
            log.info "Creating race for ${raceMap.Race.RaceID} - ${raceMap.Race.RaceName}"
            def course = raceMap.Race.Courses[0]
            def coursePattern = coursePatternService.lookup(course?.CoursePattern)
            race = new Race(
                    name: raceMap.Race.RaceName,
                    date: new Date(raceMap.Race.RaceDate.toString().replaceAll(/\/Date\((\d+)\)\//, '$1') as Long),
                    raceType: mapRaceType(course),
                    raceCategoryType: coursePattern.raceCategoryType,
                    distanceType: coursePattern.distanceType,
                    distance: coursePattern.distance,
                    athlinkRaceId: raceMap.Race.RaceID
            )
            raceService.saveRace(race, course)
        }
        race
    }

    private RaceResult importResult(User user, Race race, Long entryID) {
        if(RaceResult.findByAthlinkEntryID(entryID)) {
            return
        }

        log.info "Creating result for ${race} entry: ${entryID}"

        def racesUrl = "http://api.athlinks.com/results/${entryID}?key=${grailsApplication.config.athlinks.key}&format=json"
        def result = null

        try {
            String apiString = new URL(racesUrl).text
            result = new JsonSlurper().parseText(apiString)
        } catch(Exception e) {
            log.error e
        }

        raceResultService.mapRaceResultAthlinks(user, race, result)
    }

    private RaceType mapRaceType(Map course) {
        switch(course?.RaceCatDesc) {
            case 'Running':
                return RaceType.Running
            case 'Triathlon & Multisport':
                return RaceType.Triathlon
            case 'Duathlon':
            case 'Off-Road Duathlon':
                return RaceType.Duathlon
        }

        println "!! Could not mapRaceType - ${course?.RaceCatDesc}"

        return RaceType.Triathlon
    }
}
