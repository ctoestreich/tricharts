package com.tgid.tri.queue

import com.tgid.tri.data.parsing.AthlinksResultsParsingService

class AthlinksCoursePatternsImportJesqueJob extends LoggableJob {

    static queue = 'importAthlinksReferenceData'
    static workerPool = 'athlinksReferenceImportWorkerPool'

    AthlinksResultsParsingService athlinksResultsParsingService
    def grailsApplication

    static triggers = {
        cron name: 'coursePatternWeeklyTrigger', jesqueJobName: AthlinksCoursePatternsImportJesqueJob.simpleName, jesqueQueue: 'importAthlinksReferenceData', cronExpression: "0 0 3 ? * SUN *", timeZone: 'Pacific/Honolulu'
    }

    def perform() {
        if(!grailsApplication.config.jobs.enabled || !grailsApplication.config.jobs.athlinksCoursePatternsImportJob.enabled) {
            log.info "AthlinksCoursePatternsImportJesqueJob disabled!"
            return
        }
        withLog(this.class.simpleName, 'Importing course patterns') {
            athlinksResultsParsingService.importCoursePatterns()
        }
    }
}
