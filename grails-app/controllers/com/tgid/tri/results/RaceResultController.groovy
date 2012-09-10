package com.tgid.tri.results

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured
import com.tgid.tri.auth.User

@Secured(["ROLE_ADMIN"])
class RaceResultController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        def query = {
            if(params.firstName) {
                user {
                ilike('firstName', '%' + params.firstName + '%')
                }
            }
            if(params.lastName) {
                user {
                ilike('lastName', '%' + params.lastName + '%')
                }
            }
            if(params.username) {
                race {
                ilike('name', '%' + params.name + '%')
                }
            }
            if(params.sort) {
                order(params.sort, params.order)
            }
        }

        def criteria = RaceResult.createCriteria()
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        def raceResults = criteria.list(query, max: params.max, offset: params.offset)
        def filters = [firstName: params.firstName, lastName: params.lastName, name: params.name]

        [raceResultInstanceList: raceResults, raceResultInstanceTotal: raceResults.totalCount, filters: filters]
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
