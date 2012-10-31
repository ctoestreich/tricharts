package com.tgid.tri.auth

import com.tgid.tri.race.RaceType
import grails.plugins.springsecurity.Secured

@Secured('ROLE_USER')
class AccountController {

    def springSecurityService

    def index() {
        User user = springSecurityService.currentUser
        switch(request.method) {
            case 'GET':
                render view: 'index', model: [user: user]
                break
            case 'POST':
                if(!user.validate(params)) {
                    render view: 'index', model: [user: user]
                    break
                }

                flash.message = message(code: 'default.updated.message', args: ['User', 'properties'])
                user.properties = params
                user.save(flush: true)
                render view: 'index', model: [user: user]
        }
    }

    def password(PasswordChangeCommand passwordChangeCommand) {
        User user = springSecurityService.currentUser
        switch(request.method) {
            case 'GET':
                passwordChangeCommand.errors = null
                render view: 'password', model: [user: user, passwordChangeCommand: passwordChangeCommand]
                break
            case 'POST':
                if(!passwordChangeCommand.validate()) {
                    render view: 'password', model: [user: user, passwordChangeCommand: passwordChangeCommand]
                    break
                }

                user.password = passwordChangeCommand.password
                user.save(flush: true)
                flash.message = message(code: 'user.password.changed')

                passwordChangeCommand.clear()
                redirect action: 'password', model: [user: user, passwordChangeCommand: passwordChangeCommand]
                break
        }

    }

    def external() {
        User user = springSecurityService.currentUser
        render view: 'external', model: [user: user]
    }

    def sports() {
        User user = springSecurityService.currentUser
        UserSport userSport = UserSport.findOrSaveWhere(user: user)
        switch(request.method) {
            case 'GET':
                break
            case 'POST':
                RaceType.each { RaceType raceType ->
                    def radio = params?.get(raceType.raceType, '0')
                    if(radio != '0') {
                        userSport.sports << raceType
                    } else {
                        userSport.sports.removeAll {it.raceType == raceType.raceType}
                    }
                    userSport.save(flush: true)
                }
                break
        }

        render view: 'sports', model: [userSport: userSport]
    }
}
