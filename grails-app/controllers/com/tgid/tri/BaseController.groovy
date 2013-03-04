package com.tgid.tri

import com.tgid.tri.auth.User
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import grails.converters.JSON
import grails.converters.XML

class BaseController {

    def springSecurityService

    protected User getRequestedUser() {
        User user = ((User) springSecurityService.currentUser)
        if (SpringSecurityUtils.ifAllGranted('ROLE_ADMIN') && params.int('user.id')) {
            user = User.get(params.int('user.id'))
        }
        user
    }

    protected User getAnyUser() {
        User user = ((User) springSecurityService.currentUser)
        def userId = params?.int('id') ?: params.int('user.id')
        if (userId) {
            user = User.get(userId)
        }
        user
    }

    protected def formatResponse(resp) {
        withFormat {
            json {
                if(params?.callback)
                    render(contentType: "application/json", text: (params?.callback ?: 'data') + "(" + (resp as JSON) + ")")
                else
                    render resp as JSON
            }
            xml {
                render resp as XML
            }
            html {
                render(contentType: "application/json", text: (params?.callback ?: 'data') + "(" + (resp as JSON) + ")")
            }
        }
    }
}
