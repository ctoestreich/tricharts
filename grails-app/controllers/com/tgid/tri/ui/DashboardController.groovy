package com.tgid.tri.ui

import com.tgid.tri.BaseController
import com.tgid.tri.auth.User
import com.tgid.tri.results.RaceResult
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(["ROLE_USER"])
class DashboardController extends BaseController {

    def raceResultService
    def raceService

    def index() {
        User user = requestedUser

        def latestResults = RaceResult.findAllByUser(user)?.sort {a,b -> b.date <=> a.date }
        latestResults = latestResults.subList(0, latestResults.size() >= 5 ? 5 : latestResults.size())

        render view: 'index', model: [user: user, latestResults: latestResults]
    }

    def racesCompleted() {
        User user = requestedUser
        def userId = user.id
        def racesPie = [:]
        def queryDate = params.int('year')
        def yearly = params.boolean('yearly')

        def results = RaceResult.where {
            user.id == userId
            if(queryDate) {
                race.date >= new Date(queryDate - 1900, 0, 1)
            }
        }

        def closure = { List races, Map map, Boolean isYearly ->
            races.sort { a, b -> b.race.date <=> a.race.date}
            races.each { RaceResult raceResult ->
                def key = isYearly ? raceResult.race.date.year + 1900 : "${raceResult.race.raceCategoryType.raceCategoryType} ${raceResult.race.raceType}"
                if(!map.containsKey(key)) {
                    map.put(key, 1)
                } else {
                    def total = map.get(key) + 1
                    map.put(key, total)
                }
            }
            def pieData = []
            map.each {
                pieData << ["${it.key}", it.value]
            }
            return pieData
        }

        def pieData = closure(results.list(), racesPie, yearly) ?: [[]]

        def series = [:]
        series.put('type', "pie")
        series.put("name", "Races")
        series.put("data", pieData)

        if(pieData.get(0)) {
            render([series] as JSON)
        } else {
            render ""
        }

    }
}
