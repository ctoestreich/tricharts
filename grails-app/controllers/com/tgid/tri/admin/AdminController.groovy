package com.tgid.tri.admin

import com.tgid.tri.auth.User
import com.tgid.tri.data.AthlinksCoursePatternsImportJob
import com.tgid.tri.data.AthlinksRaceCategoryImportJob
import com.tgid.tri.data.AthlinksResultsImportJob
import com.tgid.tri.race.Race
import com.tgid.tri.race.StatusType
import grails.plugin.springcache.annotations.CacheFlush
import grails.plugins.springsecurity.Secured
import com.tgid.tri.data.AthlinksUserResultsImportJob

@Secured(['ROLE_ADMIN'])
class AdminController {

    def grailsApplication
    def athlinksResultsParsingService

    def jobSettings() {
    }

    def updateSettings() {
        grailsApplication.config.jobs.enabled = params.boolean('enabled')
        grailsApplication.config.jobs.AthlinksResultsImportJob.enabled = params.boolean('AthlinksResultsImportJob')
        grailsApplication.config.jobs.AthlinksUserResultsImportJob.enabled = params.boolean('AthlinksUserResultsImportJob')
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
        AthlinksRaceCategoryImportJob.triggerNow()
        flash.message = message(code: 'import.started.message', args: ['Import Categories'])
        redirect(controller: 'dashboard', action: 'index')
    }

    def importAthlinksCoursePatterns() {
        AthlinksCoursePatternsImportJob.triggerNow()
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
            AthlinksUserResultsImportJob.triggerNow([userId: "${user.id}"])
            flash.message = g.message(code: 'user.running.import', args: [user.username])
        } else {
            flash.message = g.message(code: 'user.running.import.failed', args: [user.username])
        }
        redirect action: 'dataImport'
    }
}
