package com.tgid.tri.auth

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured

@Secured('ROLE_ADMIN')
class CountryController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [countryInstanceList: Country.list(params), countryInstanceTotal: Country.count()]
    }

    def create() {
        switch(request.method) {
            case 'GET':
                [countryInstance: new Country(params)]
                break
            case 'POST':
                def countryInstance = new Country(params)
                if(!countryInstance.save(flush: true)) {
                    render view: 'create', model: [countryInstance: countryInstance]
                    return
                }

                flash.message = message(code: 'default.created.message', args: [message(code: 'country.label', default: 'Country'), countryInstance.id])
                redirect action: 'show', id: countryInstance.id
                break
        }
    }

    def show() {
        def countryInstance = Country.get(params.id)
        if(!countryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'country.label', default: 'Country'), params.id])
            redirect action: 'list'
            return
        }

        [countryInstance: countryInstance]
    }

    def edit() {
        switch(request.method) {
            case 'GET':
                def countryInstance = Country.get(params.id)
                if(!countryInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'country.label', default: 'Country'), params.id])
                    redirect action: 'list'
                    return
                }

                [countryInstance: countryInstance]
                break
            case 'POST':
                def countryInstance = Country.get(params.id)
                if(!countryInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'country.label', default: 'Country'), params.id])
                    redirect action: 'list'
                    return
                }

                if(params.version) {
                    def version = params.version.toLong()
                    if(countryInstance.version > version) {
                        countryInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                                                           [message(code: 'country.label', default: 'Country')] as Object[],
                                                           "Another user has updated this Country while you were editing")
                        render view: 'edit', model: [countryInstance: countryInstance]
                        return
                    }
                }

                countryInstance.properties = params

                if(!countryInstance.save(flush: true)) {
                    render view: 'edit', model: [countryInstance: countryInstance]
                    return
                }

                flash.message = message(code: 'default.updated.message', args: [message(code: 'country.label', default: 'Country'), countryInstance.id])
                redirect action: 'show', id: countryInstance.id
                break
        }
    }

    def delete() {
        def countryInstance = Country.get(params.id)
        if(!countryInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'country.label', default: 'Country'), params.id])
            redirect action: 'list'
            return
        }

        try {
            countryInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'country.label', default: 'Country'), params.id])
            redirect action: 'list'
        }
        catch(DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'country.label', default: 'Country'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
