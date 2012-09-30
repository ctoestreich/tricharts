package com.tgid.tri.auth

import grails.plugin.springcache.annotations.Cacheable
import groovy.text.SimpleTemplateEngine
import uk.co.desirableobjects.sendgrid.SendGridEmail
import uk.co.desirableobjects.sendgrid.SendGridEmailBuilder
import grails.plugin.springcache.annotations.CacheFlush

class RegistrationController {

    def userService
    def grailsApplication
    def athlinksResultsParsingService
    def sendGridService
    def springSecurityService

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
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), ''])
                redirect action: 'newuser', id: userInstance.id
                break
        }
    }

    def newuser() {
        def userInstance = User.get(params?.id)
        def body = ''

        if(!userInstance) {
            redirect uri: "/"
            return
        }

//        def registrationCode = RegistrationCode.findByUsername(userInstance?.username)
//        if(!registrationCode) {
        def registrationCode = userService.createRegistrationCode(userInstance)
//        }

        if(registrationCode) {
            sendRegistrationEmail(userInstance, registrationCode)
        }

        render view: 'newuser', model: [userInstance: userInstance]
    }

    @CacheFlush(["chartCache", "triathlonRecordsCache", "runningRecordsCache"])
    def password() {
        def passwordCode = PasswordCode.findByToken(params?.t)
        def userInstance = User.findByUsername(passwordCode?.username)
        def racers = []
        switch(request.method) {
            case 'GET':
                if(!passwordCode) {
                    flash.message = message(code: 'confirmation.code.notfound')
                } else {
                    passwordCode.dateFulfilled = new Date()
                    passwordCode.save(flush: true)
                }
                break;
            case 'POST':
                if(params?.password != params?.password2) {
                    userInstance.errors.rejectValue('password', "user.password.not.match", "Passwords do not match")
                    render view: 'resetPassword', model: [userInstance: userInstance], params: [t: params.t]
                    return
                }

                userInstance.password = params.password

                if(!userInstance.validate()) {
                    render view: 'resetPassword', model: [userInstance: userInstance], params: [t: params.t]
                    return
                }

                userInstance.save(flush: true)

                springSecurityService.reauthenticate userInstance.username

                redirect controller: 'dashboard', action: 'index'
                return
        }

        render view: 'resetPassword', model: [userInstance: userInstance], params: [t: params.t]
    }

    def confirmation() {
        def registrationCode = RegistrationCode.findByToken(params?.t)
        def racers = [], user = null
        if(registrationCode) {
            registrationCode.save(dateFulfilled: new Date())
            user = User.findByUsername(registrationCode.username)
            user.enabled = true
            user.accountExpired = false
            user.accountLocked = false
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

    @CacheFlush(["chartCache", "triathlonRecordsCache", "runningRecordsCache"])
    def complete() {
        def user = User.get(params?.userID)
        def save = false
        params.list('racers')?.each { racerId ->
            def rid = convertRacerId(racerId)
            if(rid && !Racer.findByUserAndRacerID(user, rid)) {
                save = true
                user.addToRacers(new Racer(racerID: rid, lastImport: new Date() - 2))
            }
        }
        if(save) {user.save(flush: true)}

        runAsync {
            athlinksResultsParsingService.retrieveResults(user)
        }

        springSecurityService.reauthenticate user.username

        redirect controller: 'dashboard', action: 'index'
    }

    private long convertRacerId(racerId) {
        try {
            return Long.parseLong(racerId.toString())
        } catch(NumberFormatException e) {
            log.error e
            return 0
        }
    }

    def forgotPassword() {
        render view: 'forgotPassword', model: [userInstance: new User()]
    }

    @Cacheable("siteCache")
    def resend() {
        [userInstance: new User()]
    }

    def resendPasswordEmail() {
        def userInstance = User.findByUsername(params?.username)
        if(!userInstance) {
            render g.message(code: 'user.not.found', args: [params?.username, createLink(controller: 'registration', action: 'index')])
            return
        }

        def passwordCode = userService.createPasswordCode(userInstance)
        sendPasswordEmail(userInstance, passwordCode)

        render g.message(code: 'user.email.sent', args: [params?.username])
    }

    def resendRegistrationEmail() {
        def userInstance = User.findByUsername(params?.username)

        if(!userInstance) {
//            userInstance.errors.rejectValue('username', "user.not.found")
            render g.message(code: 'user.not.found', args: [params?.username, createLink(controller: 'registration', action: 'index')])
            return
        }

        def registrationCode = userService.createRegistrationCode(userInstance)

        if(registrationCode) {
            sendRegistrationEmail(userInstance, registrationCode)
        }

        render g.message(code: 'user.email.sent', args: [params?.username])
    }

    private String sendPasswordEmail(User user, PasswordCode passwordCode) {
        def url = generateLink('password', [t: passwordCode.token])
        def body = grailsApplication.config.security.ui.password.emailBody
        if(body.contains('$')) {
            body = evaluate(body, [user: user, url: url])
        }

        log.info body.toString()

        runAsync {

            SendGridEmail email = new SendGridEmailBuilder()
                    .from("christian@tricharts.com")
                    .to(passwordCode.username)
                    .subject("TriCharts.com Password Reset Link")
                    .withHtml(body.toString())
                    .build()

            sendGridService.send(email)

//            sendMail {
//                to registrationCode.username
//                from "acetrike@gmail.com"
//                subject "Registration Link"
//                html body.toString()
//            }
        }

        return body.toString()
    }

    private String sendRegistrationEmail(User user, RegistrationCode registrationCode) {

        def url = generateLink('confirmation', [t: registrationCode.token])
        def body = grailsApplication.config.security.ui.register.emailBody
        if(body.contains('$')) {
            body = evaluate(body, [user: user, url: url])
        }

        log.info body.toString()

        runAsync {

            SendGridEmail email = new SendGridEmailBuilder()
                    .from("christian@tricharts.com")
                    .to(registrationCode.username)
                    .subject("TriCharts.com Registration Link")
                    .withHtml(body.toString())
                    .build()

            sendGridService.send(email)

//            sendMail {
//                to registrationCode.username
//                from "acetrike@gmail.com"
//                subject "Registration Link"
//                html body.toString()
//            }
        }

        return body.toString()
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
