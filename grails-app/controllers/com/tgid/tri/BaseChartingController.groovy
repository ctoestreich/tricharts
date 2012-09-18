package com.tgid.tri

import com.tgid.tri.common.JodaTimeHelper
import com.tgid.tri.race.RaceCategoryType
import com.tgid.tri.race.RaceType
import com.tgid.tri.race.SegmentType
import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.SegmentResult
import com.tgid.tri.ui.BarChartSeriesData
import com.tgid.tri.ui.BarChartSeriesDrilldownData
import grails.plugin.springcache.annotations.Cacheable
import org.joda.time.Duration

import java.text.DecimalFormat

class BaseChartingController extends BaseController {

    def visualizationService

    @Cacheable('siteCache')
    protected List<RaceCategoryType> getRaceCategoriesByType(String raceType) {
        if(raceType == 'Running')
            return [RaceCategoryType.OneMile, RaceCategoryType.FiveKilometer, RaceCategoryType.EightKilometer, RaceCategoryType.TenKilometer, RaceCategoryType.TenMile, RaceCategoryType.HalfMarathon, RaceCategoryType.Marathon]

        if(raceType == 'Triathlon')
            return [RaceCategoryType.Sprint, RaceCategoryType.Olympic, RaceCategoryType.HalfIronman, RaceCategoryType.Ironman]

        return []
    }

    @Cacheable("runningRecordsCache")
    protected void renderRunningPrsChart(String div, long userId, RaceType raceType) {
        def data = [:]
        def minYear = 0
        def maxYear = 0

        def years = RaceResult.executeQuery("select min(race.date) as mn, max(race.date) as mx from RaceResult rr where user.id = :userId and race.raceType = :raceType",
                                            [userId: userId, raceType: raceType])
        def types = RaceResult.executeQuery("select distinct race.raceCategoryType from RaceResult where user.id = :userId and race.raceType = :raceType",
                                            [userId: userId, raceType: raceType])

        years.each {
            maxYear = it[1].year
            minYear = it[0].year
        }

        if(minYear > 0 && maxYear > 0) {
            types.each { RaceCategoryType raceCategoryType ->
                def year = minYear
                while(year <= maxYear) {
                    data.putAll(visualizationService.retrieveRunRecord(userId, raceCategoryType, RaceType.Running, new Date(year, 0, 1), new Date(year + 1, 0, 1)))
                    year++
                }
            }
        }

        render template: '/templates/charts/runPrs', model: [types: types, data: data, minYear: minYear, maxYear: maxYear]
    }

    @Cacheable("triathlonRecordsCache")
    protected void renderTriathlonPrsChart(String div, long userId, RaceType raceType) {
        def data = [:]
        def minYear = 0
        def maxYear = 0

        def years = RaceResult.executeQuery("select min(race.date), max(race.date) from RaceResult where user.id = :userId and race.raceType = :raceType",
                                            [userId: userId, raceType: raceType])

        def types = [RaceCategoryType.Sprint, RaceCategoryType.Olympic, RaceCategoryType.HalfIronman, RaceCategoryType.Ironman]

        years.each {
            maxYear = it[1].year
            minYear = it[0].year
        }

        if(minYear > 0 && maxYear > 0) {
            types.each { RaceCategoryType raceCategoryType ->
                def year = minYear
                while(year <= maxYear) {
                    data.putAll(visualizationService.retrieveTriathlonRecord(userId, SegmentType.Swim, raceCategoryType, RaceType.Triathlon, new Date(year, 0, 1), new Date(year + 1, 0, 1)))
                    data.putAll(visualizationService.retrieveTriathlonRecord(userId, SegmentType.Bike, raceCategoryType, RaceType.Triathlon, new Date(year, 0, 1), new Date(year + 1, 0, 1)))
                    data.putAll(visualizationService.retrieveTriathlonRecord(userId, SegmentType.Run, raceCategoryType, RaceType.Triathlon, new Date(year, 0, 1), new Date(year + 1, 0, 1)))
                    year++
                }
            }
        }

        render template: '/templates/charts/triathlonPrs', model: [types: types, data: data, minYear: minYear, maxYear: maxYear]
    }

