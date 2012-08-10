package com.tgid.tri.ui

import com.tgid.tri.BaseController
import com.tgid.tri.auth.User
import com.tgid.tri.race.RaceCategoryType
import com.tgid.tri.race.RaceType
import com.tgid.tri.race.SegmentType
import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.SegmentResult

class VisualizationController extends BaseController {

    def sportProgression() {
        User user = requestedUser
        def userId = user.id
        def resultDiv = params?.div ?: 'resultDiv'
        def queryRaceCategoryType = RaceCategoryType.getRaceCategoryType(params?.raceCategoryType)
        def queryRaceType = RaceType.getRaceType(params?.raceType)

        if(!queryRaceCategoryType || !queryRaceType){
            println 'invalid params'
            log.error('missing params')
            return;
        }

        renderProgressionChart(resultDiv, userId, queryRaceType, queryRaceCategoryType)
    }

    def runningProgression() {
        User user = requestedUser
        def userId = user.id
        def resultDiv = params?.div ?: 'resultDiv'
        def queryRaceCategoryType = RaceCategoryType.getRaceCategoryType(params?.raceCategoryType ?: RaceCategoryType.OneMile.raceCategoryType)
        def queryRaceType = RaceType.Running

        renderProgressionChart(resultDiv, userId, queryRaceType, queryRaceCategoryType)
    }

    private void renderProgressionChart(String resultDiv, userId, RaceType queryRaceType, RaceCategoryType queryRaceCategoryType) {

        def results = RaceResult.where {
            user.id == userId
            race.raceType == queryRaceType
            race.raceCategoryType == queryRaceCategoryType
        }

        def sortedResults = results.list().sort {a, b -> a.date <=> b.date}
        def data = [:]
        sortedResults.each { result ->
            def key = result.date.year + 1900
            def period = result.duration.toPeriod()
            if(data.containsKey(key)) {
                data.get(key).append(",{x: Date.UTC(${result.date.format('1970, M-1, dd')}), y: Date.parse('1-1-1 ${period.hours}:${(period.minutes.toString().length() == 1 ? '0' : '') + period.minutes}:${period.seconds}')-timeToSubtract, name:'${result.race.name} ${result.race.date.format("M-dd-yyyy")}'}")
            } else {
                data.put(key, new StringBuilder("{x: Date.UTC(${result.date.format('1970, M-1, dd')}), y: Date.parse('1-1-1 ${period.hours}:${(period.minutes.toString().length() == 1 ? '0' : '') + period.minutes}:${period.seconds}')-timeToSubtract, name:'${result.race.name} ${result.race.date.format("M-dd-yyyy")}'}"))
            }
        }

        println data.each {
            println it?.value
        }

        render template: "/templates/charts/runProgression",
               model: [height: 200, width: 200, data: data, id: resultDiv, type: queryRaceCategoryType]
    }

    def triathlonRecords() {
        User user = requestedUser
        def userId = user.id
        def data = [:]

        data.putAll(retrieveTriathlonRecord(userId, SegmentType.Swim, RaceCategoryType.Sprint, RaceType.Triathlon))
        data.putAll(retrieveTriathlonRecord(userId, SegmentType.Bike, RaceCategoryType.Sprint, RaceType.Triathlon))
        data.putAll(retrieveTriathlonRecord(userId, SegmentType.Run, RaceCategoryType.Sprint, RaceType.Triathlon))

        data.putAll(retrieveTriathlonRecord(userId, SegmentType.Swim, RaceCategoryType.Olympic, RaceType.Triathlon))
        data.putAll(retrieveTriathlonRecord(userId, SegmentType.Bike, RaceCategoryType.Olympic, RaceType.Triathlon))
        data.putAll(retrieveTriathlonRecord(userId, SegmentType.Run, RaceCategoryType.Olympic, RaceType.Triathlon))

        println data

        render template: "triathlonRecord", model: [data: data]
    }

    private def retrieveTriathlonRecord(long userId, SegmentType segmentType, RaceCategoryType raceCategoryType, RaceType raceType) {

        def c = SegmentResult.createCriteria()
        def results = c.list {
            raceResult {
                user {
                    eq('id', userId)
                }
                race {
                    eq('raceType', raceType)
                    eq('raceCategoryType', raceCategoryType)
                }
            }
            raceSegment {
                segment {
                    eq('segmentType', segmentType)
                }
            }
        }

//        def results = SegmentResult.where {
//            raceResult.user.id == userId
//            raceResult.race.raceType == raceType
//            raceResult.race.raceCategoryType == raceCategoryType
//            raceSegment.segment.segmentType == segmentType
//        }

        def pr = results?.sort {a, b -> a.duration <=> b.duration}?.getAt(0)
        ["'${raceCategoryType}_${segmentType}'": pr]
    }

    def runningRecords() {
        User user = requestedUser
        def userId = user.id
        def data = [:]

        data.putAll(retrieveRunRecord(userId, RaceCategoryType.OneMile, RaceType.Running))
        data.putAll(retrieveRunRecord(userId, RaceCategoryType.FiveKilometer, RaceType.Running))
        data.putAll(retrieveRunRecord(userId, RaceCategoryType.TenKilometer, RaceType.Running))
        data.putAll(retrieveRunRecord(userId, RaceCategoryType.HalfMarathon, RaceType.Running))
        data.putAll(retrieveRunRecord(userId, RaceCategoryType.Marathon, RaceType.Running))

        println data

        render template: "runningRecord", model: [data: data]
    }

    private def retrieveRunRecord(long userId, RaceCategoryType raceCategoryType, RaceType raceType) {
        def results = RaceResult.where {
            user.id == userId
            race.raceType == raceType
            race.raceCategoryType == raceCategoryType
        }
        def pr = results?.list()?.sort {a, b -> a.duration <=> b.duration}?.getAt(0)
        ["'${raceCategoryType}'": pr]
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

    private void renderDashboardChart(String columnName, String resultTitle, String resultDiv, userId, RaceType queryRaceType, RaceCategoryType queryRaceCategoryType) {
        def columns = [[type: 'string', name: 'Date'], [type: 'timeofday', name: columnName]]

        def results = RaceResult.where {
            user.id == userId
            race.raceType == queryRaceType
            race.raceCategoryType == queryRaceCategoryType
        }

        def sortedResults = results.list().sort {a, b -> a.date <=> b.date}
        def data = []
        sortedResults.each { result ->
            def period = result.duration.toPeriod()
            data << ["'${result.date.format('M/dd/yyyy')}'", [period.hours, period.minutes, period.seconds, period.millis]]
        }


        render template: "chart",
               model: [height: 200, width: 200, columns: columns, data: data, title: resultTitle, id: resultDiv]
    }
}
