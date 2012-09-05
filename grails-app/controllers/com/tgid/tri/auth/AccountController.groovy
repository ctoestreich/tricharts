package com.tgid.tri.auth

import grails.plugins.springsecurity.Secured

@Secured('ROLE_USER')
class AccountController {

    def springSecurityService

    def index() {
        def user = springSecurityService.currentUser
        render view: 'index', model: [user: user]
    }
}
