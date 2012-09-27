package com.tgid.tri.common

import com.tgid.tri.data.ImportLog
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

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
                render view: 'create', model: [coursePatternLocalMapInstance: new CoursePatternLocalMap(params)], params: [importLogId: params?.importLogId]
                break
            case 'POST':
                def coursePatternLocalMapInstance = new CoursePatternLocalMap(params)
                if(!CoursePatternLocalMap.findByMapKey(params?.mapKey)) {
                    if(!coursePatternLocalMapInstance.save(flush: true)) {
                        render view: 'create', model: [coursePatternLocalMapInstance: coursePatternLocalMapInstance], params: [importLogId: params?.importLogId]
                        return
                    }
                }

                if(params?.importLogId) {
                    def importLog = ImportLog.get(params.long('importLogId'))
                    if(importLog) {
                        importLog.delete();
                    }
                    redirect controller: 'importLog', action: 'list', params: [error: true]
                    break
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
