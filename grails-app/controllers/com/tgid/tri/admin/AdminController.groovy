package com.tgid.tri.admin

import com.tgid.tri.race.Race
import com.tgid.tri.race.StatusType
import grails.plugins.springsecurity.Secured
import grails.plugin.springcache.annotations.CacheFlush
import com.tgid.tri.data.AthlinksResultsImportJob

@Secured(['ROLE_ADMIN'])
class AdminController {

    def athlinksResultsParsingService

    @CacheFlush(["triathlonRecordsCache", "runningRecordsCache"])
    def clearRecordsCaches(){
        redirect( controller: 'dashboard', action: 'index')
    }

    def importAthlinksData(){
        AthlinksResultsImportJob.triggerNow()
        redirect( controller: 'dashboard', action: 'index')
    }

    def index() { }

    def raceList() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.statusType = params?.int('statusType')?:2
        def criteria = Race.createCriteria()
        def raceCount = Race.findAllByStatusType(StatusType.getStatusType(params?.int('statusType')?:2)).size()
        def races = criteria.list(max: params.max, offset: params?.offset?:0, sort: params?.sort ?: 'date'){
            eq('statusType', StatusType.getStatusType(params?.int('statusType')?:2))
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
}
