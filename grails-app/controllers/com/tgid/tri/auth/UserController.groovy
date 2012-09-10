package com.tgid.tri.auth

import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.springframework.dao.DataIntegrityViolationException

@Secured(["ROLE_ADMIN"])
class UserController {

    static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

    def search() {
        def query = {
            if(params.term) {
                or {
                    ilike('firstName', '%' + params.term + '%')
                    ilike('lastName', '%' + params.term + '%')
                }
            }
        }
        def response = []
        def criteria = User.createCriteria()
        def users = criteria.list(query)
        users.each {
            response << [label: it.toString(), value: it.toString(), userId: it.id]
        }
        render response as JSON
    }

    def index() {
        redirect action: 'list', params: params
    }

    def list() {

        def query = {
            if(params.firstName) {
                ilike('firstName', '%' + params.firstName + '%')
            }
            if(params.lastName) {
                ilike('lastName', '%' + params.lastName + '%')
            }
            if(params.username) {
                ilike('username', '%' + params.username + '%')
            }
            if(params.sort) {
                order(params.sort, params.order)
            }
        }

        def criteria = User.createCriteria()
        params.max = Math.min(params.max ? params.int('max') : 20, 100)
        def users = criteria.list(query, max: params.max, offset: params.offset)
        def filters = [firstName: params.firstName, lastName: params.lastName, username: params.username]

        [userInstanceList: users, userInstanceTotal: users.totalCount, filters: filters]
    }

    def create() {
        switch(request.method) {
            case 'GET':
                [userInstance: new User(params)]
                break
            case 'POST':
                def userInstance = new User(params)
                if(!userInstance.save(flush: true)) {
                    render view: 'create', model: [userInstance: userInstance]
                    return
                }

                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
                redirect action: 'show', id: userInstance.id
                break
        }
    }

    def show() {
        def userInstance = User.get(params.id)
        if(!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect action: 'list'
            return
        }

        [userInstance: userInstance]
    }

    def edit() {
        switch(request.method) {
            case 'GET':
                def userInstance = User.get(params.id)
                if(!userInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                    redirect action: 'list'
                    return
                }

                [userInstance: userInstance]
                break
            case 'POST':
                def userInstance = User.get(params.id)
                if(!userInstance) {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                    redirect action: 'list'
                    return
                }

                if(params.version) {
                    def version = params.version.toLong()
                    if(userInstance.version > version) {
                        userInstance.errors.rejectValue('version', 'default.optimistic.locking.failure',
                                                        [message(code: 'user.label', default: 'User')] as Object[],
                                                        "Another user has updated this User while you were editing")
                        render view: 'edit', model: [userInstance: userInstance]
                        return
                    }
                }

                userInstance.properties = params

                if(!userInstance.save(flush: true)) {
                    render view: 'edit', model: [userInstance: userInstance]
                    return
                }

                flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
                redirect action: 'show', id: userInstance.id
                break
        }
    }

    def delete() {
        def userInstance = User.get(params.id)
        if(!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect action: 'list'
            return
        }

        try {
            userInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect action: 'list'
        }
        catch(DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])
            redirect action: 'show', id: params.id
        }
    }
}
