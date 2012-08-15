package com.tgid.tri.ui

import com.tgid.tri.BaseController
import com.tgid.tri.auth.User
import com.tgid.tri.exception.RaceResultException
import com.tgid.tri.exception.SegmentResultException
import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.SegmentResult
import grails.plugins.springsecurity.Secured
import org.joda.time.Duration
import com.tgid.tri.race.*

@Secured(["ROLE_USER"])
class DashboardController extends BaseController {

    def raceResultService
    def raceService

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

    def progression() {
        User user = requestedUser

        def races = getRaceCategoriesByType(params?.raceType)

        render view: 'progression', model: [raceResult: new RaceResult(), user: user, races: races]
    }

    def addSegments() {
        switch(request.method) {
            case 'POST':
                println params?.raceId
                def raceInstance = Race.get(params?.raceId)

                params?.segments?.toString()?.split(',')?.each {
                    println it
                    raceInstance.addToSegments(new RaceSegment(segment: Segment.get(it)))
                }

                if(!raceInstance.save(flush: true)) {
                    render view: 'addSegments', model: [raceInstance: raceInstance]
                    return
                }

                flash.message = message(code: 'race.created.pending.message', args: [message(code: 'race.label', default: 'Race'), raceInstance.name])
                redirect action: 'index', id: raceInstance.id
                break
        }
    }

    def addRace() {
        switch(request.method) {
            case 'GET':
                [raceInstance: new Race(params)]
                break
            case 'POST':
                def raceInstance = new Race(params)

                if(raceInstance.raceType == RaceType.Running) {
                    raceService.createRunSegments(raceInstance)
                } else if(raceInstance.raceType == RaceType.Biking) {
                    raceService.createBikeSegments(raceInstance)
                }

                if(!raceInstance.validate()) {
                    render view: 'addRace', model: [raceInstance: raceInstance]
                    return
                } else {
                    raceInstance.save(flush: true)
                }

                flash.message = message(code: 'race.created.pending.message', args: [message(code: 'race.label', default: 'Race'), raceInstance.name])
                if(raceInstance.raceType == RaceType.Triathlon) {
                    render view: 'addSegments', id: raceInstance.id, model: [raceInstance: raceInstance]
                    break
                } else {
                    redirect action: 'index', id: raceInstance.id
                    break
                }
        }
    }

    private List getRaceCategoriesByType(String raceType) {
        if(raceType == 'Run')
            return [RaceCategoryType.OneMile, RaceCategoryType.FiveKilometer, RaceCategoryType.EightKilometer, RaceCategoryType.TenKilometer, RaceCategoryType.TenMile, RaceCategoryType.HalfMarathon, RaceCategoryType.Marathon]

        if(raceType == 'Triathlon')
            return [RaceCategoryType.Sprint, RaceCategoryType.Olympic, RaceCategoryType.HalfIronman, RaceCategoryType.Ironman]

        return []
    }

    def createResult() {
        User user = requestedUser
        def userId = user.id
        List<Race> races
        switch(params?.raceType) {
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
        if(raceResults.count() > 0)
            return Race.findAll("from Race where raceType = '${raceType}' and statusType = '${StatusType.Approved}' and id not in (:excludedRaces)", ["excludedRaces": raceResults?.collect { it.race.id } ?: []])
        else
            return Race.findAll("from Race where raceType = '${raceType}' and statusType = '${StatusType.Approved}'")
    }

    def editRaceResult(){
        User user = requestedUser
        def raceResult = RaceResult.get(params?.int('raceResultId') ?: 0)

        render view: 'createResult', model: [race: raceResult.race, user: user, raceResult: raceResult]
    }

    def selectRace() {
        User user = requestedUser
        def race = Race.get(params.int('race.id'))
        def raceResult = new RaceResult(race: race, user: user)

        if(!race) {
            raceResult.errors.rejectValue(
                    'race',
                    'raceResult.races.none.approved')
            render view: 'createResult', model: [race: race, user: user, raceResult: raceResult]
            return
        }

        race.segments.sort {a, b -> a.segmentOrder <=> b.segmentOrder}.each {
            raceResult.addToSegmentResults(new SegmentResult(raceSegment: it, duration: new Duration(0)))
        }
        render view: 'createResult', model: [race: race, user: user, raceResult: raceResult]
    }

    def saveResult() {
        User user = requestedUser
        RaceResult raceResult = raceResultService.mapRaceResult(user, params)

        if(raceResult.hasErrors()) {
            render view: 'createResult', model: [race: raceResult.race, user: user, raceResult: raceResult]
            return
        }

        try {
            raceResultService.createRaceResult(raceResult)
            redirect action: 'index', model: [race: raceResult.race, user: user, raceResult: raceResult]
            return
        }
        catch(SegmentResultException failed) {
            flash.message = failed.message
            flash.segmentResult = failed.problem
            redirect action: 'createRunResult', model: [race: raceResult.race, user: user, raceResult: raceResult]
            return
        }
        catch(RaceResultException failed) {
            flash.message = failed.message
            flash.raceResult = failed.problem
            redirect action: 'createRunResult', model: [race: raceResult.race, user: user, raceResult: raceResult]
            return
        }
    }

    def modifyRaceResults(){
        if(params?.raceResultEdit){
            editRaceResult()
        } else {
            deleteRaceResult()
        }
    }

    def deleteRaceResult() {
        User user = requestedUser
        try {
            raceResultService.deleteRaceResult(params?.int('raceResultId') ?: 0, user);
            redirect action: 'index'
        } catch(RaceResultException failed) {
            flash.message = failed.message
            flash.raceResult = failed.problem
            redirect action: 'index'
        }
    }

    def races() {
        User user = requestedUser
        def userId = user.id
        def races = RaceResult.where {
            user.id == userId
        }

        render view: 'races', model: [races: races, user: user]
    }

    def racesBackbone() {
        render view: 'racesBackbone', model: [race: new Race()]
    }
}
