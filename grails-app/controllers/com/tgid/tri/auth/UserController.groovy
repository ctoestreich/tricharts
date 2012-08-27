package com.tgid.tri.auth

import grails.plugins.springsecurity.Secured

@Secured(["ROLE_ADMIN"])
class UserController {

    def scaffold = User
}
