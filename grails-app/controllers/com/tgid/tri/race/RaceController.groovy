package com.tgid.tri.race

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
		switch (request.method) {
		case 'GET':
        	[raceInstance: new Race(params)]
			break
		case 'POST':
	        def raceInstance = new Race(params)
            if(raceInstance.raceType == RaceType.Triathlon){
                createTriathlonSegments(raceInstance)
            }

	        if (!raceInstance.save(flush: true)) {
	            render view: 'create', model: [raceInstance: raceInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'race.label', default: 'Race'), raceInstance.id])
	        redirect action: 'show', id: raceInstance.id
			break
		}
    }

    private void createTriathlonSegments(Race race) {
        def swim = new Segment(segmentType: SegmentType.Swim, distanceType: DistanceType.Miles, distance: 0.5)
        def t1 = new Segment(segmentType: SegmentType.T1, distanceType: DistanceType.Meters, distance: 400)
        def bike = new Segment(segmentType: SegmentType.Bike, distanceType: DistanceType.Miles, distance: 15)
        def t2 = new Segment(segmentType: SegmentType.T2, distanceType: DistanceType.Meters, distance: 400)
        def run = new Segment(segmentType: SegmentType.Run, distanceType: DistanceType.Kilometers, distance: 5)
        race.addToSegments(swim)
        race.addToSegments(t1)
        race.addToSegments(bike)
        race.addToSegments(t2)
        race.addToSegments(run)
    }

    def show() {
        def raceInstance = Race.get(params.id)
        if (!raceInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'race.label', default: 'Race'), params.id])
            redirect action: 'list'
            return
        }

        [raceInstance: raceInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def raceInstance = Race.get(params.id)
	        if (!raceInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'race.label', default: 'Race'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [raceInstance: raceInstance]
			break
		case 'POST':
	        def raceInstance = Race.get(params.id)
	        if (!raceInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'race.label', default: 'Race'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (raceInstance.version > version) {
	                raceInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'race.label', default: 'Race')] as Object[],
	                          "Another user has updated this Race while you were editing")
	                render view: 'edit', model: [raceInstance: raceInstance]
	                return
	            }
	        }

	        raceInstance.properties = params

	        if (!raceInstance.save(flush: true)) {
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
        if (!raceInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'race.label', default: 'Race'), params.id])
            redirect action: 'list'
            return
        }

        try {
            raceInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'race.label', default: 'Race'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'race.label', default: 'Race'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
