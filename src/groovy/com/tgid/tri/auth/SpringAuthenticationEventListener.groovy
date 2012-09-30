package com.tgid.tri.auth

import groovy.util.logging.Commons
import org.springframework.context.ApplicationListener
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 */
@Commons
class SpringAuthenticationEventListener implements ApplicationListener<AuthenticationSuccessEvent>, LogoutHandler {

    @Override void onApplicationEvent(AuthenticationSuccessEvent event) {
        def user = User.get(event?.authentication?.principal?.id as Long)
        if(user) {
            LoginHistory.withTransaction {
                new LoginHistory(user: user, success: true).save(flush: true)
            }
        }
    }

    @Override void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        log.info "User ${authentication?.getPrincipal()?.id} logged out"
    }
}