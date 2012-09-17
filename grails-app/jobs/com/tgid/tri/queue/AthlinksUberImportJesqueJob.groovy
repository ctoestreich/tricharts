package com.tgid.tri.queue

/**
 */
class AthlinksUberImportJesqueJob extends LoggableJob {

    static queue = 'athlinksGenericImport'
    static workerPool = 'athlinksGenericImportWorkerPool'

    def grailsApplication
    def athlinksResultsParsingService

    def perform(userId) {
        if(!grailsApplication.config.jobs.enabled || !grailsApplication.config.jobs.athlinksUberImportJesqueJob.enabled) {
            withLog(this.class.simpleName, "AthlinksUberImportJesqueJob disabled!")
            return
        }

//        withLog(this.class.simpleName, "Running AthlinksUberImportJesqueJob ${new Date()} for user ${user}") {
//            athlinksResultsParsingService.retrieveResults(user)
//        }
    }
}
