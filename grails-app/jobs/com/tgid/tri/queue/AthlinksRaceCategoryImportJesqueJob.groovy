package com.tgid.tri.queue

import com.tgid.tri.data.parsing.AthlinksResultsParsingService

class AthlinksRaceCategoryImportJesqueJob extends LoggableJob {

    static queue = 'importAthlinksReferenceData'
    static workerPool = 'athlinksReferenceImportWorkerPool'

    AthlinksResultsParsingService athlinksResultsParsingService
    def grailsApplication

    static triggers = {
        cron name: 'raceCategoryWeeklyTrigger', jesqueJobName: AthlinksRaceCategoryImportJesqueJob.simpleName, jesqueQueue: 'importAthlinksReferenceData', cronExpression: "0 0 0 ? * 6", timeZone: 'Pacific/Honolulu'
    }

    def perform() {
        if(!grailsApplication.config.jobs.enabled || !grailsApplication.config.jobs.athlinksRaceCategoryImportJob.enabled) {
            log.info "AthlinksRaceCategoryImportJesqueJob disabled!"
            return
        }

        log.info "Running AthlinksRaceCategoryImportJob ${new Date()}"
        withLog(this.class.simpleName, '') {
            athlinksResultsParsingService.importRaceCategories()
        }
    }
}
