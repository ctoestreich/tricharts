package com.tgid.tri.results

import org.springframework.dao.DataIntegrityViolationException

class SegmentResultController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def index() {
        redirect action: 'list', params: params
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [segmentResultInstanceList: SegmentResult.list(params), segmentResultInstanceTotal: SegmentResult.count()]
    }

    def create() {
		switch (request.method) {
		case 'GET':
        	[segmentResultInstance: new SegmentResult(params)]
			break
		case 'POST':
	        def segmentResultInstance = new SegmentResult(params)
	        if (!segmentResultInstance.save(flush: true)) {
	            render view: 'create', model: [segmentResultInstance: segmentResultInstance]
	            return
	        }

			flash.message = message(code: 'default.created.message', args: [message(code: 'segmentResult.label', default: 'SegmentResult'), segmentResultInstance.id])
	        redirect action: 'show', id: segmentResultInstance.id
			break
		}
    }

    def show() {
        def segmentResultInstance = SegmentResult.get(params.id)
        if (!segmentResultInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'segmentResult.label', default: 'SegmentResult'), params.id])
            redirect action: 'list'
            return
        }

        [segmentResultInstance: segmentResultInstance]
    }

    def edit() {
		switch (request.method) {
		case 'GET':
	        def segmentResultInstance = SegmentResult.get(params.id)
	        if (!segmentResultInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'segmentResult.label', default: 'SegmentResult'), params.id])
	            redirect action: 'list'
	            return
	        }

	        [segmentResultInstance: segmentResultInstance]
			break
		case 'POST':
	        def segmentResultInstance = SegmentResult.get(params.id)
	        if (!segmentResultInstance) {
	            flash.message = message(code: 'default.not.found.message', args: [message(code: 'segmentResult.label', default: 'SegmentResult'), params.id])
	            redirect action: 'list'
	            return
	        }

	        if (params.version) {
	            def version = params.version.toLong()
	            if (segmentResultInstance.version > version) {
	                segmentResultInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
	                          [message(code: 'segmentResult.label', default: 'SegmentResult')] as Object[],
	                          "Another user has updated this SegmentResult while you were editing")
	                render view: 'edit', model: [segmentResultInstance: segmentResultInstance]
	                return
	            }
	        }

	        segmentResultInstance.properties = params

	        if (!segmentResultInstance.save(flush: true)) {
	            render view: 'edit', model: [segmentResultInstance: segmentResultInstance]
	            return
	        }

			flash.message = message(code: 'default.updated.message', args: [message(code: 'segmentResult.label', default: 'SegmentResult'), segmentResultInstance.id])
	        redirect action: 'show', id: segmentResultInstance.id
			break
		}
    }

    def delete() {
        def segmentResultInstance = SegmentResult.get(params.id)
        if (!segmentResultInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'segmentResult.label', default: 'SegmentResult'), params.id])
            redirect action: 'list'
            return
        }

        try {
            segmentResultInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'segmentResult.label', default: 'SegmentResult'), params.id])
            redirect action: 'list'
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'segmentResult.label', default: 'SegmentResult'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
