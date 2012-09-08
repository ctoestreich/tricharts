package com.tgid.tri.data

import com.tgid.tri.auth.Racer
import com.tgid.tri.data.parsing.AthlinksResultsParsingService
import com.tgid.tri.auth.UserService
import com.tgid.tri.auth.User
import com.tgid.tri.queue.AthlinksResultsImportJesqueJob

class AthlinksResultsImportJob {

    def group = "AthlinksImport"
    def concurrent = false
    def grailsApplication
    def jesqueService

    static triggers = {
        simple name: 'resultsBootTrigger', startDelay: 60000, repeatCount: 0, repeatInterval: 0
        cron name: 'resultsDailyTrigger', cronExpression: "0 0 6 * * ?"
    }

    def execute() {
        if(!grailsApplication.config.jobs.enabled || !grailsApplication.config.jobs.AthlinksResultsImportJob.enabled){
            log.info "AthlinksResultsImportJob disabled!"
            return
        }

        log.trace "Running AthlinksResultsImportJob ${new Date()}"

        Racer.findAllByLastImportIsNullOrLastImportLessThanEquals(new Date() - 1)?.each {
            jesqueService.enqueue('importAthlinksResults', AthlinksResultsImportJesqueJob.simpleName, it.racerID)
        }
    }
}
