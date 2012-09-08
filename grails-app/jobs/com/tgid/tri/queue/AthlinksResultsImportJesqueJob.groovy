package com.tgid.tri.queue

import com.tgid.tri.auth.Racer
import com.tgid.tri.auth.UserService
import com.tgid.tri.data.parsing.AthlinksResultsParsingService

class AthlinksResultsImportJesqueJob extends LoggableJob {

    def grailsApplication

    static queue = 'importAthlinksResults'
    static workerPool = 'athlinksResultsImportWorkerPool'

    UserService userService
    AthlinksResultsParsingService athlinksResultsParsingService

    def perform(racerId) {
        if(!grailsApplication.config.jobs.enabled || !grailsApplication.config.jobs.athlinksResultsImportJob.enabled) {
            withLog(this.class.simpleName, 'AthlinksResultsImportJob disabled!')
            return
        }

        if(racerId) {
            log.trace "Running AthlinksResultsImportJob ${new Date()}"
            def racer = Racer.findByRacerID(racerId as Long)
            if(racer) {
                withLog(this.class.simpleName, "Retrieving races & results for ${racer.user} using id: ${racer.racerID}") {
                    athlinksResultsParsingService.retrieveResults(racer)
                }
            }
        }
    }
}
