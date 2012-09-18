package com.tgid.tri.ui

import com.tgid.tri.BaseChartingController
import com.tgid.tri.auth.State
import com.tgid.tri.auth.User
import com.tgid.tri.race.RaceCategoryType
import com.tgid.tri.race.RaceType
import com.tgid.tri.race.SegmentType
import com.tgid.tri.results.RaceResult
import grails.plugin.springcache.annotations.Cacheable
import grails.plugins.springsecurity.Secured

@Secured(["ROLE_USER"])
class VisualizationController extends BaseChartingController {

    def index() {
        User user = requestedUser

        render view: 'index', model: [user: user]
    }

    def prs() {
        User user = requestedUser

        render view: 'prs', model: [user: user]
    }

    def averages() {
        User user = requestedUser

        def races = getRaceCategoriesByType(params?.raceType)

        render view: 'averages/averages', model: [user: user, races: races]
    }

    def averagePlaceOverall() {
        User user = requestedUser

        def races = getRaceCategoriesByType(params?.raceType)

        render view: 'averages/averagePlaceOverall', model: [user: user, races: races]
    }

    def averagePlaceAgeGroup() {
        User user = requestedUser

        def races = getRaceCategoriesByType(params?.raceType)

        render view: 'averages/averagePlaceAgeGroup', model: [user: user, races: races]
    }

    def progression() {
        User user = requestedUser

        def races = getRaceCategoriesByType(params?.raceType)

        render view: 'progression', model: [raceResult: new RaceResult(), user: user, races: races]
    }

    def sportProgression() {
        User user = requestedUser
        def userId = user.id
        def resultDiv = params?.div ?: 'resultDiv'
        def queryRaceCategoryType = RaceCategoryType.getRaceCategoryType(params?.raceCategoryType)
        def queryRaceType = RaceType.getRaceType(params?.raceType)

        if(!queryRaceCategoryType || !queryRaceType) {
            log.error('missing params')
            return
        }

        renderRunProgressionChart(resultDiv, userId, queryRaceType, queryRaceCategoryType)
    }

    def runningProgression() {
        User user = requestedUser
        def userId = user.id
        def resultDiv = params?.div ?: 'resultDiv'
        def queryRaceCategoryType = RaceCategoryType.getRaceCategoryType(params?.raceCategoryType ?: RaceCategoryType.OneMile.raceCategoryType)
        def queryRaceType = RaceType.Running

        renderRunProgressionChart(resultDiv, userId, queryRaceType, queryRaceCategoryType)
    }

    def triathlonProgression() {
        User user = requestedUser
        def userId = user.id
        def resultDiv = params?.div ?: 'resultDiv'
        def queryRaceCategoryType = RaceCategoryType.getRaceCategoryType(params?.raceCategoryType ?: RaceCategoryType.Sprint.raceCategoryType)
        def queryRaceType = RaceType.Triathlon

        renderTriathlonProgressionChart(resultDiv, userId, queryRaceType, queryRaceCategoryType)
    }

    @Cacheable(cache = 'chartCache', keyGenerator = 'authenticationAwareKeyGenerator')
    def runningAveragePlaces() {
        User user = requestedUser
        def userId = user.id
        def type = params?.type ?: 'oa'
        def resultDiv = params?.div ?: 'resultDiv'
        def queryRaceType = RaceType.Running

        renderRunAveragePlacesChart(resultDiv, userId, queryRaceType, type)
    }

    @Cacheable(cache = 'chartCache', keyGenerator = 'authenticationAwareKeyGenerator')
    def runningAverages() {
        User user = requestedUser
        def userId = user.id
        def resultDiv = params?.div ?: 'resultDiv'
        def queryRaceType = RaceType.Running

        renderRunAveragesChart(resultDiv, userId, queryRaceType)
    }

    @Cacheable(cache = 'chartCache', keyGenerator = 'authenticationAwareKeyGenerator')
    def triathlonAveragePlaces() {
        User user = requestedUser
        def userId = user.id
        def resultDiv = params?.div ?: 'resultDiv'
        def type = params?.type ?: 'oa'
        def queryRaceType = RaceType.Triathlon
        def segmentType = params?.segmentType == "Overall" ? null : params?.segmentType as SegmentType

        renderTriathlonAveragePlacesChart(resultDiv, userId, queryRaceType, segmentType, type)
    }

