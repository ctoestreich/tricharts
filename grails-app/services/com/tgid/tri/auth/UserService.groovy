package com.tgid.tri.auth

import groovy.json.JsonSlurper

class UserService {

    def grailsApplication

    def mapUserToAthlinkUsers(User user) {
        def searchUrl = "http://api.athlinks.com/athletes/search/${user.firstName}%20${user.lastName}/us_mn?key=${grailsApplication.config.athlinks.key}&format=json&LimitToMembers=0"
        def users = null

        try {
            String apiString = new URL(searchUrl).text
            users = new JsonSlurper().parseText(apiString)
        } catch(Exception e) {
            log.error e
        }

        users.each {
            Racer.findOrSaveWhere(user: user, racerID: it.RacerID)
        }
    }
}
