package com.tgid.tri

import com.tgid.tri.auth.User
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

class BaseController {

    def springSecurityService

    protected User getRequestedUser() {
        User user = ((User) springSecurityService.currentUser)
        if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN') && params.int('user.id')) {
            user = User.get(params.int('user.id'))
        }
        user
    }
}
