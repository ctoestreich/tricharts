package com.tgid.tri.data

import org.springframework.dao.DataIntegrityViolationException

class ImportLogController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [importLogInstanceList: ImportLog.list(params), importLogInstanceTotal: ImportLog.count()]
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[importLogInstance: new ImportLog(params)]
			break
		case 'POST':
	        def importLogInstance = new ImportLog(params)
	        if (!importLogInstance.save(flush: true)) {
	            render view: 'create', model: [importLogInstance: importLogInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'importLog.label', default: 'ImportLog'), importLogInstance.id])
	        redirect action: 'show', id: importLogInstance.id
			break
		}
    }

    def show() {
        def importLogInstance = ImportLog.get(params.id)
        if (!importLogInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'importLog.label', default: 'ImportLog'), params.id])
            redirect action: 'list'
            return
        }

        [importLogInstance: importLogInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def importLogInstance = ImportLog.get(params.id)
	        if (!importLogInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'importLog.label', default: 'ImportLog'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [importLogInstance: importLogInstance]
			break
		case 'POST':
	        def importLogInstance = ImportLog.get(params.id)
	        if (!importLogInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'importLog.label', default: 'ImportLog'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (importLogInstance.version > version) {
	                importLogInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'importLog.label', default: 'ImportLog')] as Object[],
	                          "Another user has updated this ImportLog while you were editing")
	                render view: 'edit', model: [importLogInstance: importLogInstance]
	                return
	            }
	        }

	        importLogInstance.properties = params

	        if (!importLogInstance.save(flush: true)) {
	            render view: 'edit', model: [importLogInstance: importLogInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'importLog.label', default: 'ImportLog'), importLogInstance.id])
	        redirect action: 'show', id: importLogInstance.id
			break
		}
    }

    def delete() {
        def importLogInstance = ImportLog.get(params.id)
        if (!importLogInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'importLog.label', default: 'ImportLog'), params.id])
            redirect action: 'list'
            return
        }

        try {
            importLogInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'importLog.label', default: 'ImportLog'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'importLog.label', default: 'ImportLog'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
