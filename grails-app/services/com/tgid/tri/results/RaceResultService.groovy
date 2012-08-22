package com.tgid.tri.results

import com.tgid.tri.auth.User
import com.tgid.tri.exception.RaceResultException
import com.tgid.tri.exception.SegmentResultException
import com.tgid.tri.race.Race
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.joda.time.Duration

class RaceResultService {

    RaceResult mapRaceResultAthlinks(User user, Race race, Map result = [:]) {
        def raceResult = new RaceResult(
                race: race,
                user: user,
                athlinkEntryID: result.EntryID,
                duration: Duration.millis(result.Ticks as Long),
                placeAgeGroup: result.RankA,
                placeGender: result.RankG,
                placeOverall: result.RankO,
                participantsAgeGroup: result.CountA,
                participantsGender: result.CountG,
                participantsOverall: result.Count0)

        Integer index = 0
        result.LegEntries.each { segment ->
            if(segment.ActionCatName != 'Penalty'){
                createSegmentResult(race, raceResult, segment, index)
                index++
            }
        }
        createRaceResult(raceResult)
    }

    private void createSegmentResult(Race race, RaceResult raceResult, Map segment, Integer i) {
        try {
            def raceSegment = race.segments.sort {a, b -> a.segmentOrder <=> b.segmentOrder}
            def segmentResult = new SegmentResult(raceSegment: raceSegment.get(i),
                                                  duration: Duration.millis(segment.Ticks as Long),
                                                  placeAgeGroup: segment.RankA,
                                                  placeOverall: segment.RankO,
                                                  placeGender: segment.RankG)
            raceResult.addToSegmentResults(segmentResult)
        } catch(Exception e) {
            log.info "Error adding segment to ${raceResult}"
            log.error e
        }
    }

    RaceResult mapRaceResult(User user, GrailsParameterMap params) {
        params.user = user

        def raceResult

        if(params?.raceResultId) {
            raceResult = RaceResult.get(params?.raceResultId)
            raceResult.properties = params
        } else {
            raceResult = new RaceResult(params)
        }

        if(raceResult.duration == Duration.standardSeconds(0)) {
            raceResult.errors.rejectValue('duration', 'raceResult.duration.greater.than.zero')
        }

        def segmentCount = params?.int('segmentCount') ?: 0

        (0..<segmentCount).each {
            String key = "segmentResult[$it]".toString()
            Map segment = params.get(key) as Map
            def segmentResult
            if(segment) {
                if(segment?.id) {
                    segmentResult = SegmentResult.get(segment.id)
                    segmentResult.properties = segment
                } else {
                    segmentResult = new SegmentResult(segment)
                    if(segmentCount == 1) {
                        mapSegmentResultToRace(raceResult, segmentResult)
                    }
                    raceResult.addToSegmentResults(segmentResult)
                }
            }
        }

        raceResult
    }

    RaceResult createRaceResult(RaceResult raceResult) throws RaceResultException, SegmentResultException {

        if(raceResult.validate()) {
            raceResult.save(flush: true)
        }
        else {
            log.error(raceResult.errors.toString())
            def message = "Could not save result ${raceResult}"
            throw new RaceResultException(
                    message: message,
                    problem: raceResult
            )
        }
        raceResult
    }

    void deleteRaceResult(long raceResultId, User user) {
        def raceResult = RaceResult.findByIdAndUser(raceResultId, user)
        try {
            raceResult.delete()
        } catch(Exception exception) {
            log.error(exception)
            def message = "Could not delete result ${raceResult} (${exception.message})"
            throw new RaceResultException(
                    message: message,
                    problem: raceResult
            )
        }
    }

    /**
     * When only one segment result exists just map it to the race result since ui will be hidden
     * @param raceResult
     * @param segmentResult
     */
    private void mapSegmentResultToRace(RaceResult raceResult, SegmentResult segmentResult) {
        segmentResult.duration = raceResult.duration
        segmentResult.placeOverall = raceResult.placeOverall
        segmentResult.placeAgeGroup = raceResult.placeAgeGroup
        segmentResult.placeGender = raceResult.placeGender
    }
}
