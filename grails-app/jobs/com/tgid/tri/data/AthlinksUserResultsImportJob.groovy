package com.tgid.tri.data

import com.tgid.tri.auth.User
import com.tgid.tri.auth.UserService
import com.tgid.tri.data.parsing.AthlinksResultsParsingService

/**
 */
class AthlinksUserResultsImportJob {

    def group = "AthlinksImport"
    def concurrent = false
    def grailsApplication

    UserService userService
    AthlinksResultsParsingService athlinksResultsParsingService

    static triggers = {
        simple name: 'userResultsBootTrigger', startDelay: 0, repeatCount: 0, repeatInterval: 0
    }

    def execute(context) {
        if(!grailsApplication.config.jobs.enabled || !grailsApplication.config.jobs["${this.class.name}"].enabled) {
            log.info "AthlinksResultsImportJob disabled!"
            return
        }

        def userId = context.mergedJobDataMap.get('userId')


        if(userId) {
            def user = User.get(userId)
            if(user) {
                log.info "Running AthlinksUserResultsImportJob ${new Date()} for user ${user}"
                athlinksResultsParsingService.retrieveResults(user)
            } else {
                log.info "user was not valid for $userId"
            }
        }
    }
}
