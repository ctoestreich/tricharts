package com.tgid.tri

import com.tgid.tri.auth.User
import com.tgid.tri.exception.RaceResultException
import com.tgid.tri.exception.SegmentResultException
import com.tgid.tri.race.Race
import com.tgid.tri.race.RaceSegment
import com.tgid.tri.race.RaceType
import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.SegmentResult
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.joda.time.Duration

@Secured(["ROLE_USER"])
class DashboardController {

    def springSecurityService
    def raceResultService

    def index() {

        User user = requestedUser

        def userId = user.id

        def results = RaceResult.where {
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
        if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN') && params.int('user.id')) {
            user = User.get(params.int('user.id'))
        }
        user
    }

    def createResult() {
        User user = requestedUser
        def userId = user.id
        switch (params.type) {
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
        def race = Race.get(params.int('raceResult.race'))
        def raceResult = new RaceResult(race: race, user: user)
        race.segments.sort {a, b -> a.segmentOrder <=> b.segmentOrder}.each {
            raceResult.addToSegmentResults(new SegmentResult(raceSegment: it, duration: new Duration(0)))
        }
        render view: 'createRunResult', model: [race: race, user: user, raceResult: raceResult]
    }

    def saveRunResult() {
        User user = requestedUser
        RaceResult raceResult = mapRaceResult(user)

        try {
            raceResultService.createRaceResult(raceResult)
            redirect action:'index', params: params
        }
        catch (SegmentResultException failed) {
            flash.message = failed.message
            flash.segmentResult = failed.problem
            redirect action: 'createRunResult'
        }
        catch (RaceResultException failed) {
            flash.message = failed.message
            flash.raceResult = failed.problem
            redirect action: 'createRunResult'
        }
    }

    private RaceResult mapRaceResult(User user) {
        def race = Race.get(params.int('raceResult.race'))
        def durationHours = params?.int('duration_hours') * 60 * 60
        def durationMinutes = params?.int('duration_minutes') * 60
        def durationSeconds = params?.int('duration_seconds')
        def duration = Duration.standardSeconds(durationHours + durationMinutes + durationSeconds)
        def placeAgeGroup = params?.int('placeAgeGroup')
        def placeOverall = params?.int('placeOverall')
        def raceResult = new RaceResult(race: race, user: user, duration: duration, placeAgeGroup: placeAgeGroup, placeOverall: placeOverall)

        def segmentCount = params?.int('segmentCount') ?: 0
        (0..<segmentCount).each {  i ->
            def segment = params.get("segmentResult[${i}]")
            if (segment) {
                def segmentDurationHours = (segment?.duration_hours ?: 0) * 60 * 60
                def segmentDurationMinutes = (segment?.duration_minutes ?: 0) * 60
                def segmentDurationSeconds = (segment?.duration_seconds ?: 0)
                def segmentDuration = Duration.standardSeconds(segmentDurationHours + segmentDurationMinutes + segmentDurationSeconds)
                def segmentPlaceAgeGroup = segment?.placeAgeGroup
                def segmentPlaceOverall = segment?.placeOverall
                def raceSegmentId = segment?.raceSegment?.id
                println raceSegmentId
                def segmentSegmentResults = new SegmentResult(duration: segmentDuration, placeOverall: segmentPlaceOverall, placeAgeGroup: segmentPlaceAgeGroup, raceSegment: RaceSegment.get(raceSegmentId))
                raceResult.addToSegmentResults(segmentSegmentResults)
            }
        }
        println raceResult.segmentResults.size()
        raceResult
    }

    def races() {}
}
