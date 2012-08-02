package com.tgid.tri

import grails.plugins.springsecurity.Secured
import com.tgid.tri.results.RaceResults
import com.tgid.tri.race.SegmentType
import com.tgid.tri.auth.User
import com.tgid.tri.race.RaceType

@Secured(["ROLE_USER"])
class DashboardController {
    def springSecurityService

    def index() {

        def results = RaceResults.where {
            user.id == ((User)springSecurityService.currentUser).id
            //segmentResults { segment { segmentType == SegmentType.Run && distance == 3.1 } }
        }

        def runs = results.where {
            race.raceType == RaceType.Running
        }

        def triathlons = results.where{
            race.raceType == RaceType.Triathlon
        }


        render view: 'index', model:  [runs: runs, triathlons: triathlons]
    }
}
