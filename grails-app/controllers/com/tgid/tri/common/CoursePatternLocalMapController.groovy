package com.tgid.tri.common

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class CoursePatternLocalMapController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [coursePatternLocalMapInstanceList: CoursePatternLocalMap.list(params), coursePatternLocalMapInstanceTotal: CoursePatternLocalMap.count()]
    }

    def create() {
        switch(request.method) {
            case 'GET':
                [coursePatternLocalMapInstance: new CoursePatternLocalMap(params)]
                break
            case 'POST':
                def coursePatternLocalMapInstance = new CoursePatternLocalMap(params)
                if(!coursePatternLocalMapInstance.save(flush: true)) {
                    render view: 'create', model: [coursePatternLocalMapInstance: coursePatternLocalMapInstance]
                    return
                }

                flash.message = message(code: 'default.created.message', args: [message(code: 'coursePatternLocalMap.label', default: 'CoursePatternLocalMap'), coursePatternLocalMapInstance.id])
                redirect action: 'show', id: coursePatternLocalMapInstance.id
                break
        }
    }

    def show() {
        def coursePatternLocalMapInstance = CoursePatternLocalMap.get(params.id)
        if(!coursePatternLocalMapInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'coursePatternLocalMap.label', default: 'CoursePatternLocalMap'), params.id])
            redirect action: 'list'
            return
        }

        [coursePatternLocalMapInstance: coursePatternLocalMapInstance]
    }

    def edit() {
        switch(request.method) {
            case 'GET':
                def coursePatternLocalMapInstance = CoursePatternLocalMap.get(params.id)
                if(!coursePatternLocalMapInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'coursePatternLocalMap.label', default: 'CoursePatternLocalMap'), params.id])
                    redirect action: 'list'
                    return
                }

                [coursePatternLocalMapInstance: coursePatternLocalMapInstance]
                break
            case 'POST':
                def coursePatternLocalMapInstance = CoursePatternLocalMap.get(params.id)
                if(!coursePatternLocalMapInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'coursePatternLocalMap.label', default: 'CoursePatternLocalMap'), params.id])
                    redirect action: 'list'
                    return
                }

                if(params.version) {
                    def version = params.version.toLong()
                    if(coursePatternLocalMapInstance.version > version) {
                        coursePatternLocalMapInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                                                                         [message(code: 'coursePatternLocalMap.label', default: 'CoursePatternLocalMap')] as Object[],
                                                                         "Another user has updated this CoursePatternLocalMap while you were editing")
                        render view: 'edit', model: [coursePatternLocalMapInstance: coursePatternLocalMapInstance]
                        return
                    }
                }

                coursePatternLocalMapInstance.properties = params

                if(!coursePatternLocalMapInstance.save(flush: true)) {
                    render view: 'edit', model: [coursePatternLocalMapInstance: coursePatternLocalMapInstance]
                    return
                }

                flash.message = message(code: 'default.updated.message', args: [message(code: 'coursePatternLocalMap.label', default: 'CoursePatternLocalMap'), coursePatternLocalMapInstance.id])
                redirect action: 'show', id: coursePatternLocalMapInstance.id
                break
        }
    }

    def delete() {
        def coursePatternLocalMapInstance = CoursePatternLocalMap.get(params.id)
        if(!coursePatternLocalMapInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'coursePatternLocalMap.label', default: 'CoursePatternLocalMap'), params.id])
            redirect action: 'list'
            return
        }

        try {
            coursePatternLocalMapInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'coursePatternLocalMap.label', default: 'CoursePatternLocalMap'), params.id])
            redirect action: 'list'
        }
        catch(DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'coursePatternLocalMap.label', default: 'CoursePatternLocalMap'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
