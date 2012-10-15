package com.tgid.tri.ui

import com.tgid.tri.BaseController
import com.tgid.tri.auth.User
import com.tgid.tri.exception.RaceResultException
import com.tgid.tri.exception.SegmentResultException
import com.tgid.tri.results.RaceResult
import grails.plugins.springsecurity.Secured
import com.tgid.tri.race.*

@Secured(["ROLE_USER"])
class ResultsController extends BaseController {

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

    def runCharts() {
        User user = requestedUser

        render view: 'runCharts', model: [user: user]
    }

    def addSegments() {
        User user = requestedUser
        switch(request.method) {
            case 'POST':
                def raceInstance = Race.get(params?.raceId)
                def segments = params.list("segments")

                segments?.each {
                    raceInstance.addToSegments(new RaceSegment(segment: Segment.get(it.toString())))
                }

                if(!raceInstance.save(flush: true)) {
                    render view: 'addSegments', model: [raceInstance: raceInstance]
                    return
                }

                flash.message = message(code: 'race.created.approved.message', args: [message(code: 'race.label', default: 'Race'), raceInstance.name])
                def raceResult = raceResultService.mapRaceResult(raceInstance, new RaceResult(race: raceInstance, user: user))
                render view: 'createResult', model: [race: raceInstance, user: user, raceResult: raceResult]
                break
        }
    }

    def addRace() {
        User user = requestedUser
        if(params?.raceType) {
            params["raceType"] = RaceType.getRaceType(params.raceType)
        }
        switch(request.method) {
            case 'GET':
                [raceInstance: new Race(params), user: user]
                break
            case 'POST':
                def raceInstance = new Race(params)

                if(raceInstance.raceType == RaceType.Running) {
                    raceService.createRunSegments(raceInstance)
                } else if(raceInstance.raceType == RaceType.Biking) {
                    raceService.createBikeSegments(raceInstance)
                }

                if(!raceInstance.validate()) {
                    render view: 'addRace', model: [raceInstance: raceInstance, user: user]
                    return
                } else {
                    raceInstance.save(flush: true)
                }

                if(raceInstance.raceType == RaceType.Triathlon) {
                    render view: 'addSegments', id: raceInstance.id, model: [raceInstance: raceInstance, user: user]
                    break
                } else {
                    flash.message = message(code: 'race.created.approved.message', args: [message(code: 'race.label', default: 'Race'), raceInstance.name])
                    def raceResult = raceResultService.mapRaceResult(raceInstance, new RaceResult(race: raceInstance, user: user))
                    render view: 'createResult', model: [race: raceInstance, user: user, raceResult: raceResult]
                    break
                }
        }
    }

    def createResult() {
        User user = requestedUser
        def userId = user.id
        List<Race> races
        switch(params?.raceType) {
            case 'Running':
                races = findRacesWithNoResults(userId, RaceType.Running)
                break
            case 'Triathlon':
                races = findRacesWithNoResults(userId, RaceType.Triathlon)
                break
            default:
                races = []
        }
        render view: 'createResult', model: [raceResult: new RaceResult(), user: user, races: races, 'user.id': user.id, raceName: params?.raceName], params: ['race.id': params?.int('race.id')]
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

    def editRaceResult() {
        User user = requestedUser
        def raceResult = RaceResult.get(params?.int('raceResultId') ?: 0)

        render view: 'createResult', model: [race: raceResult.race, user: user as User, raceResult: raceResult, 'user.id': user.id]
    }

    def selectRace() {
        User user = requestedUser
        def race = Race.get(params.int('race.id'))
        def raceResult = new RaceResult(race: race, user: user)

        if(!race) {
            def raceType = params?.raceType
            params.clear()
            params.setProperty('user.id', user.id)
            params.setProperty('raceType', raceType)
            if(!params.int('race.id')) {
                flash.message = g.message(code: 'raceResult.race.null')
            }
            raceResult.errors.rejectValue('race', 'raceResult.races.none.approved')
            redirect action: 'createResult', params: params, model: [race: null, user: user, raceResult: raceResult]
            return
        }

        raceResultService.mapRaceResult(race, raceResult)

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
            params.clear()
            params.setProperty('user.id', user.id)
            raceResultService.createRaceResult(raceResult)
            redirect action: 'index', params: params
        }
        catch(SegmentResultException failed) {
            flash.message = failed.message
            flash.segmentResult = failed.problem
            redirect action: 'createRunResult', model: [race: raceResult.race, user: user, raceResult: raceResult]
        }
        catch(RaceResultException failed) {
            flash.message = failed.message
            flash.raceResult = failed.problem
            redirect action: 'createRunResult', model: [race: raceResult.race, user: user, raceResult: raceResult]
        }
    }

    def modifyRaceResults() {
        if(params?.raceResultEdit) {
            editRaceResult()
        } else {
            deleteRaceResult()
        }
    }

    def deleteRaceResult() {
        User user = requestedUser
        try {
            if(user && params?.int('raceResultDeleteId')) {
                raceResultService.deleteRaceResult(params.int('raceResultDeleteId'), user);
            }
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
