package com.tgid.tri.auth

import groovy.text.SimpleTemplateEngine
import uk.co.desirableobjects.sendgrid.SendGridEmailBuilder
import uk.co.desirableobjects.sendgrid.SendGridEmail

class RegistrationController {

    def userService
    def grailsApplication
    def athlinksResultsParsingService
    def sendGridService

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

        if(!userInstance){
            redirect uri: "/"
            return
        }

        if(!RegistrationCode.findByUsername(userInstance?.username)) {
            def registrationCode = userService.createRegistrationCode(userInstance)
            body = createRegistrationEmail(userInstance, registrationCode)
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

        runAsync {
            athlinksResultsParsingService.retrieveResults(user)
        }

        redirect controller: 'dashboard', action: 'index'
    }

    private String createRegistrationEmail(User user, RegistrationCode registrationCode) {

        def url = generateLink('confirmation', [t: registrationCode.token])
        def body = grailsApplication.config.security.ui.register.emailBody
        if(body.contains('$')) {
            body = evaluate(body, [user: user, url: url])
        }

        log.info body.toString()

        runAsync {

            SendGridEmail email = new SendGridEmailBuilder()
                    .from("acetrike@gmail.com")
                    .to(registrationCode.username)
                    .subject("Registration Link")
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
