package com.tgid.tri.race

import com.tgid.tri.queue.AthlinksRaceImportJesqueJob
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

@Secured(["ROLE_ADMIN"])
class RaceController {

    def raceService
    def jesqueService

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        def query = {
            if(params.name) {
                ilike('name', '%' + params.name + '%')
            }
            if(params.raceType) {
                eq('raceType', params.raceType as RaceType)
            }
            if(params.statusType) {
                eq('statusType', params.statusType as StatusType)
            }
            if(RaceCategoryType.getRaceCategoryType(params.raceCategoryType)) {
                eq('raceCategoryType', RaceCategoryType.getRaceCategoryType(params.raceCategoryType))
            }
            if(params.state) {
                state {
                    eq('abbrev', params.state)
                }
            }
            if(params.sort) {
                order(params.sort, params.order)
            }
        }

        def criteria = Race.createCriteria()
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        def races = criteria.list(query, max: params.max, offset: params.offset)
        def filters = [raceType: params.raceType, name: params.name, raceCategoryType: params.raceCategoryType, state: params.state]

        [raceInstanceList: races, raceInstanceTotal: races.totalCount, filters: filters]
    }

    def create() {
        switch(request.method) {
            case 'GET':
                [raceInstance: new Race(params)]
                break
            case 'POST':
                def raceInstance = new Race(params)
                if(raceInstance.raceType == RaceType.Triathlon) {
                    raceService.createTriathlonSegments(raceInstance)
                } else if(raceInstance.raceType == RaceType.Running) {
                    raceService.createRunSegments(raceInstance)
                } else if(raceInstance.raceType == RaceType.Biking) {
                    raceService.createBikeSegments(raceInstance)
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

    def reimportRace() {
        def race = Race.get(params?.id)

        println race

        if(race) {
            jesqueService.enqueue('athlinksGenericImport', AthlinksRaceImportJesqueJob.simpleName, race.athlinkRaceID)
            flash.message = message(code: 'import.started.message', args: ['Import Race'])
            redirect(action: 'show', id: race.id)
            return
        }

        redirect(action: 'list')
    }
}