    @Cacheable(cache = 'chartCache', keyGenerator = 'authenticationAwareKeyGenerator')
    def triathlonAverages() {
        User user = requestedUser
        def userId = user.id
        def resultDiv = params?.div ?: 'resultDiv'
        def queryRaceType = RaceType.Triathlon
        def segmentType = params?.segmentType as SegmentType

        renderTriathlonAveragesChart(resultDiv, userId, queryRaceType, segmentType)
    }

    @Cacheable("runningRecordsCache")
    def runningPrs() {
        User user = requestedUser
        def userId = user.id
        def resultDiv = params?.div ?: 'resultDiv'
        def queryRaceType = RaceType.Running

        renderRunningPrsChart(resultDiv, userId, queryRaceType)
    }

    @Cacheable("triathlonRecordsCache")
    def triathlonPrs() {
        User user = requestedUser
        def userId = user.id
        def resultDiv = params?.div ?: 'resultDiv'
        def queryRaceType = RaceType.Triathlon

        renderTriathlonPrsChart(resultDiv, userId, queryRaceType)
    }

    def triathlonRecords() {
        User user = requestedUser
        def userId = user?.id
        def data = visualizationService.mapTriathlonRecords(userId)

        render template: "triathlonRecord", model: [data: data]
    }

    def runningRecords() {
        User user = requestedUser
        def userId = user.id
        def data = visualizationService.mapRunningRecords(userId)
        render template: "runningRecord", model: [data: data, user: user]
    }

    def mileChart() {
        User user = requestedUser
        def userId = user.id
        def columnName = 'Mile Time'
        def resultTitle = "Mile Run Times"
        def resultDiv = "mileChartDiv"
        def queryRaceCategoryType = RaceCategoryType.OneMile
        def queryRaceType = RaceType.Running

        renderDashboardChart(columnName, resultTitle, resultDiv, userId, queryRaceType, queryRaceCategoryType)
    }

    def fiveKilometerChart() {
        User user = requestedUser
        def userId = user.id
        def columnName = '5k Time'
        def resultTitle = "5k Run Times"
        def resultDiv = "fiveKilometerChartDiv"
        def queryRaceCategoryType = RaceCategoryType.FiveKilometer
        def queryRaceType = RaceType.Running

        renderDashboardChart(columnName, resultTitle, resultDiv, userId, queryRaceType, queryRaceCategoryType)
    }

    def marathonChart() {
        User user = requestedUser
        def userId = user.id
        def columnName = 'Marathon Time'
        def resultTitle = "Marathon Run Times"
        def resultDiv = "marathonChartDiv"
        def queryRaceCategoryType = RaceCategoryType.Marathon
        def queryRaceType = RaceType.Running

        renderDashboardChart(columnName, resultTitle, resultDiv, userId, queryRaceType, queryRaceCategoryType)
    }

    def runScatter() {
        User user = requestedUser
        def userId = user.id
        def ageMin = params.int("ageMin") ?: 30
        def ageMax = params.int("ageMax") ?: 35
        def div = params.div
        def showMale = params.boolean('m')
        def showFemale = params.boolean('f')
        def showYou = params.boolean('y')
        def state = State.findByAbbrev(params?.state ?: 'MN')
        def raceCategoryType = RaceCategoryType?.getRaceCategoryType(params.r) ?: RaceCategoryType.OneMile
        def raceType = RaceType.getRaceType(params.t) ?: RaceType.Running
        def results = visualizationService.mapRunningScatter(userId, ageMin, ageMax, state, raceCategoryType, raceType)

        render template: "/templates/charts/runScatter", div: "scatter", model: [div: div, state: state, you: results.you, males: results.males, females: results.females,
                user: user,
                raceCategoryType: raceCategoryType,
                showMale: showMale,
                showFemale: showFemale,
                showYou: showYou]
    }

    def runMileAverageByState() {
        User user = requestedUser
        def races = getRaceCategoriesByType('Running')
        def raceCategoryType = RaceCategoryType?.getRaceCategoryType(params.r) ?: RaceCategoryType.OneMile

        render view: 'global/averagesByState', model: [raceType: RaceType.Running, user: user, races: races, raceCategoryType: raceCategoryType]
    }

    def triMileAverageByState() {
        User user = requestedUser
        def races = getRaceCategoriesByType('Triathlon')
        def raceCategoryType = RaceCategoryType?.getRaceCategoryType(params.r) ?: RaceCategoryType.Sprint

        render view: 'global/averagesByState', model: [raceType: RaceType.Triathlon, user: user, races: races, raceCategoryType: raceCategoryType]
    }
}
