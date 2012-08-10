package com.tgid.tri.admin

import com.tgid.tri.race.Race
import grails.plugins.springsecurity.Secured
import com.tgid.tri.race.StatusType

@Secured(['ROLE_ADMIN'])
class AdminController {

    def index() { }

    def raceList() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        def races = Race.list(params)
        if (params?.statusType) {
            races = races.findAll { it.statusType == StatusType.getStatusType(params.int('statusType')) }
        }

        [raceInstanceList: races, raceInstanceTotal: races.size()]
//        def approvedRaces = races?.findAll { it.statusType == StatusType.Approved}
//        def pendingRaces = races?.findAll { it.statusType == StatusType.Pending}
//        def deletedRaces = races?.findAll { it.statusType == StatusType.Deleted}

//        [raceInstanceList: approvedRaces, raceInstanceTotal: approvedRaces.size(),
//                pendingRaceInstanceList: approvedRaces, pendingRaceInstanceTotal: pendingRaces.size(),
//                deletedRaceInstanceList: approvedRaces, deletedRaceInstanceTotal: deletedRaces.size()]
    }

    def updateRaceStatus(){
        def race = Race.get(params?.raceId)
        if (race){
            race.statusType = params?.newStatus ?: StatusType.Pending
            race.save()
        }
        redirect action:'raceList', params: params
    }
}
