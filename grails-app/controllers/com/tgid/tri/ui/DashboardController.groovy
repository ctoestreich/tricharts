package com.tgid.tri.ui

import com.tgid.tri.BaseController
import com.tgid.tri.auth.User
import com.tgid.tri.exception.RaceResultException
import com.tgid.tri.exception.SegmentResultException
import com.tgid.tri.race.Race
import com.tgid.tri.race.RaceType
import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.SegmentResult
import grails.plugins.springsecurity.Secured
import org.joda.time.Duration

@Secured(["ROLE_USER"])
class DashboardController extends BaseController {

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

    def createResult() {
        User user = requestedUser
        def userId = user.id
        List<Race> races
        switch (params?.type) {
            case 'Run':
                races = findRacesWithNoResults(userId, RaceType.Running)
                break
            case 'Triathlon':
                races = findRacesWithNoResults(userId, RaceType.Triathlon)
                break
            default:
                races = []
        }
        render view: 'createResult', model: [raceResult: new RaceResult(), user: user, races: races]
    }

    private List<Race> findRacesWithNoResults(userId, RaceType raceType) {
        def results = RaceResult.where {
            user.id == userId
        }
        def raceResults = results.where {
            race.raceType == raceType
        }
        if (raceResults.count() > 0)
            return Race.findAll("from Race where raceType = '${raceType}' and id not in (:excludedRaces)", ["excludedRaces": raceResults?.collect { it.race.id } ?: []])
        else
            return Race.findAll("from Race where raceType = '${raceType}'")
    }

    def selectRace() {
        User user = requestedUser
        def userId = user.id
        def race = Race.get(params.int('race.id'))
        def raceResult = new RaceResult(race: race, user: user)
        race.segments.sort {a, b -> a.segmentOrder <=> b.segmentOrder}.each {
            raceResult.addToSegmentResults(new SegmentResult(raceSegment: it, duration: new Duration(0)))
        }
        render view: 'createResult', model: [race: race, user: user, raceResult: raceResult]
    }

    def saveResult() {
        User user = requestedUser
        RaceResult raceResult = mapRaceResult(user)

        try {
            raceResultService.createRaceResult(raceResult)
            redirect action: 'index', params: params
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
        params.user = user

        def raceResult = new RaceResult(params)

        def segmentCount = params?.int('segmentCount') ?: 0
        (0..<segmentCount).each {
            String key = "segmentResult[$it]".toString()
            Map segment = params.get(key) as Map
            if (segment) {
                def segmentSegmentResults = new SegmentResult(segment)
                raceResult.addToSegmentResults(segmentSegmentResults)
            }
        }
        raceResult
    }

    def deleteRaceResult() {
        User user = requestedUser
        try {
            raceResultService.deleteRaceResult(params?.int('raceResultId') ?: 0, user);
            redirect action: 'index'
        } catch (RaceResultException failed) {
            flash.message = failed.message
            flash.raceResult = failed.problem
            redirect action: 'index'
        }
    }

    def races() {}
}
