package com.tgid.tri.results

import org.springframework.dao.DataIntegrityViolationException

class RaceResultController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [raceResultInstanceList: RaceResult.list(params), raceResultInstanceTotal: RaceResult.count()]
    }

    def create() {
        switch (request.method) {
            case 'GET':
                [raceResultInstance: new RaceResult(params)]
                break
            case 'POST':
                def raceResultInstance = new RaceResult(params)
                if (!raceResultInstance.save(flush: true)) {
                    render view: 'create', model: [raceResultInstance: raceResultInstance]
                    return
                }

                flash.message = message(code: 'default.created.message', args: [message(code: 'raceResult.label', default: 'RaceResult'), raceResultInstance.id])
                redirect action: 'show', id: raceResultInstance.id
                break
        }
    }

    def show() {
        def raceResultInstance = RaceResult.get(params.id)
        if (!raceResultInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'raceResult.label', default: 'RaceResult'), params.id])
            redirect action: 'list'
            return
        }

        [raceResultInstance: raceResultInstance]
    }

    def edit() {
        switch (request.method) {
            case 'GET':
                def raceResultInstance = RaceResult.get(params.id)
                if (!raceResultInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'raceResult.label', default: 'RaceResult'), params.id])
                    redirect action: 'list'
                    return
                }

                [raceResultInstance: raceResultInstance]
                break
            case 'POST':
                def raceResultInstance = RaceResult.get(params.id)
                if (!raceResultInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'raceResult.label', default: 'RaceResult'), params.id])
                    redirect action: 'list'
                    return
                }

                if (params.version) {
                    def version = params.version.toLong()
                    if (raceResultInstance.version > version) {
                        raceResultInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                                [message(code: 'raceResult.label', default: 'RaceResult')] as Object[],
                                "Another user has updated this RaceResult while you were editing")
                        render view: 'edit', model: [raceResultInstance: raceResultInstance]
                        return
                    }
                }

                raceResultInstance.properties = params

                if (!raceResultInstance.save(flush: true)) {
                    render view: 'edit', model: [raceResultInstance: raceResultInstance]
                    return
                }

                flash.message = message(code: 'default.updated.message', args: [message(code: 'raceResult.label', default: 'RaceResult'), raceResultInstance.id])
                redirect action: 'show', id: raceResultInstance.id
                break
        }
    }

    def delete() {
        def raceResultInstance = RaceResult.get(params.id)
        if (!raceResultInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'raceResult.label', default: 'RaceResult'), params.id])
            redirect action: 'list'
            return
        }

        try {
            raceResultInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'raceResult.label', default: 'RaceResult'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'raceResult.label', default: 'RaceResult'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
