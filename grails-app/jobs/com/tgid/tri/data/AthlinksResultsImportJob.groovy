package com.tgid.tri.data

import com.tgid.tri.auth.User
import com.tgid.tri.data.parsing.AthlinksResultsParsingService



class AthlinksResultsImportJob {

    def group = "AthlinksImport"
    def concurrent = false

    AthlinksResultsParsingService athlinksResultsParsingService

    static triggers = {
        simple name:'simpleTrigger', startDelay:10000, repeatInterval: 30000, repeatCount: 10
        cron name: 'myTrigger', cronExpression: "0 0 6 * * ?"
    }

    def execute() {
        User.findAllWhere(athlinkRacerId: !null).each {
            athlinksResultsParsingService.retrieveResults(it)
        }
    }
}
