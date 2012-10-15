package com.tgid.tri.race

import com.tgid.tri.auth.Country
import com.tgid.tri.auth.State

class Race {

    String name
    Date date
    Long athlinkRaceID
    RaceType raceType
    DistanceType distanceType
    RaceCategoryType raceCategoryType
    Double distance = 0.00
    String resultsUrl
    StatusType statusType = StatusType.Approved
    Long courseID
    Long eventCourseID
    RaceCategory raceCategory
    CoursePattern coursePattern
    State state
    Country country
    String city

    static hasMany = [segments: RaceSegment]

    static constraints = {
        resultsUrl nullable: true, url: true, blank: true
        name nullable: false, blank: false, unique: ['date', 'raceCategoryType']
        date nullable: false
        raceType nullable: false
        distanceType nullable: false
        distance nullable: false, min: 0.01 as Double
        raceCategoryType nullable: false
        statusType nullable: false
        athlinkRaceID nullable: true
        eventCourseID nullable: true
        courseID nullable: true
        raceCategory nullable: true
        coursePattern nullable: true
        state nullable: true
        country nullable: true
        city nullable: true
    }

    static mapping = {
        sort 'date'
        raceCategoryType index: 'RaceCategory_Index'
        raceType index: 'RaceType_Index'
    }

    @Override
    String toString() {
        "${name ?: 'Unknown'}${(raceCategoryType) ? " ($raceCategoryType) " : ' '}${date?.format("yyyy")}"
    }
}
