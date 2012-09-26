package com.tgid.tri.auth

import org.springframework.dao.DataIntegrityViolationException

class RegistrationCodeController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [registrationCodeInstanceList: RegistrationCode.list(params), registrationCodeInstanceTotal: RegistrationCode.count()]
    }

    def create() {
        switch(request.method) {
            case 'GET':
                [registrationCodeInstance: new RegistrationCode(params)]
                break
            case 'POST':
                def registrationCodeInstance = new RegistrationCode(params)
                if(!registrationCodeInstance.save(flush: true)) {
                    render view: 'create', model: [registrationCodeInstance: registrationCodeInstance]
                    return
                }

                flash.message = message(code: 'default.created.message', args: [message(code: 'registrationCode.label', default: 'RegistrationCode'), registrationCodeInstance.id])
                redirect action: 'show', id: registrationCodeInstance.id
                break
        }
    }

    def show() {
        def registrationCodeInstance = RegistrationCode.get(params.id)
        if(!registrationCodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'registrationCode.label', default: 'RegistrationCode'), params.id])
            redirect action: 'list'
            return
        }

        [registrationCodeInstance: registrationCodeInstance]
    }

    def edit() {
        switch(request.method) {
            case 'GET':
                def registrationCodeInstance = RegistrationCode.get(params.id)
                if(!registrationCodeInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'registrationCode.label', default: 'RegistrationCode'), params.id])
                    redirect action: 'list'
                    return
                }

                [registrationCodeInstance: registrationCodeInstance]
                break
            case 'POST':
                def registrationCodeInstance = RegistrationCode.get(params.id)
                if(!registrationCodeInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'registrationCode.label', default: 'RegistrationCode'), params.id])
                    redirect action: 'list'
                    return
                }

                if(params.version) {
                    def version = params.version.toLong()
                    if(registrationCodeInstance.version > version) {
                        registrationCodeInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                                                                    [message(code: 'registrationCode.label', default: 'RegistrationCode')] as Object[],
                                                                    "Another user has updated this RegistrationCode while you were editing")
                        render view: 'edit', model: [registrationCodeInstance: registrationCodeInstance]
                        return
                    }
                }

                registrationCodeInstance.properties = params

                if(!registrationCodeInstance.save(flush: true)) {
                    render view: 'edit', model: [registrationCodeInstance: registrationCodeInstance]
                    return
                }

                flash.message = message(code: 'default.updated.message', args: [message(code: 'registrationCode.label', default: 'RegistrationCode'), registrationCodeInstance.id])
                redirect action: 'show', id: registrationCodeInstance.id
                break
        }
    }

    def delete() {
        def registrationCodeInstance = RegistrationCode.get(params.id)
        if(!registrationCodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'registrationCode.label', default: 'RegistrationCode'), params.id])
            redirect action: 'list'
            return
        }

        try {
            registrationCodeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'registrationCode.label', default: 'RegistrationCode'), params.id])
            redirect action: 'list'
        }
        catch(DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'registrationCode.label', default: 'RegistrationCode'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
