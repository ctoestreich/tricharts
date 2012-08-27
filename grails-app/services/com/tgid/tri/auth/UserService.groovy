package com.tgid.tri.auth

import groovy.json.JsonSlurper

class UserService {

    def grailsApplication

    void mapUserToAthlinkUsers(User user) {
        def searchUrl = "http://api.athlinks.com/athletes/search/${user.firstName}%20${user.lastName}?key=${grailsApplication.config.athlinks.key}&format=json&LimitToMembers=0&state=${user.states.join(",")}"
        def users = null

        try {
            String apiString = new URL(searchUrl).text
            users = new JsonSlurper().parseText(apiString)
        } catch(Exception e) {
            log.error e
        }

        users.each {
            Racer.findOrSaveWhere(user: user, racerID: it.RacerID as Long)
        }
    }

    RegistrationCode createRegistrationCode(User user) {
        def registrationCode = new RegistrationCode(username: user.username, dateCreated: new Date())
        if(registrationCode.validate()) {
            registrationCode.save(flush: true)
            return registrationCode
        }

        return null
    }
}
