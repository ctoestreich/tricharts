package com.tgid.tri.queue

import com.tgid.tri.auth.User


class AthlinksUserResultsImportJesqueJob {

    static queue = 'importAthlinksUserResults'
    static workerPool = 'athlinksUserResultsImportWorkerPool'

    def grailsApplication
    def athlinksResultsParsingService

    def perform(userId) {
        if(!grailsApplication.config.jobs.enabled || !grailsApplication.config.jobs.athlinksUserResultsImportJob.enabled) {
            log.info "AthlinksUserResultsImportJesqueJob disabled!"
            return
        }

        if(userId) {
            def user = User.get(userId as Long)
            if(user) {
                log.info "Running AthlinksUserResultsImportJesqueJob ${new Date()} for user ${user}"
                athlinksResultsParsingService.retrieveResults(user)
            } else {
                log.info "user was not valid for $userId"
            }
        }
    }
}
