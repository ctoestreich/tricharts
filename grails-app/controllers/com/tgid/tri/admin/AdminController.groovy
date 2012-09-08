package com.tgid.tri.admin

import com.tgid.tri.auth.User
import com.tgid.tri.data.AthlinksResultsImportJob
import com.tgid.tri.queue.AthlinksCoursePatternsImportJesqueJob
import com.tgid.tri.queue.AthlinksRaceCategoryImportJesqueJob
import com.tgid.tri.queue.AthlinksUserResultsImportJesqueJob
import com.tgid.tri.race.Race
import com.tgid.tri.race.StatusType
import grails.plugin.springcache.annotations.CacheFlush
import grails.plugins.springsecurity.Secured
import com.tgid.tri.queue.AthlinksResultsImportJesqueJob

@Secured(['ROLE_ADMIN'])
class AdminController {

    def grailsApplication
    def jesqueService

    def jobSettings() {
        render view: 'jobSettings'
    }

    def updateSettings() {
        grailsApplication.config.jobs.enabled = params.boolean('enabled')
        grailsApplication.config.jobs.athlinksResultsImportJob.enabled = params.boolean('athlinksResultsImportJob')
        grailsApplication.config.jobs.athlinksUserResultsImportJob.enabled = params.boolean('athlinksUserResultsImportJesqueJob')
        grailsApplication.config.jobs.athlinksUserResultsImportJob.enabled = params.boolean('athlinksUserResultsImportJesqueJob')
        grailsApplication.config.jobs.athlinksCoursePatternsImportJob.enabled = params.boolean('athlinksCoursePatternsImportJob')
        redirect action: 'jobSettings'
    }

    @CacheFlush(["triathlonRecordsCache", "runningRecordsCache"])
    def clearRecordsCaches() {
        flash.message = message(code: 'clear.cache', args: ['PRs Cache'])
        redirect(controller: 'dashboard', action: 'index')
    }

    def importAthlinksResults() {
        AthlinksResultsImportJob.triggerNow()
        flash.message = message(code: 'import.started.message', args: ['Import Results'])
        redirect(controller: 'dashboard', action: 'index')
    }

    def importAthlinksRaceCategories() {
        jesqueService.enqueue('importAthlinksReferenceData', AthlinksRaceCategoryImportJesqueJob.simpleName)
        flash.message = message(code: 'import.started.message', args: ['Import Categories'])
        redirect(controller: 'dashboard', action: 'index')
    }

    def importAthlinksCoursePatterns() {
        jesqueService.enqueue('importAthlinksReferenceData', AthlinksCoursePatternsImportJesqueJob.simpleName)
        flash.message = message(code: 'import.started.message', args: ['Import Courses'])
        redirect(controller: 'dashboard', action: 'index')
    }

    def index() { }

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

    def dataImportProcess() {
        def user = User.get(params?.id)

        if(user) {
            jesqueService.enqueue('importAthlinksUserResults', AthlinksUserResultsImportJesqueJob.simpleName, user.id)
            flash.message = g.message(code: 'user.running.import', args: [user.username])
        } else {
            flash.message = g.message(code: 'user.running.import.failed', args: [user.username])
        }
        redirect action: 'dataImport'
    }

    def viewDebugLog() {
        def file = new File(grailsApplication.config.logg.dir.toString() + "/trichartsInfo.log")
        render view: 'viewLog', model: [name: 'Info', data: file]
    }

    def viewErrorLog() {
        def file = new File(grailsApplication.config.logg.dir.toString() + "/trichartsError.log")
        render view: 'viewLog', model: [name: 'Error', data: file]
    }
}
