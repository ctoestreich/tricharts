package com.tgid.tri.auth

import grails.plugins.springsecurity.Secured

@Secured(["ROLE_ADMIN"])
class UserRoleController {

    def scaffold = UserRole
}
