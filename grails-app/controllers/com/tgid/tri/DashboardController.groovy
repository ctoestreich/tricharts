package com.tgid.tri

import com.tgid.tri.auth.User
import com.tgid.tri.race.RaceType
import com.tgid.tri.results.RaceResults
import grails.plugins.springsecurity.Secured

@Secured(["ROLE_USER"])
class DashboardController {

    def springSecurityService

    def index() {

        def results = RaceResults.where {
            user.id == ((User) springSecurityService.currentUser).id
            //segmentResults { segment { segmentType == SegmentType.Run && distance == 3.1 } }
        }

        def runs = results.where {
            race.raceType == RaceType.Running
        }.max(5)

        def triathlons = results.where {
            race.raceType == RaceType.Triathlon
        }.max(5)


        render view: 'index', model: [runs: runs, triathlons: triathlons]
    }

    def races(){}
}
