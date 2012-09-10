package com.tgid.tri.results

import com.tgid.tri.race.RaceCategoryType
import com.tgid.tri.race.RaceType
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

@Secured(["ROLE_ADMIN"])
class SegmentResultController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        def query = {
            if(params.firstName) {
                raceResult {
                    user {
                        ilike('firstName', '%' + params.firstName + '%')
                    }
                }
            }
            if(params.lastName) {
                raceResult {
                    user {
                        ilike('lastName', '%' + params.lastName + '%')
                    }
                }
            }
            if(params.name) {
                raceResult {
                    race {
                        ilike('name', '%' + params.name + '%')
                    }
                }
            }
            if(params.raceType) {
                raceResult {
                    race {
                        eq('raceType', params.raceType as RaceType)
                    }
                }
            }
            if(params.raceCategoryType) {
                raceResult {
                    race {
                        eq('raceCategoryType', params.raceCategoryType as RaceCategoryType)
                    }
                }
            }
        }

        def criteria = SegmentResult.createCriteria()
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        def segmentResults = criteria.list(query, max: params.max, offset: params.offset)
        def filters = [firstName: params.firstName,
                lastName: params.lastName,
                raceType: params.raceType,
                name: params.name,
                raceCategoryType: params.raceCategoryType]

        [segmentResultInstanceList: segmentResults, segmentResultInstanceTotal: segmentResults.totalCount, filters: filters]
    }

    def create() {
        switch(request.method) {
            case 'GET':
                [segmentResultInstance: new SegmentResult(params)]
                break
            case 'POST':
                def segmentResultInstance = new SegmentResult(params)
                if(!segmentResultInstance.save(flush: true)) {
                    render view: 'create', model: [segmentResultInstance: segmentResultInstance]
                    return
                }

                flash.message = message(code: 'default.created.message', args: [message(code: 'segmentResult.label', default: 'SegmentResult'), segmentResultInstance.id])
                redirect action: 'show', id: segmentResultInstance.id
                break
        }
    }

    def show() {
        def segmentResultInstance = SegmentResult.get(params.id)
        if(!segmentResultInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'segmentResult.label', default: 'SegmentResult'), params.id])
            redirect action: 'list'
            return
        }

        [segmentResultInstance: segmentResultInstance]
    }

    def edit() {
        switch(request.method) {
            case 'GET':
                def segmentResultInstance = SegmentResult.get(params.id)
                if(!segmentResultInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'segmentResult.label', default: 'SegmentResult'), params.id])
                    redirect action: 'list'
                    return
                }

                [segmentResultInstance: segmentResultInstance]
                break
            case 'POST':
                def segmentResultInstance = SegmentResult.get(params.id)
                if(!segmentResultInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'segmentResult.label', default: 'SegmentResult'), params.id])
                    redirect action: 'list'
                    return
                }

                if(params.version) {
                    def version = params.version.toLong()
                    if(segmentResultInstance.version > version) {
                        segmentResultInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                                                                 [message(code: 'segmentResult.label', default: 'SegmentResult')] as Object[],
                                                                 "Another user has updated this SegmentResult while you were editing")
                        render view: 'edit', model: [segmentResultInstance: segmentResultInstance]
                        return
                    }
                }

                segmentResultInstance.properties = params

                if(!segmentResultInstance.save(flush: true)) {
                    render view: 'edit', model: [segmentResultInstance: segmentResultInstance]
                    return
                }

                flash.message = message(code: 'default.updated.message', args: [message(code: 'segmentResult.label', default: 'SegmentResult'), segmentResultInstance.id])
                redirect action: 'show', id: segmentResultInstance.id
                break
        }
    }

    def delete() {
        def segmentResultInstance = SegmentResult.get(params.id)
        if(!segmentResultInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'segmentResult.label', default: 'SegmentResult'), params.id])
            redirect action: 'list'
            return
        }

        try {
            segmentResultInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'segmentResult.label', default: 'SegmentResult'), params.id])
            redirect action: 'list'
        }
        catch(DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'segmentResult.label', default: 'SegmentResult'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
