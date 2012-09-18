package com.tgid.tri.queue

import com.tgid.tri.auth.User


class AthlinksUserResultsImportJesqueJob extends LoggableJob {

    static queue = 'athlinksGenericImport'
    static workerPool = 'athlinksGenericImportWorkerPool'

    def grailsApplication
    def athlinksResultsParsingService

    def perform(userId) {
        if(!grailsApplication.config.jobs.enabled || !grailsApplication.config.jobs.athlinksUserResultsImportJob.enabled) {
            withLog(this.class.simpleName, "AthlinksUserResultsImportJesqueJob disabled!")
            return
        }

        if(userId) {
            def user = User.get(userId as Long)
            if(user) {
                withLog(this.class.simpleName, "Running AthlinksUserResultsImportJesqueJob ${new Date()} for user ${user}") {
                    athlinksResultsParsingService.retrieveResults(user)
                }
            } else {
                withLog(this.class.simpleName, "user was not valid for $userId")
            }
        } else {
            withLog(this.class.simpleName, "userId param was not provided.")
        }
    }
}
