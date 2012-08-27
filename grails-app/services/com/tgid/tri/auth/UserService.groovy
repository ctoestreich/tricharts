package com.tgid.tri.auth

import groovy.json.JsonSlurper

class UserService {

    def grailsApplication

    List<Map<String, String>> lookupAthlinkRacers(User user) {
        def racers = [], users = null
        def searchUrl = "http://api.athlinks.com/athletes/search/${user?.firstName}%20${user?.lastName}?key=${grailsApplication.config.athlinks.key}&format=json&LimitToMembers=0&state=${user?.states?.collect { it?.provID?.toLowerCase()}?.join(",")}"
        log.info searchUrl

        if(user) {
            try {
                String apiString = new URL(searchUrl).text
                users = new JsonSlurper().parseText(apiString)
            } catch(Exception e) {
                log.error e
            }
        }

        users.each {
            println it
            racers << [user: user, racerID: it.RacerID as Long, age: it?.Age, city: it?.City, name: it?.DisplayName]
        }
        return racers
    }

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
        def registrationCode = RegistrationCode.findOrCreateWhere(username: user.username)
        if(registrationCode.validate()) {
            registrationCode.save(flush: true)
            return registrationCode
        }

        return null
    }
}
