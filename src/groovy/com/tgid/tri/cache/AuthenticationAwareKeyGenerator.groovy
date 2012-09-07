package com.tgid.tri.cache

import grails.plugin.springcache.key.CacheKeyBuilder
import grails.plugin.springcache.web.ContentCacheParameters
import grails.plugin.springcache.web.key.DefaultKeyGenerator
import org.codehaus.groovy.grails.commons.ApplicationHolder

/**
 */
public class AuthenticationAwareKeyGenerator extends DefaultKeyGenerator {

    @Override
    protected void generateKeyInternal(CacheKeyBuilder builder, ContentCacheParameters context) {
        super.generateKeyInternal(builder, context)
        def springSecurityService = ApplicationHolder.application.mainContext.getBean('springSecurityService')
        if(springSecurityService?.isLoggedIn()) {
            builder << "authUserId=${springSecurityService.principal.getId()}".toString()
        }
    }
}