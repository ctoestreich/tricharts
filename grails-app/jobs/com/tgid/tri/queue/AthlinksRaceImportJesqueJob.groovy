package com.tgid.tri.queue

import com.tgid.tri.data.parsing.AthlinksResultsParsingService
import com.tgid.tri.race.Race

/**
 */
class AthlinksRaceImportJesqueJob extends LoggableJob {

    def grailsApplication

    static queue = 'athlinksGenericImport'
    static workerPool = 'athlinksGenericImportWorkerPool'

    AthlinksResultsParsingService athlinksResultsParsingService

    def perform(athlinkRaceID) {
        if(!grailsApplication.config.jobs.enabled || !grailsApplication.config.jobs.athlinksRaceImportJob.enabled) {
            withLog(this.class.simpleName, 'AthlinksResultsImportJob disabled!')
            return
        }

        if(athlinkRaceID) {
            def race = Race.findByAthlinkRaceID(athlinkRaceID as Long)
            if(race) {
                withLog(this.class.simpleName, "Retrieving race ${race?.id} - ${race?.name}") {
                    try {
                        athlinksResultsParsingService.updateAthlinksRace(race)
                    } catch(Exception e) {
                        log.error e
                        withLog(this.class.simpleName, "Error: ${e}")
                    }
                }
            } else {
                withLog(this.class.simpleName, "Race not found using athlinkRaceID:${athlinkRaceID}!")
            }
        } else {
            withLog(this.class.simpleName, "AthlinkRaceID was not provided!")
        }
    }
}
