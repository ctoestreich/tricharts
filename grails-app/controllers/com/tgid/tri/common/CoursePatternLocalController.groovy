package com.tgid.tri.common

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class CoursePatternLocalController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [coursePatternLocalInstanceList: CoursePatternLocal.list(params), coursePatternLocalInstanceTotal: CoursePatternLocal.count()]
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[coursePatternLocalInstance: new CoursePatternLocal(params)]
			break
		case 'POST':
	        def coursePatternLocalInstance = new CoursePatternLocal(params)
	        if (!coursePatternLocalInstance.save(flush: true)) {
	            render view: 'create', model: [coursePatternLocalInstance: coursePatternLocalInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'coursePatternLocal.label', default: 'CoursePatternLocal'), coursePatternLocalInstance.id])
	        redirect action: 'show', id: coursePatternLocalInstance.id
			break
		}
    }

    def show() {
        def coursePatternLocalInstance = CoursePatternLocal.get(params.id)
        if (!coursePatternLocalInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'coursePatternLocal.label', default: 'CoursePatternLocal'), params.id])
            redirect action: 'list'
            return
        }

        [coursePatternLocalInstance: coursePatternLocalInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def coursePatternLocalInstance = CoursePatternLocal.get(params.id)
	        if (!coursePatternLocalInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'coursePatternLocal.label', default: 'CoursePatternLocal'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [coursePatternLocalInstance: coursePatternLocalInstance]
			break
		case 'POST':
	        def coursePatternLocalInstance = CoursePatternLocal.get(params.id)
	        if (!coursePatternLocalInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'coursePatternLocal.label', default: 'CoursePatternLocal'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (coursePatternLocalInstance.version > version) {
	                coursePatternLocalInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'coursePatternLocal.label', default: 'CoursePatternLocal')] as Object[],
	                          "Another user has updated this CoursePatternLocal while you were editing")
	                render view: 'edit', model: [coursePatternLocalInstance: coursePatternLocalInstance]
	                return
	            }
	        }

	        coursePatternLocalInstance.properties = params

	        if (!coursePatternLocalInstance.save(flush: true)) {
	            render view: 'edit', model: [coursePatternLocalInstance: coursePatternLocalInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'coursePatternLocal.label', default: 'CoursePatternLocal'), coursePatternLocalInstance.id])
	        redirect action: 'show', id: coursePatternLocalInstance.id
			break
		}
    }

    def delete() {
        def coursePatternLocalInstance = CoursePatternLocal.get(params.id)
        if (!coursePatternLocalInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'coursePatternLocal.label', default: 'CoursePatternLocal'), params.id])
            redirect action: 'list'
            return
        }

        try {
            coursePatternLocalInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'coursePatternLocal.label', default: 'CoursePatternLocal'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'coursePatternLocal.label', default: 'CoursePatternLocal'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
