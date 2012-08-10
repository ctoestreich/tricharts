package com.tgid.tri.race

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class RaceController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [raceInstanceList: Race.list(params), raceInstanceTotal: Race.count()]
    }

    def create() {
        switch(request.method) {
            case 'GET':
                [raceInstance: new Race(params)]
                break
            case 'POST':
                def raceInstance = new Race(params)
                if(raceInstance.raceType == RaceType.Triathlon) {
                    createTriathlonSegments(raceInstance)
                } else if(raceInstance.raceType == RaceType.Running){
                    createRunSegments(raceInstance)
                } else if(raceInstance.raceType == RaceType.Biking){
                    createBikeSegments(raceInstance)
                }

                if(!raceInstance.save(flush: true)) {
                    render view: 'create', model: [raceInstance: raceInstance]
                    return
                }

                flash.message = message(code: 'default.created.message', args: [message(code: 'race.label', default: 'Race'), raceInstance.id])
                redirect action: 'show', id: raceInstance.id
                break
        }
    }

    private void createBikeSegments(Race race){
        def runSegment = Segment.findOrCreateWhere(segmentType: SegmentType.Bike, distanceType: race.distanceType, distance: race.distance)
        race.addToSegments(new RaceSegment(segment: runSegment))
    }

    private void createRunSegments(Race race){
        def runSegment = Segment.findOrCreateWhere(segmentType: SegmentType.Run, distanceType: race.distanceType, distance: race.distance)
        race.addToSegments(new RaceSegment(segment: runSegment))
    }

    private void createTriathlonSegments(Race race) {
        def swimSegment = Segment.findOrCreateWhere(segmentType: SegmentType.Swim, distanceType: DistanceType.Miles, distance: 0.5)
        def t1Segment = Segment.findOrCreateWhere(segmentType: SegmentType.T1, distanceType: DistanceType.Meters, distance: 400)
        def bikeSegment = Segment.findOrCreateWhere(segmentType: SegmentType.Bike, distanceType: DistanceType.Miles, distance: 15)
        def t2Segment = Segment.findOrCreateWhere(segmentType: SegmentType.T2, distanceType: DistanceType.Meters, distance: 400)
        def runSegment = Segment.findOrCreateWhere(segmentType: SegmentType.Run, distanceType: DistanceType.Kilometers, distance: 5)
        race.addToSegments(new RaceSegment(segment: swimSegment))
        race.addToSegments(new RaceSegment(segment: t1Segment))
        race.addToSegments(new RaceSegment(segment: bikeSegment))
        race.addToSegments(new RaceSegment(segment: t2Segment))
        race.addToSegments(new RaceSegment(segment: runSegment))
    }

    def show() {
        def raceInstance = Race.get(params.id)
        if(!raceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'race.label', default: 'Race'), params.id])
            redirect action: 'list'
            return
        }

        [raceInstance: raceInstance]
    }

    def edit() {
        switch(request.method) {
            case 'GET':
                def raceInstance = Race.get(params.id)
                if(!raceInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'race.label', default: 'Race'), params.id])
                    redirect action: 'list'
                    return
                }

                [raceInstance: raceInstance]
                break
            case 'POST':
                def raceInstance = Race.get(params.id)
                if(!raceInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'race.label', default: 'Race'), params.id])
                    redirect action: 'list'
                    return
                }

                if(params.version) {
                    def version = params.version.toLong()
                    if(raceInstance.version > version) {
                        raceInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                                                        [message(code: 'race.label', default: 'Race')] as Object[],
                                                        "Another user has updated this Race while you were editing")
                        render view: 'edit', model: [raceInstance: raceInstance]
                        return
                    }
                }

                raceInstance.properties = params

                if(!raceInstance.save(flush: true)) {
                    render view: 'edit', model: [raceInstance: raceInstance]
                    return
                }

                flash.message = message(code: 'default.updated.message', args: [message(code: 'race.label', default: 'Race'), raceInstance.id])
                redirect action: 'show', id: raceInstance.id
                break
        }
    }

    def delete() {
        def raceInstance = Race.get(params.id)
        if(!raceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'race.label', default: 'Race'), params.id])
            redirect action: 'list'
            return
        }

        try {
            raceInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'race.label', default: 'Race'), params.id])
            redirect action: 'list'
        }
        catch(DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'race.label', default: 'Race'), params.id])
            redirect action: 'show', id: params.id
        }
    }

    def backboneList() {
        render Race.list() as JSON
    }

    def backboneSave() {
        def race = new Race(request.JSON);
        render(race.save() as JSON)
    }

    def backboneDelete() {
        def race = Race.findById(params.id)
        race?.delete()
        render(race as JSON)
    }

    def backboneEdit() {
        def race = Race.findById(params.id)
        bindData(race, request.JSON)
        render(race.save() as JSON)
    }
}
