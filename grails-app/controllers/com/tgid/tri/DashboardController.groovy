package com.tgid.tri

import com.tgid.tri.auth.User
import com.tgid.tri.race.Race
import com.tgid.tri.race.RaceType
import com.tgid.tri.results.RaceResults
import com.tgid.tri.results.SegmentResult
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.joda.time.Duration
import com.tgid.tri.results.RaceResult

@Secured(["ROLE_USER"])
class DashboardController {

    def springSecurityService

    def index() {

        User user = requestedUser

        def userId = user.id

        def results = RaceResults.where {
            user.id == userId
            //segmentResults { segment { segmentType == SegmentType.Run && distance == 3.1 } }
        }

        def runs = results.where {
            race.raceType == RaceType.Running
        }.max(params.int('max') ?: 500)

        def triathlons = results.where {
            race.raceType == RaceType.Triathlon
        }.max(params.int('max') ?: 500)


        render view: 'index', model: [runs: runs, triathlons: triathlons, user: user]
    }

    private User getRequestedUser() {
        User user = ((User) springSecurityService.currentUser)
        if(SpringSecurityUtils.ifAllGranted('ROLE_ADMIN') && params.int('user.id')) {
            user = User.get(params.int('user.id'))
        }
        user
    }

    def createResult() {
        User user = requestedUser
        def userId = user.id
        switch(params.type) {
            case 'Run':
                def results = RaceResult.where {
                    user.id == userId
                    //segmentResults { segment { segmentType == SegmentType.Run && distance == 3.1 } }
                }
                def runResults = results.where {
                    race.raceType == RaceType.Running
                }
                def runs = Race.findAll("from Race where raceType = 'Running' and id not in (:excludedRaces)", ["excludedRaces": runResults.collect { it.race.id }])
                render view: 'createRunResult', model: [raceResult: new RaceResult(), user: user, runs: runs]
                break
            default:
                render view: 'createRunResult', model: [raceResult: new RaceResult(), user: user]
        }
    }

    def createRunResult() {
        User user = requestedUser
        def userId = user.id
        def race = Race.get(params.int('raceResults.race'))
        def raceResults = new RaceResult(race: race, user: user)
        race.segments.sort {a, b -> a.segmentOrder <=> b.segmentOrder}.each {
            raceResults.addToSegmentResults(new SegmentResult(raceSegment: it, duration: new Duration(0)))
        }
        render view: 'createRunResult', model: [race: race, user: user, raceResults: raceResults]
    }

    def saveRunResult() {
        User user = requestedUser
        def userId = user.id
        def race = Race.get(params.int('raceResults.race'))
        def durationHours = params?.int('duration_hours') * 60 * 60
        def durationMinutes = params?.int('duration_minutes') * 60
        def durationSeconds = params?.int('duration_seconds')
        def duration = Duration.standardSeconds(durationHours + durationMinutes + durationSeconds)
        def placeAgeGroup = params?.int('placeAgeGroup')
        def placeOverall = params?.int('placeOverall')
        def raceResults = new RaceResult(race: race, user: user, duration: duration, placeAgeGroup: placeAgeGroup, placeOverall: placeOverall)

        println params?.segmentCount
        params.list("segmentResults").each {
            println "segmentResults $it"
        }

        params?.segmentResults?.each {
            println "segmentResults - ${it}"
        }

//        (params?.segmentCount ?: 0).times {
//            println params.get("segment${it}")
//            println params.get("segment")
////            def durationHours = params?.int('duration_hours') * 60 * 60
////            def durationMinutes = params?.int('duration_minutes') * 60
////            def durationSeconds = params?.int('duration_seconds')
////            def duration = Duration.standardSeconds(durationHours + durationMinutes + durationSeconds)
////            def placeAgeGroup = params?.int('placeAgeGroup')
////            def placeOverall = params?.int('placeOverall')
////            def segmentResults = new SegmentResult()
//        }
    }

    def races() {}
}