    @Cacheable('chartCache')
    protected void renderRunProgressionChart(String resultDiv, Long userId, RaceType raceType, RaceCategoryType raceCategoryType) {
        def results = visualizationService.getRaceResults(userId, raceType, raceCategoryType)

        def sortedResults = results.sort {a, b -> a.date <=> b.date}
        def data = [:]
        sortedResults.each { result ->
            def key = result.date.year + 1900
            def period = result.duration.toPeriod()
            if(data.containsKey(key)) {
                data.get(key).append(",{x: Date.UTC(${result.date.format('1970, M-1, dd')}), y: Date.parse('1-1-1 ${period.hours}:${(period.minutes.toString().length() == 1 ? '0' : '') + period.minutes}:${period.seconds}')-timeToSubtract, name:'${result.race.name.replace('\'', '\\\'')} ${result.race.date.format("M-dd-yyyy")}'}")
            } else {
                data.put(key, new StringBuilder("{x: Date.UTC(${result.date.format('1970, M-1, dd')}), y: Date.parse('1-1-1 ${period.hours}:${(period.minutes.toString().length() == 1 ? '0' : '') + period.minutes}:${period.seconds}')-timeToSubtract, name:'${result.race.name.replace('\'', '\\\'')} ${result.race.date.format("M-dd-yyyy")}'}"))
            }
        }

        render template: "/templates/charts/runProgression",
               model: [height: 200, width: 200, data: data, id: resultDiv, type: raceCategoryType]
    }

    @Cacheable('chartCache')
    protected renderTriathlonAveragePlacesChart(String resultDiv, Long userId, RaceType queryRaceType, SegmentType segmentType, String type = 'oa') {
        def races = getRaceCategoriesByType('Triathlon')
        def dataByCategoryByYear = new HashMap<String, HashMap<Integer, List>>()
        def categories = []
        def totalRaces = []
        def dataByCategory = new HashMap<String, List>()

        mapAverageSegmentResults(races, categories, userId, queryRaceType, totalRaces, dataByCategoryByYear, dataByCategory)

        //def sortedData = data.keySet().toList().sort { a, b -> a <=> b}.collect()

        def results = createAveragePlacementChartData(dataByCategory, dataByCategoryByYear, races, SegmentType.Run, type)

        render template: "/templates/charts/runAveragePlaces",
               model: [title: "Average ${type == "oa" ? 'Overall' : 'Age Group'} Placement By Year - ${segmentType ?: 'Overall'}", height: 200, width: 200, data: results, id: resultDiv, categories: categories, totalRaces: totalRaces]
    }

    @Cacheable('chartCache')
    protected renderRunAveragePlacesChart(String resultDiv, Long userId, RaceType queryRaceType, String type = 'oa') {
        def races = getRaceCategoriesByType('Running')
        def dataByCategoryByYear = new HashMap<String, HashMap<Integer, List>>()
        def categories = []
        def totalRaces = []
        def dataByCategory = new HashMap<String, List>()

        mapAverageSegmentResults(races, categories, userId, queryRaceType, totalRaces, dataByCategoryByYear, dataByCategory)

        //def sortedData = dataByCategory.keySet().toList().sort { a, b -> a <=> b}.collect()

        def results = createAveragePlacementChartData(dataByCategory, dataByCategoryByYear, races, SegmentType.Run, type)

        render template: "/templates/charts/runAveragePlaces",
               model: [title: "Average ${type == "oa" ? 'Overall' : 'Age Group'} Placement By Year", height: 200, width: 200, data: results, id: resultDiv, categories: categories, totalRaces: totalRaces]
    }

    @Cacheable('chartCache')
    protected renderTriathlonAveragesChart(String resultDiv, Long userId, RaceType queryRaceType, SegmentType segmentType) {
        def races = getRaceCategoriesByType('Triathlon')
        def dataByCategoryByYear = new HashMap<String, HashMap<Integer, List>>()
        def categories = []
        def totalRaces = []
        def dataByCategory = new HashMap<String, List>()

        mapAverageSegmentResults(races, categories, userId, queryRaceType, totalRaces, dataByCategoryByYear, dataByCategory)

        def results = createAveragesPaceChartData(dataByCategory, dataByCategoryByYear, races, segmentType)

        if(segmentType == SegmentType.Run || segmentType == SegmentType.Swim) {
            render template: "/templates/charts/runAverages",
                   model: [title: (segmentType == SegmentType.Run) ? 'Average Mile Pace Per Year' : 'Average Swim Pace Per Year', data: results, id: resultDiv, categories: categories, totalRaces: totalRaces]
        } else if(segmentType == SegmentType.Bike) {
            render template: "/templates/charts/bikeAverages",
                   model: [title: 'Average Bike Speed Per Year', height: 200, width: 200, data: results, id: resultDiv, categories: categories, totalRaces: totalRaces]
        }
    }

