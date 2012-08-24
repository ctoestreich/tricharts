package com.tgid.tri.data

import com.tgid.tri.auth.Racer
import com.tgid.tri.data.parsing.AthlinksResultsParsingService

class AthlinksResultsImportJob {

    def group = "AthlinksImport"
    def concurrent = false

    AthlinksResultsParsingService athlinksResultsParsingService

    static triggers = {
        simple name: 'resultsBootTrigger', startDelay: 120000, repeatCount: 1, repeatInterval: 10000
        cron name: 'resultsDailyTrigger', cronExpression: "0 0 6 * * ?"
    }

    def execute() {
        log.trace "Running AthlinksResultsImportJob ${new Date()}"
        Racer.list().each {
            log.info "Adding Results for ${it.user} using id: ${it.racerID}"
            athlinksResultsParsingService.retrieveResults(it.user)
        }
    }
}
