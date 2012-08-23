package com.tgid.tri.race

class Race {

    String name
    Date date
    Long athlinkRaceID
    RaceType raceType
    DistanceType distanceType
    RaceCategoryType raceCategoryType
    Float distance = 0.00f
    String resultsUrl
    StatusType statusType = StatusType.Pending
    Long eventCourseID

    static hasMany = [segments: RaceSegment]

    static constraints = {
        resultsUrl nullable: true, url: true, blank: true
        name nullable: false, blank: false, unique: ['date', 'raceCategoryType']
        date nullable: false
        raceType nullable: false
        distanceType nullable: false
        distance nullable: false, min: 0.01F
        raceCategoryType nullable: false
        statusType nullable: false
        athlinkRaceID nullable: true
        eventCourseID nullable: true
    }

    static mapping = {
        sort 'date'
        raceCategoryType index: 'RaceCategory_Index'
        raceType index: 'RaceType_Index'
    }

    @Override
    String toString() {
        "${name ?: 'Unknown'}${(raceCategoryType) ?" ($raceCategoryType) ": ' '}${date?.format("yyyy")}"
    }
}
