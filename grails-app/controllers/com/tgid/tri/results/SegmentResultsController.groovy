package com.tgid.tri.results

import org.springframework.dao.DataIntegrityViolationException

class SegmentResultsController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [segmentResultsInstanceList: SegmentResult.list(params), segmentResultsInstanceTotal: SegmentResult.count()]
    }

    def create() {
        switch(request.method) {
            case 'GET':
                [segmentResultsInstance: new SegmentResult(params)]
                break
            case 'POST':
                def segmentResultsInstance = new SegmentResult(params)
                if(!segmentResultsInstance.save(flush: true)) {
                    render view: 'create', model: [segmentResultsInstance: segmentResultsInstance]
                    return
                }

                flash.message = message(code: 'default.created.message', args: [message(code: 'segmentResults.label', default: 'SegmentResult'), segmentResultsInstance.id])
                redirect action: 'show', id: segmentResultsInstance.id
                break
        }
    }

    def show() {
        def segmentResultsInstance = SegmentResult.get(params.id)
        if(!segmentResultsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'segmentResults.label', default: 'SegmentResult'), params.id])
            redirect action: 'list'
            return
        }

        [segmentResultsInstance: segmentResultsInstance]
    }

    def edit() {
        switch(request.method) {
            case 'GET':
                def segmentResultsInstance = SegmentResult.get(params.id)
                if(!segmentResultsInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'segmentResults.label', default: 'SegmentResult'), params.id])
                    redirect action: 'list'
                    return
                }

                [segmentResultsInstance: segmentResultsInstance]
                break
            case 'POST':
                def segmentResultsInstance = SegmentResult.get(params.id)
                if(!segmentResultsInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'segmentResults.label', default: 'SegmentResult'), params.id])
                    redirect action: 'list'
                    return
                }

                if(params.version) {
                    def version = params.version.toLong()
                    if(segmentResultsInstance.version > version) {
                        segmentResultsInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                                                                  [message(code: 'segmentResults.label', default: 'SegmentResult')] as Object[],
                                                                  "Another user has updated this SegmentResult while you were editing")
                        render view: 'edit', model: [segmentResultsInstance: segmentResultsInstance]
                        return
                    }
                }

                segmentResultsInstance.properties = params

                if(!segmentResultsInstance.save(flush: true)) {
                    render view: 'edit', model: [segmentResultsInstance: segmentResultsInstance]
                    return
                }

                flash.message = message(code: 'default.updated.message', args: [message(code: 'segmentResults.label', default: 'SegmentResult'), segmentResultsInstance.id])
                redirect action: 'show', id: segmentResultsInstance.id
                break
        }
    }

    def delete() {
        def segmentResultsInstance = SegmentResult.get(params.id)
        if(!segmentResultsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'segmentResults.label', default: 'SegmentResult'), params.id])
            redirect action: 'list'
            return
        }

        try {
            segmentResultsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'segmentResults.label', default: 'SegmentResult'), params.id])
            redirect action: 'list'
        }
        catch(DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'segmentResults.label', default: 'SegmentResult'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