    protected void mapSegmentResults(SegmentResult segmentResult, HashMap<String, HashMap<Integer, List>> data, String key, RaceResult result, HashMap<String, List> dataByCategory) {
        if(segmentResult) {
            if(dataByCategory.containsKey(key)) {
                dataByCategory.get(key) << segmentResult
            } else {
                dataByCategory.put(key, [])
                dataByCategory.get(key) << segmentResult
            }

            def childKey = result.race.date.year + 1900
            if(data.containsKey(key)) {
                def childMap = data.get(key)
                if(childMap.containsKey(childKey)) {
                    childMap.get(childKey) << segmentResult
                } else {
                    childMap.put(childKey, [segmentResult])
                }
            } else {
                data.put(key, [:])
                data.get(key).put(childKey, [segmentResult])
            }
        }
    }

    protected createAveragesPaceChartData(HashMap<String, List> sortedData, HashMap<String, HashMap<Integer, List>> data, List<RaceCategoryType> races, SegmentType segmentType) {
        def drilldowns = []

        races.eachWithIndex { race, i ->
            def key = race.toString()
            def listByCategory = sortedData?.get(key) ?: []
            BarChartSeriesData barChartSeriesData = new BarChartSeriesData()
            barChartSeriesData.y = getChartPace(segmentType, listByCategory)
            barChartSeriesData.hasData = (listByCategory?.size() > 0)

            if(data.containsKey(key)) {
                BarChartSeriesDrilldownData drilldown = new BarChartSeriesDrilldownData(name: "${race}")
                data.get(key).entrySet().each { ddEntry ->
                    drilldown.categories << "'${ddEntry.key}'"
                    drilldown.data << getChartPace(segmentType, ddEntry.value)
                }
                barChartSeriesData.drilldown = drilldown
            }

            drilldowns << barChartSeriesData
        }

        drilldowns
    }

    private String getChartPace(SegmentType segmentType, List segments) {
        switch(segmentType) {
            case SegmentType.Run:
            case SegmentType.Swim:
                return "Date.parse('1-1-1 ${tri.formatDuration(duration: averageTime(segments), formatter: JodaTimeHelper.getPeriodFormat(true, true, true))}')-timeToSubtract"
                break
            case SegmentType.Bike:
            default:
                return "${averageSpeed(segments)}"
                break
        }
        return ""
    }

    protected createAveragePlacementChartData(HashMap<String, List> sortedData, HashMap<String, HashMap<Integer, List>> data, List<RaceCategoryType> races, SegmentType segmentType, String type = "oa") {
        def drilldowns = []

        races.eachWithIndex { race, i ->
            def key = race.toString()
            def listByCategory = sortedData?.get(key) ?: []
            BarChartSeriesData barChartSeriesData = new BarChartSeriesData()
            def place = averagePlace(listByCategory, type)
            barChartSeriesData.y = place
            barChartSeriesData.hasData = (place > 0)

            if(data.containsKey(key)) {
                BarChartSeriesDrilldownData drilldown = new BarChartSeriesDrilldownData(name: "${race}")
                data.get(key).entrySet().each { ddEntry ->
                    drilldown.categories << "'${ddEntry.key}'"
                    drilldown.data << averagePlace(ddEntry.value, type)
                }
                barChartSeriesData.drilldown = drilldown
            }

            drilldowns << barChartSeriesData
        }

        drilldowns
    }

    @Cacheable('chartCache')
    protected renderRunAveragesChart(String resultDiv, Long userId, RaceType queryRaceType) {
        def races = getRaceCategoriesByType('Running')
        def dataByCategoryByYear = new HashMap<String, HashMap<Integer, List>>()
        def categories = []
        def totalRaces = []
        def dataByCategory = new HashMap<String, List>()

        mapAverageSegmentResults(races, categories, userId, queryRaceType, totalRaces, dataByCategoryByYear, dataByCategory)

        def results = createAveragesPaceChartData(dataByCategory, dataByCategoryByYear, races, SegmentType.Run)

        render template: "/templates/charts/runAverages",
               model: [height: 200, width: 200, data: results, id: resultDiv, categories: categories, totalRaces: totalRaces]
    }

