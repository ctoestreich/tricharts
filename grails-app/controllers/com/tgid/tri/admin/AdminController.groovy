package com.tgid.tri.admin

import com.tgid.tri.auth.Racer
import com.tgid.tri.auth.User
import com.tgid.tri.data.AthlinksResultsImportJob
import com.tgid.tri.queue.AthlinksCoursePatternsImportJesqueJob
import com.tgid.tri.queue.AthlinksRaceCategoryImportJesqueJob
import com.tgid.tri.queue.AthlinksRaceImportJesqueJob
import com.tgid.tri.queue.AthlinksUserResultsImportJesqueJob
import com.tgid.tri.race.Race
import com.tgid.tri.race.StatusType
import grails.plugin.springcache.annotations.CacheFlush
import grails.plugins.springsecurity.Secured

@Secured(['ROLE_ADMIN'])
class AdminController {

    def grailsApplication
    def jesqueService
    def userService
    def redisService
    def athlinksResultsParsingService

    def jobSettings() {
        render view: 'jobSettings'
    }

    def updateSettings() {
        grailsApplication.config.jobs.enabled = params.boolean('enabled')
        grailsApplication.config.jobs.athlinksResultsImportJob.enabled = params.boolean('athlinksResultsImportJob')
        grailsApplication.config.jobs.athlinksUserResultsImportJob.enabled = params.boolean('athlinksUserResultsImportJesqueJob')
        grailsApplication.config.jobs.athlinksUserResultsImportJob.enabled = params.boolean('athlinksUserResultsImportJesqueJob')
        grailsApplication.config.jobs.athlinksCoursePatternsImportJob.enabled = params.boolean('athlinksCoursePatternsImportJob')
        grailsApplication.config.jobs.athlinksRaceImportJob.enabled = params.boolean('athlinksRaceImportJob')
        redirect action: 'jobSettings'
    }

    @CacheFlush(["triathlonRecordsCache", "runningRecordsCache"])
    def clearRecordsCaches() {
        flash.message = message(code: 'clear.cache', args: ['PRs Cache'])
        redirect(controller: 'admin', action: 'index')
    }

    @CacheFlush(["chartCache"])
    def clearChartCaches() {
        flash.message = message(code: 'clear.cache', args: ['Chart Cache'])
        redirect(controller: 'admin', action: 'index')
    }

    @CacheFlush(["siteCache"])
    def clearSiteCaches() {
        flash.message = message(code: 'clear.cache', args: ['Site Cache'])
        redirect(controller: 'admin', action: 'index')
    }

    def importAthlinksResults() {
        AthlinksResultsImportJob.triggerNow()
        flash.message = message(code: 'import.started.message', args: ['Import Results', ''])
        redirect(controller: 'admin', action: 'runJob')
    }

    def importAthlinksRaceCategories() {
        jesqueService.enqueue('importAthlinksReferenceData', AthlinksRaceCategoryImportJesqueJob.simpleName)
        flash.message = message(code: 'import.started.message', args: ['Import Categories',''])
        redirect(controller: 'admin', action: 'runJob')
    }

    def importAthlinksCoursePatterns() {
        jesqueService.enqueue('importAthlinksReferenceData', AthlinksCoursePatternsImportJesqueJob.simpleName)
        flash.message = message(code: 'import.started.message', args: ['Import Courses',''])
        redirect(controller: 'admin', action: 'runJob')
    }

    def importAllAthlinksRaces() {
        def count = 0
        Race.list().each {
            if(it?.athlinkRaceID) {
                count++
                jesqueService.enqueue('athlinksGenericImport', AthlinksRaceImportJesqueJob.simpleName, it.athlinkRaceID)
            }
        }
        flash.message = g.message(code: 'import.started.message', args: [AthlinksRaceImportJesqueJob.simpleName, " ${count} Records"])
        redirect action: 'runJob'
    }

    def index() {
        if(params?.clearCache) {
            redisService.deleteKeysWithPattern("admin-*")
        }
        render view: 'index'
    }

    def runJob() {}

    def raceList() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.statusType = params?.int('statusType') ?: 2
        def criteria = Race.createCriteria()
        def raceCount = Race.findAllByStatusType(StatusType.getStatusType(params?.int('statusType') ?: 2)).size()
        def races = criteria.list(max: params.max, offset: params?.offset ?: 0, sort: params?.sort ?: 'date') {
            eq('statusType', StatusType.getStatusType(params?.int('statusType') ?: 2))
        }
        [raceInstanceList: races, raceInstanceTotal: raceCount]
    }

    def updateRaceStatus() {
        def race = Race.get(params?.raceId)
        if(race) {
            race.statusType = params?.newStatus ?: StatusType.Pending
            race.save()
        }
        redirect action: 'raceList', params: params
    }

    def dataImport() {
        render view: 'dataImport'
    }

    def dataImportRace() {
        render view: 'dataImportRace'
    }

    def dataImportProcess() {
        def user = User.get(params?.id)

        if(user) {
            jesqueService.enqueue('athlinksGenericImport', AthlinksUserResultsImportJesqueJob.simpleName, user.id)
            flash.message = g.message(code: 'user.running.import', args: [user.username])
        } else {
            flash.message = g.message(code: 'user.running.import.failed', args: [user.username])
        }
        redirect action: 'dataImport'
    }

    def dataImportRaceProcess() {
        def race = Race.get(params?.id)

        if(race?.athlinkRaceID) {
            jesqueService.enqueue('athlinksGenericImport', AthlinksRaceImportJesqueJob.simpleName, race.athlinkRaceID)
            flash.message = g.message(code: 'race.running.import', args: [race.name])
        } else {
            flash.message = g.message(code: 'race.running.import.failed', args: [race])
        }
        redirect action: 'dataImportRace'
    }

    def viewDebugLog() {
        def file = new File(grailsApplication.config.logg.dir.toString() + "/trichartsInfo.log")
        render view: 'viewLog', model: [name: 'Info', data: file]
    }

    def viewErrorLog() {
        def file = new File(grailsApplication.config.logg.dir.toString() + "/trichartsError.log")
        render view: 'viewLog', model: [name: 'Error', data: file]
    }

    def importRacers() {
        def user = User.get(params?.userID)
        def racers = userService.lookupAthlinkRacers(user)

        render view: 'importRacers', model: [user: user, userID: user.id, racers: racers]
    }

    def completeImport() {
        def user = User.get(params?.userID)
        def save = false
        def racers = params.list('racers')
        racers?.each { String racerId ->
            if(!Racer.findByUserAndRacerID(user, racerId as Long)) {
                save = true
                user.addToRacers(new Racer(racerID: racerId as Long, lastImport: new Date() - 2))
            }
        }
        if(save) {user.save(flush: true)}

        runAsync {
            athlinksResultsParsingService.retrieveResults(user)
        }

        redirect controller: 'user', action: 'show', id: user.id
    }
}
