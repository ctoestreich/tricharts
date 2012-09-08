package com.tgid.tri

import com.tgid.tri.auth.User
import uk.co.desirableobjects.sendgrid.SendGridEmail
import uk.co.desirableobjects.sendgrid.SendGridEmailBuilder
import grails.plugin.springcache.annotations.Cacheable

class SiteController extends BaseController {

    def sendGridService
    def redisService

    @Cacheable('siteCache')
    def index(){
        def resultCount = redisService.memoize('resultCount', 3600){
            com.tgid.tri.results.RaceResult.count()
        }
        render view: '/index', model: [resultCount: resultCount]
    }

    @Cacheable('siteCache')
    def faq() { }

    @Cacheable('siteCache')
    def thanks() { }

    @Cacheable('siteCache')
    def aboutus() {
        User user = requestedUser

        render view: 'aboutus', model: [user: user]
    }

    @Cacheable('siteCache')
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

                sendGridService.send(gridEmail)
            }
//        }

        render view: 'sent', model: [user: user]
    }
}
