package com.tgid.tri.data

import com.tgid.tri.data.parsing.AthlinksResultsParsingService

class AthlinksCoursePatternsImportJob {

    def group = "AthlinksImport"
    def concurrent = false

    AthlinksResultsParsingService athlinksResultsParsingService

    static triggers = {
        simple name: 'coursePatternBootTrigger', startDelay: 1000, repeatCount: 1, repeatInterval: 10000
        cron name: 'coursePatternWeeklyTrigger', cronExpression: "0 0 0 ? * 6"
    }

    def execute() {
        log.info "Running AthlinksCoursePatternsImportJob ${new Date()}"
        athlinksResultsParsingService.importCoursePatterns()
    }
}
