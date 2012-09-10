package com.tgid.tri.race

import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

@Secured(["ROLE_ADMIN"])
class RaceSegmentController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        def query = {
            if(params.name) {
                race {
                    ilike('name', '%' + params.name + '%')
                }
            }
            if(params.raceType) {
                race {
                    eq('raceType', params.raceType as RaceType)
                }
            }
            if(params.raceCategoryType) {
                race {
                    eq('raceCategoryType', params.raceCategoryType as RaceCategoryType)
                }
            }
            if(params.sort) {
                order(params.sort, params.order)
            }
        }

        def criteria = RaceSegment.createCriteria()
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        def raceSegments = criteria.list(query, max: params.max, offset: params.offset)
        def filters = [raceType: params.raceType, name: params.name, raceCategoryType: params.raceCategoryType]

        [raceSegmentInstanceList: raceSegments, raceSegmentInstanceTotal: raceSegments.totalCount, filters: filters]
    }

    def create() {
        switch(request.method) {
            case 'GET':
                [raceSegmentInstance: new RaceSegment(params)]
                break
            case 'POST':
                def raceSegmentInstance = new RaceSegment(params)
                if(!raceSegmentInstance.save(flush: true)) {
                    render view: 'create', model: [raceSegmentInstance: raceSegmentInstance]
                    return
                }

                flash.message = message(code: 'default.created.message', args: [message(code: 'raceSegment.label', default: 'RaceSegment'), raceSegmentInstance.id])
                redirect action: 'show', id: raceSegmentInstance.id
                break
        }
    }

    def show() {
        def raceSegmentInstance = RaceSegment.get(params.id)
        if(!raceSegmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'raceSegment.label', default: 'RaceSegment'), params.id])
            redirect action: 'list'
            return
        }

        [raceSegmentInstance: raceSegmentInstance]
    }

    def edit() {
        switch(request.method) {
            case 'GET':
                def raceSegmentInstance = RaceSegment.get(params.id)
                if(!raceSegmentInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'raceSegment.label', default: 'RaceSegment'), params.id])
                    redirect action: 'list'
                    return
                }

                [raceSegmentInstance: raceSegmentInstance]
                break
            case 'POST':
                def raceSegmentInstance = RaceSegment.get(params.id)
                if(!raceSegmentInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'raceSegment.label', default: 'RaceSegment'), params.id])
                    redirect action: 'list'
                    return
                }

                if(params.version) {
                    def version = params.version.toLong()
                    if(raceSegmentInstance.version > version) {
                        raceSegmentInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                                                               [message(code: 'raceSegment.label', default: 'RaceSegment')] as Object[],
                                                               "Another user has updated this RaceSegment while you were editing")
                        render view: 'edit', model: [raceSegmentInstance: raceSegmentInstance]
                        return
                    }
                }

                raceSegmentInstance.properties = params

                if(!raceSegmentInstance.save(flush: true)) {
                    render view: 'edit', model: [raceSegmentInstance: raceSegmentInstance]
                    return
                }

                flash.message = message(code: 'default.updated.message', args: [message(code: 'raceSegment.label', default: 'RaceSegment'), raceSegmentInstance.id])
                redirect action: 'show', id: raceSegmentInstance.id
                break
        }
    }

    def delete() {
        def raceSegmentInstance = RaceSegment.get(params.id)
        if(!raceSegmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'raceSegment.label', default: 'RaceSegment'), params.id])
            redirect action: 'list'
            return
        }

        try {
            raceSegmentInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'raceSegment.label', default: 'RaceSegment'), params.id])
            redirect action: 'list'
        }
        catch(DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'raceSegment.label', default: 'RaceSegment'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
