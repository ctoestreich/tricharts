package com.tgid.tri

import com.tgid.tri.auth.User
import uk.co.desirableobjects.sendgrid.SendGridEmail
import uk.co.desirableobjects.sendgrid.SendGridEmailBuilder

class SiteController extends BaseController {

    def sendGridService

    def thanks() { }

    def aboutus() {
        User user = requestedUser

        render view: 'aboutus', model: [user: user]
    }

    def contact() {
        User user = requestedUser

        render view: 'contact', model: [user: user]
    }

    def contactSend() {
        User user = requestedUser
        def email = params?.email ?: ''
        def name = params?.name ?: ''
        def comment = params?.comment ?: ''

//        runAsync {
            if(params?.email) {
                log.info "Sending email from ${params.email}"
                SendGridEmail gridEmail = new SendGridEmailBuilder()
                        .from(name, email)
                        .to("christian@tricharts.com")
                        .subject("TriCharts.com Comments")
                        .withHtml(comment)
                        .build()

                println gridEmail.toMap()

                sendGridService.send(gridEmail)
            }
//        }

        render view: 'sent', model: [user: user]
    }
}
