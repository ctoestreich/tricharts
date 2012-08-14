package com.tgid.tri.race

import org.springframework.dao.DataIntegrityViolationException
import grails.plugins.springsecurity.Secured

@Secured(["ROLE_ADMIN"])
class SegmentController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [segmentInstanceList: Segment.list(params), segmentInstanceTotal: Segment.count()]
    }

    def create() {
        switch(request.method) {
            case 'GET':
                [segmentInstance: new Segment(params)]
                break
            case 'POST':
                def segmentInstance = new Segment(params)
                if(!segmentInstance.save(flush: true)) {
                    render view: 'create', model: [segmentInstance: segmentInstance]
                    return
                }

                flash.message = message(code: 'default.created.message', args: [message(code: 'segment.label', default: 'Segment'), segmentInstance.id])
                redirect action: 'show', id: segmentInstance.id
                break
        }
    }

    def show() {
        def segmentInstance = Segment.get(params.id)
        if(!segmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'segment.label', default: 'Segment'), params.id])
            redirect action: 'list'
            return
        }

        [segmentInstance: segmentInstance]
    }

    def edit() {
        switch(request.method) {
            case 'GET':
                def segmentInstance = Segment.get(params.id)
                if(!segmentInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'segment.label', default: 'Segment'), params.id])
                    redirect action: 'list'
                    return
                }

                [segmentInstance: segmentInstance]
                break
            case 'POST':
                def segmentInstance = Segment.get(params.id)
                if(!segmentInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'segment.label', default: 'Segment'), params.id])
                    redirect action: 'list'
                    return
                }

                if(params.version) {
                    def version = params.version.toLong()
                    if(segmentInstance.version > version) {
                        segmentInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                                                           [message(code: 'segment.label', default: 'Segment')] as Object[],
                                                           "Another user has updated this Segment while you were editing")
                        render view: 'edit', model: [segmentInstance: segmentInstance]
                        return
                    }
                }

                segmentInstance.properties = params

                if(!segmentInstance.save(flush: true)) {
                    render view: 'edit', model: [segmentInstance: segmentInstance]
                    return
                }

                flash.message = message(code: 'default.updated.message', args: [message(code: 'segment.label', default: 'Segment'), segmentInstance.id])
                redirect action: 'show', id: segmentInstance.id
                break
        }
    }

    def delete() {
        def segmentInstance = Segment.get(params.id)
        if(!segmentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'segment.label', default: 'Segment'), params.id])
            redirect action: 'list'
            return
        }

        try {
            segmentInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'segment.label', default: 'Segment'), params.id])
            redirect action: 'list'
        }
        catch(DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'segment.label', default: 'Segment'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
