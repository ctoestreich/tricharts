package com.tgid.tri.data

import com.tgid.tri.data.parsing.AthlinksResultsParsingService

class AthlinksRaceCategoryImportJob {

    def group = "AthlinksImport"
    def concurrent = false

    AthlinksResultsParsingService athlinksResultsParsingService

    static triggers = {
        simple name: 'raceCategoryBootTrigger', startDelay: 1000, repeatCount: 1, repeatInterval: 10000
        cron name: 'raceCategoryWeeklyTrigger', cronExpression: "0 0 0 ? * 6"
    }

    def execute() {
        log.info "Running AthlinksRaceCategoryImportJob ${new Date()}"

        athlinksResultsParsingService.importRaceCategories()
    }
}
