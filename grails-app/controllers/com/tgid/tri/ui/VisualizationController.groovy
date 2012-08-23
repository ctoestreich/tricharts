package com.tgid.tri.ui

import com.tgid.tri.BaseController
import com.tgid.tri.auth.User
import com.tgid.tri.race.RaceCategoryType
import com.tgid.tri.race.RaceType
import com.tgid.tri.race.SegmentType
import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.SegmentResult
import grails.plugins.springsecurity.Secured
import grails.plugin.springcache.annotations.Cacheable
import org.joda.time.Duration

@Secured(["ROLE_USER"])
class VisualizationController extends BaseController {

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

    @Cacheable("triathlonRecordsCache")
    def triathlonRecords() {
        User user = requestedUser
        def userId = user?.id
        def data = [:]

        if(userId) {
            data.putAll(retrieveTriathlonRecord(userId, SegmentType.Swim, RaceCategoryType.Sprint, RaceType.Triathlon))
            data.putAll(retrieveTriathlonRecord(userId, SegmentType.Bike, RaceCategoryType.Sprint, RaceType.Triathlon))
            data.putAll(retrieveTriathlonRecord(userId, SegmentType.Run, RaceCategoryType.Sprint, RaceType.Triathlon))

            data.putAll(retrieveTriathlonRecord(userId, SegmentType.Swim, RaceCategoryType.Olympic, RaceType.Triathlon))
            data.putAll(retrieveTriathlonRecord(userId, SegmentType.Bike, RaceCategoryType.Olympic, RaceType.Triathlon))
            data.putAll(retrieveTriathlonRecord(userId, SegmentType.Run, RaceCategoryType.Olympic, RaceType.Triathlon))
        }

        render template: "triathlonRecord", model: [data: data]
    }

    @Cacheable("runningRecordsCache")
    def runningRecords() {
        User user = requestedUser
        def userId = user.id
        def data = [:]

        data.putAll(retrieveRunRecord(userId, RaceCategoryType.OneMile, RaceType.Running))
        data.putAll(retrieveRunRecord(userId, RaceCategoryType.FiveKilometer, RaceType.Running))
        data.putAll(retrieveRunRecord(userId, RaceCategoryType.TenKilometer, RaceType.Running))
        data.putAll(retrieveRunRecord(userId, RaceCategoryType.HalfMarathon, RaceType.Running))
        data.putAll(retrieveRunRecord(userId, RaceCategoryType.Marathon, RaceType.Running))

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

    private void renderRunProgressionChart(String resultDiv, userId, RaceType queryRaceType, RaceCategoryType queryRaceCategoryType) {

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

        render template: "/templates/charts/runProgression",
               model: [height: 200, width: 200, data: data, id: resultDiv, type: queryRaceCategoryType]
    }

    private void renderTriathlonProgressionChart(String resultDiv, userId, RaceType queryRaceType, RaceCategoryType queryRaceCategoryType) {

        def segmentData = new HashMap<SegmentType, HashMap<Integer, StringBuilder>>()
        segmentData.put(SegmentType.Swim, [:])
        segmentData.put(SegmentType.Bike, [:])
        segmentData.put(SegmentType.Run, [:])

        segmentData.each { segmentType, data ->
            def c = SegmentResult.createCriteria()
            def results = c.list {
                raceResult {
                    user {
                        eq('id', userId)
                    }
                    race {
                        eq('raceType', queryRaceType)
                        eq('raceCategoryType', queryRaceCategoryType)
                    }
                }
                raceSegment {
                    segment {
                        eq('segmentType', segmentType)
                    }
                }
            }

            def sortedResults = results.sort {a, b -> a.date <=> b.date}
            sortedResults.each { result ->
                def key = result.date.year + 1900

                if(data.containsKey(key)) {
                    data.get(key).append(",${getTriathlonGraphData(result, segmentType)}")
                } else {
                    data.put(key, getTriathlonGraphData(result, segmentType))
                }
            }
        }

        render template: "/templates/charts/triathlonProgression",
               model: [height: 200, width: 200, data: segmentData, id: resultDiv, type: queryRaceCategoryType]
    }

    private StringBuilder getTriathlonGraphData(SegmentResult result, SegmentType segmentType) {
        def x = "Date.UTC(${result.date.format('1970, M-1, dd')})"
        def y = (segmentType == SegmentType.Bike) ? result.pace : getSwimRunPaceGraph(result)
        def name = "${result.raceResult.race.name} ${result.raceResult.race.date.format("M-dd-yyyy")}"

        new StringBuilder("{x: ${x}, y:${y}, name:'${name}'}")
    }

    private String getSwimRunPaceGraph(SegmentResult result) {
        "Date.parse('1-1-1 00:${result.pace}')-timeToSubtract"
    }

    private Map retrieveTriathlonRecord(long userId, SegmentType segmentType, RaceCategoryType raceCategoryType, RaceType raceType) {

        def c = SegmentResult.createCriteria()
        def results = c.list {
//            gt('duration', Duration.standardSeconds(120))
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

        //filter out results under 2min for now
        results = results.findAll{ it.duration > Duration.standardSeconds(120)}.collect()

//        def results = SegmentResult.where {
//            raceResult.user.id == userId
//            raceResult.race.raceType == raceType
//            raceResult.race.raceCategoryType == raceCategoryType
//            raceSegment.segment.segmentType == segmentType
//        }

        def pr

        if(segmentType == SegmentType.Bike) {
            pr = results?.sort {a, b -> b?.pace?.speed <=> a?.pace?.speed}?.getAt(0)
        } else {
            pr = results?.sort {a, b -> a?.pace?.duration <=> b?.pace?.duration}?.getAt(0)
        }

        ["'${raceCategoryType}_${segmentType}'": pr]
    }

    private Map retrieveRunRecord(long userId, RaceCategoryType raceCategoryType, RaceType raceType) {
        def results = RaceResult.where {
            user.id == userId
            race.raceType == raceType
            race.raceCategoryType == raceCategoryType
        }
        def pr = results?.list()?.sort {a, b -> a.duration <=> b.duration}?.getAt(0)
        ["'${raceCategoryType}'": pr]
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
