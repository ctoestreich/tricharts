package com.tgid.tri.data.parsing

import com.tgid.tri.auth.User
import com.tgid.tri.race.DistanceType
import com.tgid.tri.race.Race
import com.tgid.tri.race.RaceCategoryType
import com.tgid.tri.race.RaceType
import com.tgid.tri.results.RaceService
import groovy.json.JsonSlurper
import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.RaceResultService

class AthlinksResultsParsingService {

    def grailsApplication
    RaceService raceService
    RaceResultService raceResultService

    def retrieveResults(User user) {
        importRaces(user)
    }

    private void importRaces(User user) {
        def racesUrl = "http://api.athlinks.com/athletes/results/${user.athlinkRacerId}?key=${grailsApplication.config.athlinks.key}&format=json"
        def races = null
        try {
            String apiString = new URL(racesUrl).text
            races = new JsonSlurper().parseText(apiString)
        } catch(Exception e) {
            log.error e
        }

        races.each {
            if(it?.Race?.RaceID && !Race.findByAthlinkRaceId(it.Race.RaceID)) {
                def course = it.Race.Courses[0]
                def race = new Race(
                        name: it.Race.RaceName,
                        date: new Date(it.Race.RaceDate.toString().replaceAll(/\/Date\((\d+)\)\//, '$1') as Long),
                        raceType: mapRaceType(course),
                        raceCategoryType: mapRaceCategoryType(course),
                        distanceType: mapDistanceType(course),
                        distance: mapDistance(course),
                        athlinkRaceId: it.Race.RaceID
                )
                raceService.saveRace(race, course)
                importResult(user, race, it.EntryID)
            }
        }
    }

    private void importResult(User user, Race race, Long entryID){
        if(RaceResult.findByAthlinkEntryID(entryID)){
            return
        }

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

    private mapRaceResult

    private Float mapDistance(Map course) {
        if(course?.CoursePattern == '1Mi Run') { return 1 }
        if(course?.CoursePattern == '5K') { return 5 }
        if(course?.CoursePattern == '8K Run' || course?.CoursePattern == '8K') { return 8 }
        if(course?.CoursePattern == 'Marathon') { return 26.2f }
        if(course?.CoursePattern == '1/2 Mara') { return 13.1f }
        if(course?.CoursePattern == 'Half Iron') { return 70.3f }
        if(course?.CoursePattern == 'Ironman') { return 140.6f }

        println "!! Could not mapDistance - ${course?.CoursePattern}"

        return 1
    }

    private DistanceType mapDistanceType(Map course) {
        if(course?.CoursePattern == '8K Run' || course?.CoursePattern == '8K') { return DistanceType.Kilometers }
        if(course?.CoursePattern == '5K') { return DistanceType.Kilometers }
        if(course?.CoursePattern == '10K') { return DistanceType.Kilometers }

        return DistanceType.Miles
    }

    private RaceCategoryType mapRaceCategoryType(Map course) {
        if(course?.CoursePattern == '1Mi Run') { return RaceCategoryType.OneMile}
        if(course?.CoursePattern == '5K') { return RaceCategoryType.FiveKilometer}
        if(course?.CoursePattern == '1/2 Mara') { return RaceCategoryType.HalfMarathon}
        if(course?.CoursePattern == 'Marathon') { return RaceCategoryType.Marathon}
        if(course?.CoursePattern == 'Half Iron') { return RaceCategoryType.HalfIronman }
        if(course?.CoursePattern == 'Ironman') { return RaceCategoryType.Ironman}
        if(course?.CourseName?.toString()?.toUpperCase()?.contains('SPRINT') && course?.RaceCatDesc == 'Triathlon & Multisport') { return RaceCategoryType.Sprint}
        if(course?.CourseName?.toString()?.toUpperCase()?.contains('OLYMPIC') && course?.RaceCatDesc == 'Triathlon & Multisport') { return RaceCategoryType.Olympic}

        println "!! Could not mapRaceCategoryType - ${course?.CoursePattern}"

        return RaceCategoryType.Sprint
    }

    private RaceType mapRaceType(Map course) {
        switch(course?.RaceCatDesc) {
            case 'Running':
                return RaceType.Running
            case 'Triathlon & Multisport':
                return RaceType.Triathlon
        }

        println "!! Could not mapRaceType - ${course?.RaceCatDesc}"

        return RaceType.Triathlon
    }
}
