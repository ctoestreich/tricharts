package com.tgid.tri.data

import com.tgid.tri.auth.Racer
import com.tgid.tri.data.parsing.AthlinksResultsParsingService
import com.tgid.tri.auth.UserService
import com.tgid.tri.auth.User

class AthlinksResultsImportJob {

    def group = "AthlinksImport"
    def concurrent = false
    def grailsApplication

    UserService userService
    AthlinksResultsParsingService athlinksResultsParsingService

    static triggers = {
        simple name: 'resultsBootTrigger', startDelay: 120000, repeatCount: 1, repeatInterval: 10000
        cron name: 'resultsDailyTrigger', cronExpression: "0 0 6 * * ?"
    }

    def execute() {
        if(!grailsApplication.config.jobs.enabled || !grailsApplication.config.jobs["${this.class.name}"].enabled){
            log.info "AthlinksResultsImportJob disabled!"
            return
        }

        log.trace "Running AthlinksResultsImportJob ${new Date()}"

        Racer.findAllByLastImportIsNullOrLastImportLessThanEquals(new Date() - 1)?.each {
            log.info "Retrieving races & results for ${it.user} using id: ${it.racerID}"
            athlinksResultsParsingService.retrieveResults(it)
        }
    }
}
