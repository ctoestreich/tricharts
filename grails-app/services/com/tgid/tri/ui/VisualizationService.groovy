package com.tgid.tri.ui

import com.tgid.tri.auth.GenderType
import com.tgid.tri.auth.State
import com.tgid.tri.common.JodaTimeHelper
import com.tgid.tri.race.RaceCategoryType
import com.tgid.tri.race.RaceType
import com.tgid.tri.race.SegmentType
import com.tgid.tri.results.RaceResult
import com.tgid.tri.results.SegmentResult
import grails.plugin.springcache.annotations.Cacheable
import org.joda.time.Duration

class VisualizationService {

    @Cacheable("chartCache")
    def mapRunningScatter(Long userId, Integer ageMin, Integer ageMax, State state, RaceCategoryType queryRaceCategoryType = RaceCategoryType.OneMile) {
        def queryRaceType = RaceType.Running
        def queryState = state
        def results = RaceResult.where {
            race.state == queryState
            race.raceType == queryRaceType
            race.raceCategoryType == queryRaceCategoryType
            age >= ageMin
            age <= ageMax
        }

        def males = []
        def females = []
        def you = []

        //filter out results under 2min for now
        // results = results.list().findAll { it.duration > Duration.standardSeconds(120)}.collect()

        results.list().each { RaceResult raceResult ->
            if(raceResult?.age && raceResult.result) {
                def data = ["${raceResult.age}", "Date.parse('1-1-1 ${ JodaTimeHelper.getPeriodFormat(true, true, true).print(raceResult.result.pace?.duration?.toPeriod())}')-timeToSubtract"]
                if(raceResult.user.id == userId) {
                    you << data
                } else {
                    switch(raceResult.genderType) {
                        case GenderType.Female:
                            females << data
                            break
                        case GenderType.Male:
                        default:
                            males << data
                            break
                    }
                }
            }
        }

        [males: males, females: females, you: you]
    }

    @Cacheable("triathlonRecordsCache")
    Map mapTriathlonRecords(long userId) {
        def data = [:]
        if(userId) {
            data.putAll(retrieveTriathlonRecord(userId, SegmentType.Swim, RaceCategoryType.Sprint, RaceType.Triathlon))
            data.putAll(retrieveTriathlonRecord(userId, SegmentType.Bike, RaceCategoryType.Sprint, RaceType.Triathlon))
            data.putAll(retrieveTriathlonRecord(userId, SegmentType.Run, RaceCategoryType.Sprint, RaceType.Triathlon))

            data.putAll(retrieveTriathlonRecord(userId, SegmentType.Swim, RaceCategoryType.Olympic, RaceType.Triathlon))
            data.putAll(retrieveTriathlonRecord(userId, SegmentType.Bike, RaceCategoryType.Olympic, RaceType.Triathlon))
            data.putAll(retrieveTriathlonRecord(userId, SegmentType.Run, RaceCategoryType.Olympic, RaceType.Triathlon))
        }
        data
    }

    @Cacheable('runningRecordsCache')
    Map mapRunningRecords(long userId) {
        def data = [:]
        if(userId) {
            data.putAll(retrieveRunRecord(userId, RaceCategoryType.OneMile, RaceType.Running))
            data.putAll(retrieveRunRecord(userId, RaceCategoryType.FiveKilometer, RaceType.Running))
            data.putAll(retrieveRunRecord(userId, RaceCategoryType.TenKilometer, RaceType.Running))
            data.putAll(retrieveRunRecord(userId, RaceCategoryType.TenMile, RaceType.Running))
            data.putAll(retrieveRunRecord(userId, RaceCategoryType.HalfMarathon, RaceType.Running))
            data.putAll(retrieveRunRecord(userId, RaceCategoryType.Marathon, RaceType.Running))
        }
        data
    }

    @Cacheable('chartCache')
    private Map retrieveTriathlonRecord(long userId, SegmentType segmentType, RaceCategoryType raceCategoryType, RaceType raceType, Date minDate = null, Date maxDate = null) {
        def pr
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
                    if(minDate && maxDate) {
                        between('date', minDate, maxDate)
                    }
                }
            }
            raceSegment {
                segment {
                    eq('segmentType', segmentType)
                }
            }
        }

        //filter out results under 2min for now
        results = results.findAll { it.duration > Duration.standardSeconds(120)}.collect()

        if(segmentType == SegmentType.Bike) {
            pr = results?.sort {a, b -> b?.pace?.speed <=> a?.pace?.speed}?.getAt(0)
        } else {
            pr = results?.sort {a, b -> a?.pace?.duration <=> b?.pace?.duration}?.getAt(0)
        }

        ["'${minDate?.year ? minDate.year + 1900 + '_' : ''}${raceCategoryType}_${segmentType}'": pr]
    }

    @Cacheable('chartCache')
    private Map retrieveRunRecord(Long userId, RaceCategoryType raceCategoryType, RaceType raceType, Date minDate = null, Date maxDate = null) {
        def results = RaceResult.where {
            user.id == userId
            race.raceType == raceType
            race.raceCategoryType == raceCategoryType
            if(minDate && maxDate) {
                race.date > minDate
                race.date <= maxDate
            }
        }
        def pr = results?.list()?.sort {a, b -> a.duration <=> b.duration}?.getAt(0)
        ["'${minDate?.year ? minDate.year + 1900 + '_' : ''}${raceCategoryType}'": pr]
    }

    List<RaceResult> getRaceResults(Long userId, RaceType queryRaceType, RaceCategoryType queryRaceCategoryType) {
        def results = RaceResult.where {
            user.id == userId
            race.raceType == queryRaceType
            race.raceCategoryType == queryRaceCategoryType
        }
        results?.list()
    }
}
