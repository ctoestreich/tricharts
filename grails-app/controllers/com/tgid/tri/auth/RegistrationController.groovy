package com.tgid.tri.auth

import groovy.text.SimpleTemplateEngine

class RegistrationController {

    def springSecurityService
    def userService
    def grailsApplication

    def index() {
        def userInstance = new User(params)
        userInstance.enabled = false
        userInstance.accountLocked = true
        switch(request.method) {
            case 'GET':
                render view: 'index', model: [userInstance: userInstance]
                break
            case 'POST':
                if(params?.password != params?.password2){
                    userInstance.errors.rejectValue('password', "user.password.not.match","Passwords do not match")
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

    def newuser(){
        def userInstance = User.get(params?.id)

        if(!RegistrationCode.findByUsername(userInstance?.username)){
            def registrationCode = userService.createRegistrationCode(userInstance)
            createRegistrationEmail(userInstance, registrationCode)
        }

        render view: 'newuser', model: [userInstance: userInstance]
    }

    private String createRegistrationEmail(User user, RegistrationCode registrationCode) {

        def url = generateLink('confirmation', [t: registrationCode.token])
        def body = grailsApplication.config.security.ui.register.emailBody
        if (body.contains('$')) {
            body = evaluate(body, [user: user, url: url])
        }

        sendMail {
            to registrationCode.username
            subject "Registration Link"
            html body.toString()
        }
    }

    private String generateLink(String action, linkParams) {
        createLink(base: "$request.scheme://$request.serverName:$request.serverPort$request.contextPath",
                   controller: 'registration', action: action,
                   params: linkParams)
    }

    protected String evaluate(s, binding) {
        new SimpleTemplateEngine().createTemplate(s).make(binding)
    }
}
