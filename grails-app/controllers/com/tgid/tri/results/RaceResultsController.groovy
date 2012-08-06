package com.tgid.tri.results

import org.springframework.dao.DataIntegrityViolationException

class RaceResultsController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [raceResultsInstanceList: RaceResult.list(params), raceResultsInstanceTotal: RaceResults.count()]
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[raceResultsInstance: new RaceResults(params)]
			break
		case 'POST':
	        def raceResultsInstance = new RaceResult(params)
	        if (!raceResultsInstance.save(flush: true)) {
	            render view: 'create', model: [raceResultsInstance: raceResultsInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'raceResults.label', default: 'RaceResults'), raceResultsInstance.id])
	        redirect action: 'show', id: raceResultsInstance.id
			break
		}
    }

    def show() {
        def raceResultsInstance = RaceResult.get(params.id)
        if (!raceResultsInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'raceResults.label', default: 'RaceResults'), params.id])
            redirect action: 'list'
            return
        }

        [raceResultsInstance: raceResultsInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def raceResultsInstance = RaceResult.get(params.id)
	        if (!raceResultsInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'raceResults.label', default: 'RaceResults'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [raceResultsInstance: raceResultsInstance]
			break
		case 'POST':
	        def raceResultsInstance = RaceResult.get(params.id)
	        if (!raceResultsInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'raceResults.label', default: 'RaceResults'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (raceResultsInstance.version > version) {
	                raceResultsInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'raceResults.label', default: 'RaceResults')] as Object[],
	                          "Another user has updated this RaceResults while you were editing")
	                render view: 'edit', model: [raceResultsInstance: raceResultsInstance]
	                return
	            }
	        }

	        raceResultsInstance.properties = params

	        if (!raceResultsInstance.save(flush: true)) {
	            render view: 'edit', model: [raceResultsInstance: raceResultsInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'raceResults.label', default: 'RaceResults'), raceResultsInstance.id])
	        redirect action: 'show', id: raceResultsInstance.id
			break
		}
    }

    def delete() {
        def raceResultsInstance = RaceResults.get(params.id)
        if (!raceResultsInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'raceResults.label', default: 'RaceResults'), params.id])
            redirect action: 'list'
            return
        }

        try {
            raceResultsInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'raceResults.label', default: 'RaceResults'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'raceResults.label', default: 'RaceResults'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
