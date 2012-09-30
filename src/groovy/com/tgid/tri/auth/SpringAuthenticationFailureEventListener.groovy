package com.tgid.tri.auth

import groovy.util.logging.Commons
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent

/**
 */
@Commons
class SpringAuthenticationFailureEventListener implements ApplicationListener<AbstractAuthenticationFailureEvent> {

    @Override void onApplicationEvent(AbstractAuthenticationFailureEvent event) {
        def user = User.findByUsername(event?.authentication?.name)
        if(user) {
            LoginHistory.withTransaction {
                new LoginHistory(user: user, success: false).save(flush: true)
            }
        }
    }
}