    private void mapAverageSegmentResults(List<RaceCategoryType> races, categories, long userId, RaceType queryRaceType, totalRaces, dataByCategoryByYear, dataByCategory) {
        races.each { raceCategoryType ->
            categories << "'${raceCategoryType.raceCategoryType}'"
            def results = visualizationService.getRaceResults(userId, queryRaceType, raceCategoryType)

            def sortedResults = results?.sort {it?.date}

            totalRaces << "{name: '${raceCategoryType.raceCategoryType}', y: ${sortedResults?.size() ?: 0}}"

            sortedResults.each { result ->
                def key = raceCategoryType.raceCategoryType
                def segmentResult = result?.segmentResults?.toList()?.get(0)
                mapSegmentResults(segmentResult, dataByCategoryByYear, key, result, dataByCategory)
            }
        }
    }

    protected Long averagePlace(List races, String type = "oa") {
        Integer total = 0
        Integer place = 0
        races.toArray().each { def segment ->
            def segmentPlace = 0
            switch(type) {
                case "oa":
                    segmentPlace = segment?.placeOverall ?: 0
                    break
                case "ag":
                    segmentPlace = segment?.placeAgeGroup ?: 0
                    break
            }
            place += segmentPlace
            total += (segmentPlace > 0) ? 1 : 0
        }
        println "${races?.size()} - ${place}/${total}=${Math.round(place / (total ?:1))}"
        def result = Math.round(place / (total ?:1))
        return result
    }

    protected Double averageSpeed(List races) {
        BigDecimal speed = 0.00
        Integer total = 0
        races.toArray().each { SegmentResult segment ->
            if(segment?.pace?.speed && segment?.pace?.speed > 0) {
                speed += segment.pace.speed
                total++
            }
        }
        if(total > 0 && speed > 0){
            return Double.valueOf(new DecimalFormat("#.##").format(speed / total));
        } else {
            return 0
        }
    }

    protected Duration averageTime(List races) {
        Duration time = Duration.standardSeconds(0)
        Integer total = 0
        races?.toArray()?.each { SegmentResult segment ->
            if(segment?.pace?.duration && segment?.pace?.duration > Duration.standardMinutes(0)) {
                time = time.plus(segment?.pace?.duration)
                total++
            }
        }

        def result = Duration.standardSeconds(0)
        if(total > 0) {
            result = Duration.standardSeconds(Math.round(time.standardSeconds / total))
        }
        return result
    }

    @Cacheable(cache = 'chartCache', keyGenerator = 'authenticationAwareKeyGenerator')
    protected void renderTriathlonProgressionChart(String resultDiv, userId, RaceType queryRaceType, RaceCategoryType queryRaceCategoryType) {

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

    protected StringBuilder getTriathlonGraphData(SegmentResult result, SegmentType segmentType) {
        def x = "Date.UTC(${result.date.format('1970, M-1, dd')})"
        def y = (segmentType == SegmentType.Bike) ? result.pace : getSwimRunPaceGraph(result)
        def name = "${result.raceResult.race.name} ${result.raceResult.race.date.format("M-dd-yyyy")}"

        new StringBuilder("{x: ${x}, y:${y}, name:'${name}'}")
    }

    protected String getSwimRunPaceGraph(SegmentResult result) {
        "Date.parse('1-1-1 00:${result.pace}')-timeToSubtract"
    }

    @Cacheable('chartCache')
    protected void renderDashboardChart(String columnName, String resultTitle, String resultDiv, Long userId, RaceType queryRaceType, RaceCategoryType queryRaceCategoryType) {
        def columns = [[type: 'string', name: 'Date'], [type: 'timeofday', name: columnName]]

        def results = visualizationService.getRaceResults(userId, queryRaceType, queryRaceCategoryType)

        def sortedResults = results?.sort {a, b -> a.date <=> b.date}
        def data = []
        sortedResults.each { result ->
            def period = result.duration.toPeriod()
            data << ["'${result.date.format('M/dd/yyyy')}'", [period.hours, period.minutes, period.seconds, period.millis]]
        }


        render template: "chart",
               model: [height: 200, width: 200, columns: columns, data: data, title: resultTitle, id: resultDiv]
    }
}
