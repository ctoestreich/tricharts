package com.tgid.tri.auth

import groovy.text.SimpleTemplateEngine

class RegistrationController {

    def userService
    def grailsApplication
    def athlinksResultsParsingService

    def index() {
        def userInstance = new User(params)
        userInstance.enabled = false
        userInstance.accountLocked = true
        switch(request.method) {
            case 'GET':
                render view: 'index', model: [userInstance: userInstance]
                break
            case 'POST':
                if(params?.password != params?.password2) {
                    userInstance.errors.rejectValue('password', "user.password.not.match", "Passwords do not match")
                    render view: 'index', model: [userInstance: userInstance, password2: params?.password2]
                    return
                }

                if(!userInstance.validate()) {
                    render view: 'index', model: [userInstance: userInstance, password2: params?.password2]
                    return
                }

                userInstance.save(flush: true)
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.username])
                redirect action: 'newuser', id: userInstance.id
                break
        }
    }

    def newuser() {
        def userInstance = User.get(params?.id)

        if(!RegistrationCode.findByUsername(userInstance?.username)) {
            def registrationCode = userService.createRegistrationCode(userInstance)
            createRegistrationEmail(userInstance, registrationCode)
        }

        render view: 'newuser', model: [userInstance: userInstance]
    }

    def confirmation() {
        def registrationCode = RegistrationCode.findByToken(params?.t)
        def racers = [], user = null
        if(registrationCode) {
            user = User.findByUsername(registrationCode.username)
            user.enabled = true
            user.accountExpired = false
            user.save(flush: true)
            def userRole = Role.findByAuthority('ROLE_USER')
            if(userRole && !user?.authorities?.contains(userRole)) {
                new UserRole(role: userRole, user: user).save(flush: true)
                if(user.username == 'acetrike@yahoo.com') {
                    new UserRole(role: Role.findByAuthority('ROLE_ADMIN'), user: user).save(flush: true)
                }
            }

            racers = userService.lookupAthlinkRacers(user)

        } else {
            flash.message = message(code: 'confirmation.code.notfound')
        }

        render view: 'confirmation', model: [user: user, racers: racers]
    }

    def complete() {
        def user = User.get(params?.userID)
        def save = false
        params['racers'].each {
            if(!Racer.findByUserAndRacerID(user, it)) {
                save = true
                user.addToRacers(new Racer(racerID: it, lastImport: new Date() - 2))
            }
        }
        if(save) {user.save(flush: true)}

        athlinksResultsParsingService.retrieveResults(user)

        redirect controller: 'dashboard', action: 'index'
    }

    private String createRegistrationEmail(User user, RegistrationCode registrationCode) {

        def url = generateLink('confirmation', [t: registrationCode.token])
        def body = grailsApplication.config.security.ui.register.emailBody
        if(body.contains('$')) {
            body = evaluate(body, [user: user, url: url])
        }

        println body.toString()

        sendMail {
            to registrationCode.username
            from "acetrike@gmail.com"
            subject "Registration Link"
            html body.toString()
        }
    }



    private String generateLink(String action, linkParams) {
        createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                   controller: 'registration', action: action,
                   params: linkParams)
    }

    private String evaluate(s, binding) {
        new SimpleTemplateEngine().createTemplate(s).make(binding)
    }
}
